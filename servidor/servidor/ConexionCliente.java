package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.Gson;

import cliente.comando.Comando;
import mensaje.Paquete;
import mensaje.PaquetePersonaje;
import mensaje.PaqueteUsuario;
import servidor.comando.ComandoServer;

public class ConexionCliente extends Thread {

	private Socket socket;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;

	private PaquetePersonaje paquetePersonaje;
	private PaqueteUsuario paqueteUsuario;
	private final Gson gson = new Gson();

	public ConexionCliente(String ip, Socket socket, final ObjectInputStream entrada, final ObjectOutputStream salida)
			throws IOException {
		this.socket = socket;
		this.entrada = entrada;
		this.salida = salida;
	}

	public synchronized void run() {
		try {
			ComandoServer comando;
			Paquete paquete;
			PaqueteUsuario paqueteUsuario = new PaqueteUsuario();

			String cadenaLeida = (String) entrada.readObject();

			while (!((paquete = gson.fromJson(cadenaLeida, Paquete.class)).getComando() == Comando.DESCONECTAR)) {
				comando = (ComandoServer) paquete.getObjeto(Comando.NOMBREPAQUETE);
				comando.setCadena(cadenaLeida);
				comando.setEscuchaCliente(this);
				comando.ejecutar();
				cadenaLeida = (String) entrada.readObject();
			}

			entrada.close();
			salida.close();
			socket.close();

//			Servidor.getPersonajesConectados().remove(paquetePersonaje.getId());
//		    Servidor.getUbicacionPersonajes().remove(paquetePersonaje.getId());
//		    Servidor.getClientesConectados().remove(this);
//
//		    for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
//				paqueteDePersonajes = new PaqueteDePersonajes(Servidor.getPersonajesConectados());
//				paqueteDePersonajes.setComando(Comando.CONEXION);
//				conectado.salida.writeObject(gson.toJson(paqueteDePersonajes, PaqueteDePersonajes.class));
//		    }
		} catch (IOException | ClassNotFoundException e) {

		}
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public Gson getGson() {
		return gson;
	}

	public PaquetePersonaje getPaquetePersonaje() {
		return paquetePersonaje;
	}

	public void setPaquetePersonaje(final PaquetePersonaje paquetePersonaje) {
		this.paquetePersonaje = paquetePersonaje;
	}

	public PaqueteUsuario getPaqueteUsuario() {
		return paqueteUsuario;
	}

	public void setPaqueteUsuario(final PaqueteUsuario paqueteUsuario) {
		this.paqueteUsuario = paqueteUsuario;
	}
}
