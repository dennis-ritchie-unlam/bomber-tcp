package cliente;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import mensaje.PaqueteUsuario;

public class Cliente extends Thread {
	private Socket cliente;
	private String ip;
	private String ipServer;
	private int puerto;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	
	private final Gson gson = new Gson();
	
	private PaqueteUsuario paqueteUsuario;
	
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
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo al iniciar la conexión con el servidor");
			System.exit(1);
		}
	}
	
	public synchronized void run() {
		paqueteUsuario = new PaqueteUsuario();
		
		while(!paqueteUsuario.isInicioSesion()) {
			System.out.println("Estoy mostrando el menu");
		}
		
		System.out.println("Seleccione algo del menu");
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
}
