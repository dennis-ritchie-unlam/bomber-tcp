package cliente;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import cliente.comando.Comando;
import cliente.comando.ComandoCliente;
import frame.MenuJugar;
import mensaje.Paquete;
import mensaje.PaquetePersonaje;
import mensaje.PaqueteUsuario;
import sala.Sala;

public class Cliente extends Thread {
	private Socket cliente;
	private String ip;
	private String ipServer;
	private int puerto;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;

	private final Gson gson = new Gson();

	private PaqueteUsuario paqueteUsuario;
	private PaquetePersonaje paquetePersonaje;

	private int accion;

	public Cliente() {
		try {
			Properties propiedad = new Properties();
			propiedad.load(new FileInputStream("config.properties"));
			puerto = Integer.parseInt(propiedad.getProperty("PUERTO", "10000"));
			ipServer = propiedad.getProperty("IP", "localhost");
			cliente = new Socket(ipServer, puerto);
			ip = cliente.getInetAddress().getHostAddress();
			entrada = new ObjectInputStream(cliente.getInputStream());
			salida = new ObjectOutputStream(cliente.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo al iniciar la conexión con el servidor");
			System.exit(1);
		}
	}

	public synchronized void run() {
		try {
			paqueteUsuario = new PaqueteUsuario();
			ComandoCliente comand;

			MenuJugar menuJugar = null;
			while (!paqueteUsuario.isInicioSesion()) {
				if (menuJugar == null) {
					menuJugar = new MenuJugar(this);
					menuJugar.setVisible(true);

					// Creo los paquetes que le voy a enviar al servidor
					paqueteUsuario = new PaqueteUsuario();
					paquetePersonaje = new PaquetePersonaje();

					// Espero a que el usuario seleccione alguna accion
					wait();

					comand = (ComandoCliente) Paquete.getObjetoSet(Comando.NOMBREPAQUETE, getAccion());
					comand.setCadena(null);
					comand.setCliente(this);
					comand.ejecutar();

					// Le envio el paquete al servidor
					salida.writeObject(gson.toJson(paqueteUsuario));
					// Recibo el paquete desde el servidor
					String cadenaLeida = (String) entrada.readObject();
					Paquete paquete = gson.fromJson(cadenaLeida, Paquete.class);

					comand = (ComandoCliente) paquete.getObjeto(Comando.NOMBREPAQUETE);
					comand.setCadena(cadenaLeida);
					comand.setCliente(this);
					comand.ejecutar();
				}
			}

			while(paqueteUsuario.isInicioSesion()) {
				
			}
			Sala sala = new Sala("Principal");
			sala.entrar(this);

			System.out.println("me conecte a la sala");
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getAccion() {
		return accion;
	}

	public void setAccion(final int accion) {
		this.accion = accion;
	}

	public ObjectInputStream getEntrada() {
		return this.entrada;
	}

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public PaqueteUsuario getPaqueteUsuario() {
		return paqueteUsuario;
	}

	public void setPaqueteUsuario(PaqueteUsuario paqueteUsuario) {
		this.paqueteUsuario = paqueteUsuario;
	}

	public Gson getGson() {
		return gson;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}
	
    public void enviar(String s) {
        try {
        	
//		    juego.getCliente().getPaqueteMensaje().setUserEmisor(juego.getPersonaje().getNombre());
//		    juego.getCliente().getPaqueteMensaje().setUserReceptor(getTitle());
//		    juego.getCliente().getPaqueteMensaje().setMensaje(texto.getText());
//
//		    // MANDO EL COMANDO PARA QUE ENVIE EL MSJ
//		    juego.getCliente().getPaqueteMensaje().setComando(Comando.TALK);
//		    // El user receptor en espacio indica que es para todos
//		    if (getTitle() == "Sala") {
//			juego.getCliente().getPaqueteMensaje().setUserReceptor(null);
//		    }
//
//		    juego.getCliente().getSalida().writeObject(gson.toJson(juego.getCliente().getPaqueteMensaje()));
		    
		    
        	salida.writeObject(gson.toJson(s));
//        	
//            salida.write(s + "\n");
//            salida.flush();
        } catch (IOException ex) {
//            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
