package servidor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import entidades.Mapa;

public class ServidorJuego extends Thread {
	public static AtencionConexiones atencionConexiones;
	
	public static ArrayList<Sala> listadoSalas;
	private static ArrayList<ConexionCliente> clientesConectados = new ArrayList<>();
	
	private static Mapa mapa = new Mapa();
	private static ServerSocket servidor = null;
	
	private static Thread server;
	
	private static final int ANCHO = 700;
	private static final int ALTO = 640;
	private static final int ALTO_LOG = 520;
	private static final int CORRIMIENTO = 25;
	private static final int ANCHO_LOG = ANCHO - CORRIMIENTO;
	private static final int TAM_LETRA_LABEL = 16;
	private static final int POSX = 10;
	private static final int POSY = 0;
	private static final int ANCHO_LABEL = 200;
	private static final int ALTO_LABEL = 30;
	private static final int TAM_LETRA_TEXAREA = 13;
	private static final int POSYTA = 40;
	private static final int POSX_BOTON = 220;
	private static final int CORRIMIENTO_BOTON = 70;
	private static final int POSY_BOTON = ALTO - CORRIMIENTO_BOTON;
	private static final int ANCHO_BOTON = 100;
	private static final int ALTO_BOTON = 30;
	private static final int POSX_BOTON2 = 360;

	public static JTextArea log;
	
	public static void main(final String[] args) {
		cargarInterfaz();
	}
	
	/**
	 * Muestra la interfaz gráfica del servidor.
	 */
	private static void cargarInterfaz() {
		JFrame ventana = new JFrame("Servidor BMB");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(ANCHO, ALTO);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setLayout(null);
		//ventana.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/servidor/server.png"));
		JLabel titulo = new JLabel("Log del servidor...");
		titulo.setFont(new Font("Courier New", Font.BOLD, TAM_LETRA_LABEL));
		titulo.setBounds(POSX, POSY, ANCHO_LABEL, ALTO_LABEL);
		ventana.add(titulo);

		log = new JTextArea();
		log.setEditable(false);
		log.setFont(new Font("Times New Roman", Font.PLAIN, TAM_LETRA_TEXAREA));
		JScrollPane scroll = new JScrollPane(log, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(POSX, POSYTA, ANCHO_LOG, ALTO_LOG);
		ventana.add(scroll);

		final JButton botonIniciar = new JButton();
		final JButton botonDetener = new JButton();
		botonIniciar.setText("Iniciar");
		botonIniciar.setBounds(POSX_BOTON, POSY_BOTON, ANCHO_BOTON, ALTO_BOTON);
		botonIniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				server = new Thread(new ServidorJuego());
				server.start();
				botonIniciar.setEnabled(false);
				botonDetener.setEnabled(true);
			}
		});

		ventana.add(botonIniciar);

		botonDetener.setText("Detener");
		botonDetener.setBounds(POSX_BOTON2, POSY_BOTON, ANCHO_BOTON, ALTO_BOTON);
		botonDetener.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					server.stop();
					atencionConexiones.stop();
//					atencionMovimientos.stop();
					for (ConexionCliente cliente : clientesConectados) {
						cliente.getSalidaDatos().close();
						cliente.getEntradaDatos().close();
						cliente.getSocket().close();
					}
					servidor.close();
					log.append("El servidor se ha detenido." + System.lineSeparator());
				} catch (IOException e1) {
					log.append("Fallo al intentar detener el servidor." + System.lineSeparator());
					System.exit(1);
				}
//				if (conexionDB != null) {
//					conexionDB.close();
//				}
				botonDetener.setEnabled(false);
				botonIniciar.setEnabled(true);
			}
		});
		botonDetener.setEnabled(false);
		ventana.add(botonDetener);

		ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		ventana.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void windowClosing(final WindowEvent evt) {
				if (servidor != null) {
					try {
						server.stop();
						atencionConexiones.stop();
//						atencionMovimientos.stop();
						for (ConexionCliente cliente : clientesConectados) {
							cliente.getSalidaDatos().close();
							cliente.getEntradaDatos().close();
							cliente.getSocket().close();
						}
						servidor.close();
						log.append("El servidor se ha detenido." + System.lineSeparator());
					} catch (IOException e) {
						log.append("Fallo al intentar detener el servidor." + System.lineSeparator());
					}
				}
//				if (conexionDB != null) {
//					conexionDB.close();
//				}
				System.exit(0);
			}
		});

		ventana.setVisible(true);
	}
	
	@Override
	public void run() {
		Socket socket = null;
		Mensaje mensajes = new Mensaje();
		
		try {
			ArchivoDePropiedades archivo = new ArchivoDePropiedades("config.properties");
			archivo.lectura();
			int puerto = archivo.getPuerto();
			servidor = new ServerSocket(puerto);
			agregarLog("Servidor funcionando en el puerto: " + puerto);
			listadoSalas = new ArrayList<>();
	        Sala sala = new Sala("Principal");
	        agregarSala(sala);
	        String ipRemota;
	        atencionConexiones = new AtencionConexiones();
	        atencionConexiones.start();
	        
			while (true) {
				socket = servidor.accept();
				ipRemota =  socket.getInetAddress().getHostAddress();
				
				agregarLog("Nuevo usuario se ha conectado " + ipRemota);
				
				DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
				DataInputStream entrada = new DataInputStream(socket.getInputStream());
				
				ConexionCliente cc = new ConexionCliente(socket, mensajes, mapa, entrada, salida);
				cc.start();
				clientesConectados.add(cc);
			}
		} catch (IOException e) {
			agregarLog("Error en el servidor");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				servidor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(1);
		}
	}
	
	public static void agregarLog(String texto) {
		log.append(texto + System.lineSeparator());
		System.out.println(texto);
	}
	
	public static ArrayList<ConexionCliente> getClientesConectados() {
		return clientesConectados;
	}
	
	public static void agregarSala(Sala s) {
        //if (obtenerSala(s.getNombre()) == null) {
            listadoSalas.add(s);
            agregarLog(("Se ha creado la sala con nombre " + s.getNombre()));
//            for(Sala sa: listadoSalas) {
//            	sa.difundirSalas();
//            }
        //}
    }
}
