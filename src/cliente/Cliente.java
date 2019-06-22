package cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import frame.MenuInicio;
import panel.PanelJuego;
import panel.PanelSession;
import paquete.Paquete;
import paquete.PaqueteJuego;
import paquete.PaqueteMovimiento;
import paquete.PaqueteSession;
import servidor.ArchivoDePropiedades;

public class Cliente extends Thread {

	private Socket socket;
	private int puerto;
	private String host;
	private Gson gson;
	private GsonBuilder builder;
	private DataInputStream entradaDatos;
	private DataOutputStream salidaDatos;

	boolean conectado = false;
	private String accion;

	private ConexionServidor conexionServidor;

	private PanelSession contentPaneSession;
	private PanelJuego contentPaneJuego;

	public Cliente() {
		super("Bomberman");
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
		synchronized (this) {
//		JOptionPane.showMessageDialog(null,"recibirMensajeServidor");
			try {
				String mensaje = null;
				entradaDatos = new DataInputStream(socket.getInputStream());
				salidaDatos = new DataOutputStream(socket.getOutputStream());
				Paquete paquete = new Paquete();
				salidaDatos.writeUTF(gson.toJson(paquete, Paquete.class));
				String datos = entradaDatos.readUTF();
				String comando = paquete.getComando();
				if (!conectado) {
					try {
						mensaje = datos;
						PaqueteSession paqueteSession = gson.fromJson(mensaje, PaqueteSession.class);
						MenuInicio menu = new MenuInicio(this, paqueteSession);
						menu.setVisible(true);
						wait();
						
						/** 
						 * Enviar al servidor los paquetes
						 */
				    	salidaDatos.writeUTF(gson.toJson(paqueteSession, PaqueteSession.class));
				    	entradaDatos = new DataInputStream(socket.getInputStream());

				    	entradaDatos.readUTF();
				    	datos = entradaDatos.readUTF();
				    	paqueteSession = gson.fromJson(datos, PaqueteSession.class);
				    	
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
//					mensaje = entradaDatos.readUTF();
//					PaqueteJuego paqueteJuego = gson.fromJson(mensaje, PaqueteJuego.class);
//					this.removeAll();
//					contentPaneJuego.setMapa(paqueteJuego.getMapa());
//					contentPaneJuego.setBombas(paqueteJuego.getBombas());
//					contentPaneJuego.repaint();
//					if(!this.getKeyListeners().equals(conexionServidor)) {
//						this.addKeyListener(conexionServidor);
//					}
//					contentPaneJuego.setVisible(true);
//					setContentPane(contentPaneJuego);
					}
				}

			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "UnknownHostException");
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "IOException");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Cliente c = new Cliente();
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
				}

			}
		});

		hilo.start();
	}
}
