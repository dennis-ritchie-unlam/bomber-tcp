package cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import comando.Comando;
import entidades.Entidad;
import panel.PanelJuego;
import panel.PanelSession;
import paquete.Paquete;
import paquete.PaqueteJuego;
import paquete.PaqueteSession;
import servidor.ArchivoDePropiedades;

public class ClienteVentanaMonstruo extends JFrame {

	private Socket socket;
	private int puerto;
	private String host;
	private Gson gson;
	private GsonBuilder builder;
	private DataInputStream entradaDatos;
	static final int ALTO = 21;
	static final int ANCHO = 21;
	static final int BLOCK_SIZE = 32;
	boolean conectado = false;
	private String accion;
	
	
	
	private static final long serialVersionUID = 1L;
	private static final int ALTO_MENU = 300;
	private static final int ANCHO_MENU = 450;
	private static final int Y_MENU = 100;
	private static final int X_MENU = 100;
	private static final int BORDER = 5;
	private static final int ALTO_PANE = 271;
	private static final int ANCHO_PANE = 444;
	private static final int ALTO_LB_PASS = 21;
	private static final int ANCHO_LB_PASS = 68;
	private static final int Y_LB_PASS = 118;
	private static final int X_LB_PASS = 111;
	private static final int ALTO_USUARIO = 23;
	private static final int ANCHO_USUARIO = 55;
	private static final int Y_USUARIO = 66;
	private static final int X_USUARIO = 111;
	private static final int ALTO_LB_INGRESAR = 23;
	private static final int ANCHO_LB_INGRESAR = 68;
	private static final int Y_LB_INGRESAR = 183;
	private static final int X_LB_INGRESAR = 193;
	private static final int TAM_TXT = 15;
	private static final int TAM_TXT_PASSWORD = 11;
	private static final int ALTO_TF = 20;
	private static final int ANCHO_TF = 118;
	private static final int Y_TF = 69;
	private static final int X_TF = 198;
	private static final int COLUMNAS = 10;
	private static final int ALTO_PASS = 20;
	private static final int ANCHO_PASS = 118;
	private static final int Y_PASS = 119;
	private static final int X_PASS = 198;
	private static final int ALTO_BTN_CONECTAR = 23;
	private static final int ANCHO_BTN_CONECTAR = 153;
	private static final int Y_BTN_CONECTAR = 182;
	private static final int X_BTN_CONECTAR = 141;
	private static final int ALTO_BACK = 271;
	private static final int ANCHO_BACK = 444;
	
	private JTextField textField;
	private JPasswordField passwordField;
	
	
	
	
	
	private ConexionServidor conexionServidor;
	
	private PanelSession contentPaneSession;
	private PanelJuego contentPaneJuego;

	public ClienteVentanaMonstruo() {
		super("Bomberman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, ANCHO * BLOCK_SIZE + BLOCK_SIZE / 2, ALTO * BLOCK_SIZE + BLOCK_SIZE * 5 / 4);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Bomberman TCP");
		setSize(676, 700);
		this.setVisible(true);
		ArchivoDePropiedades archivo = new ArchivoDePropiedades("config.properties");
		archivo.lectura();
		puerto = archivo.getPuerto();
		host = archivo.getIp();

		builder = new GsonBuilder();
		builder.registerTypeAdapter(Entidad.class, new InterfaceAdapter<Entidad>());
		gson = builder.create();
		try {      
            socket = new Socket(host, puerto);
            contentPaneJuego = new PanelJuego();
            contentPaneSession = new PanelSession(this);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//		JOptionPane.showMessageDialog(null, "CREO CLIENTE");
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getAccion() {
		return this.accion;
	}

	public void recibirMensajeServidor() throws InterruptedException {

//		JOptionPane.showMessageDialog(null,"recibirMensajeServidor");
		try {
			String mensaje = null;			
			entradaDatos = new DataInputStream(socket.getInputStream());
			String datos = entradaDatos.readUTF();
//			JOptionPane.showMessageDialog(null,"datos " + datos);
			Paquete paquete = gson.fromJson(datos, Paquete.class);
			String comando = paquete.getComando();
//			JOptionPane.showMessageDialog(null,"kasdasd");
			if (!conectado) {
				try {
//					JOptionPane.showMessageDialog(null,"USUARIO SIN INICIAR SESION");
					mensaje = entradaDatos.readUTF();
					PaqueteSession paqueteSession = gson.fromJson(mensaje, PaqueteSession.class);
//					JOptionPane.showMessageDialog(null,"ANTES DE PINTAR INICIAR SESION");
					//this.removeAll();
					
					
					
					contentPaneSession.setLayout(null);
					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.setBounds(0, 0, 200, ALTO_PANE);
					contentPaneSession.add(layeredPane);

					JLabel lblPassword = new JLabel("Password");
					lblPassword.setBounds(X_LB_PASS, Y_LB_PASS, ANCHO_LB_PASS, ALTO_LB_PASS);
					layeredPane.add(lblPassword);
					lblPassword.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));
					lblPassword.setForeground(Color.WHITE);

					JLabel lblUsuario = new JLabel("Usuario");
					lblUsuario.setBounds(X_USUARIO, Y_USUARIO, ANCHO_USUARIO, ALTO_USUARIO);
					layeredPane.add(lblUsuario, new Integer(2));
					lblUsuario.setForeground(Color.WHITE);
					lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));

					JLabel lblIngresar = new JLabel("Ingresar");
					lblIngresar.setBounds(X_LB_INGRESAR, Y_LB_INGRESAR, ANCHO_LB_INGRESAR, ALTO_LB_INGRESAR);
					layeredPane.add(lblIngresar, new Integer(2));
					lblIngresar.setForeground(Color.WHITE);
					lblIngresar.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));

					textField = new JTextField();
//					textField.addActionListener(new ActionListener() {
//						@Override
//						public void actionPerformed(final ActionEvent arg0) {
//							logIn(cliente);
//						}
//					});
					textField.setBounds(X_TF, Y_TF, ANCHO_TF, ALTO_TF);
					layeredPane.add(textField, new Integer(1));
					textField.setColumns(COLUMNAS);

					passwordField = new JPasswordField();
//					passwordField.addActionListener(new ActionListener() {
//						@Override
//						public void actionPerformed(final ActionEvent e) {
//							logIn(cliente);
//						}
//					});
					passwordField.setBounds(X_PASS, Y_PASS, ANCHO_PASS, ALTO_PASS);
					layeredPane.add(passwordField, new Integer(1));
					passwordField.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT_PASSWORD));
					
					contentPaneSession.setPaqueteSession(paqueteSession);
					contentPaneSession.repaint();
					contentPaneSession.setVisible(true);
//					JOptionPane.showMessageDialog(null,"DESPUES DE PINTAR INICIAR SESION");
					setContentPane(contentPaneSession);
					this.repaint();
					this.setBackground(new Color(0));
					this.wait();
					conectado = paqueteSession.esValido();
				} catch (IOException e) {
					conectado = false;
					e.printStackTrace();
				}
			} else {
//				JOptionPane.showMessageDialog(null,"USUARIO LOGEADO");
				comando = (comando == null || comando.isEmpty()) ? "" : comando;
				switch (comando) {
				case Comando.INICIAR_SESION:
					break;
				case Comando.CERRAR_SESION:
					break;
				default:
					/*
					 * Abrir ventana de Juego
					 */
					mensaje = entradaDatos.readUTF();
					PaqueteJuego paqueteJuego = gson.fromJson(mensaje, PaqueteJuego.class);
					this.removeAll();
					contentPaneJuego.setMapa(paqueteJuego.getMapa());
					contentPaneJuego.setBombas(paqueteJuego.getBombas());
					contentPaneJuego.repaint();
					if(!this.getKeyListeners().equals(conexionServidor)) {
						this.addKeyListener(conexionServidor);
					}
					contentPaneJuego.setVisible(true);
					setContentPane(contentPaneJuego);
				}
			}

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null,"UnknownHostException");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"IOException");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClienteVentanaMonstruo c = new ClienteVentanaMonstruo();
		c.setVisible(true);
		Thread hilo = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(45);
						c.recibirMensajeServidor();
					} catch (InterruptedException e) {
						Logger.getLogger("Error");
					}
					c.getContentPane().repaint();
				}

			}
		});

		hilo.start();
	}
}
