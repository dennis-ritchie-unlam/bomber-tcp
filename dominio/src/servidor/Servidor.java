package servidor;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import mensaje.PaqueteMensaje;
import mensaje.PaquetePersonaje;

//import mensajeria.PaqueteMensaje;
//import mensajeria.PaqueteMovimiento;
//import mensajeria.PaquetePersonaje;

public class Servidor extends Thread {
	private static ArrayList<ConexionCliente> clientesConectados = new ArrayList<>();

//	private static Map<Integer, PaqueteMovimiento> ubicacionPersonajes = new HashMap<>();
	private static Map<Integer, PaquetePersonaje> personajesConectados = new HashMap<>();

	private static Thread server;

	private static ServerSocket serverSocket;
//	private static Conector conexionDB;
	private final int puerto = 51000;

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

	public static AtencionConexion atencionConexiones;
//	public static AtencionMovimientos atencionMovimientos;

	/**
	 * Programa de ejecución principal.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		cargarInterfaz();
	}

	/**
	 * Muestra la interfaz gráfica del servidor.
	 */
	private static void cargarInterfaz() {
		JFrame ventana = new JFrame("Servidor BOMBERMAN!!!");
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
				server = new Thread(new Servidor());
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
						cliente.getSalida().close();
						cliente.getEntrada().close();
						cliente.getSocket().close();
					}
					serverSocket.close();
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
				if (serverSocket != null) {
					try {
						server.stop();
						atencionConexiones.stop();
//						atencionMovimientos.stop();
						for (ConexionCliente cliente : clientesConectados) {
							cliente.getSalida().close();
							cliente.getEntrada().close();
							cliente.getSocket().close();
						}
						serverSocket.close();
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
		try {

//			conexionDB = new Conector();
//			conexionDB.connect();

			log.append("Iniciando el servidor..." + System.lineSeparator());
			serverSocket = new ServerSocket(puerto);
			log.append("Servidor esperando conexiones..." + System.lineSeparator());
			String ipRemota;

			atencionConexiones = new AtencionConexion();
//			atencionMovimientos = new AtencionMovimientos();

			atencionConexiones.start();
//			atencionMovimientos.start();

			while (true) {
				Socket cliente = serverSocket.accept();
				ipRemota = cliente.getInetAddress().getHostAddress();
				log.append(ipRemota + " se ha conectado" + System.lineSeparator());

				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

				ConexionCliente atencion = new ConexionCliente(ipRemota, cliente, entrada, salida);
				atencion.start();
				clientesConectados.add(atencion);
			}
		} catch (Exception e) {
			log.append("Fallo la conexión." + System.lineSeparator());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Envia un mensaje a un usuario conectado.
	 * 
	 * @param pqm PaqueteMensaje con el mensaje a enviar.
	 * @return devuelve true si se pudo enviar el mensaje.
	 */
	public static boolean mensajeAUsuario(final PaqueteMensaje pqm) {
		for (Map.Entry<Integer, PaquetePersonaje> personaje : personajesConectados.entrySet()) {
//			if (personaje.getValue().getNombre().equals(pqm.getUserReceptor())) {
//				Servidor.log.append(
//						pqm.getUserEmisor() + " envió mensaje a " + pqm.getUserReceptor() + System.lineSeparator());
//				return true;
//			}
		}
		Servidor.log.append("El mensaje para " + pqm.getUserReceptor()
				+ " no se envió, ya que se encuentra desconectado." + System.lineSeparator());
		return false;
	}

	/**
	 * Loguea en el server si se pudo mandar o no el mensaje
	 * 
	 * @param contador
	 * @return result
	 */
	public static boolean mensajeAAll(final int contador) {

		boolean result = personajesConectados.size() == contador + 1;

		if (result) {
			// inicio
			// sesion
			Servidor.log.append("Se ha enviado un mensaje a todos los usuarios" + System.lineSeparator());
		} else {
			Servidor.log
					.append("Uno o más de todos los usuarios se ha desconectado, se ha mandado el mensaje a los demas."
							+ System.lineSeparator());
		}

		return result; // Devuelvo resultado
	}

	/**
	 * @return devuelve una lista con todos los clientes conectados.
	 */
	public static ArrayList<ConexionCliente> getClientesConectados() {
		return clientesConectados;
	}

//	/**
//	 * @return devuelve un mapa con las ubicaciones de los personajes.
//	 */
//	public static Map<Integer, PaqueteMovimiento> getUbicacionPersonajes() {
//		return ubicacionPersonajes;
//	}

	/**
	 * @return devuelve un mapa con los personajes conectados al servidor.
	 */
	public static Map<Integer, PaquetePersonaje> getPersonajesConectados() {
		return personajesConectados;
	}

//	/**
//	 * @return devuelve el conector del servidor.
//	 */
//	public static Conector getConector() {
//		return conexionDB;
//	}
}
