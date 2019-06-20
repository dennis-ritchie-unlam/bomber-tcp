package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorJuego {

	public static void main(String[] args) {
		int maximoConexiones = 4;
		ServerSocket servidor = null;
		Socket socket = null;
		Mensaje mensajes = new Mensaje();

		try {
			ArchivoDePropiedades archivo = new ArchivoDePropiedades("config.properties");
			archivo.lectura();
			int puerto = archivo.getPuerto();
			servidor = new ServerSocket(puerto, maximoConexiones);
			System.out.println("Servidor funcionando en el puerto: " + puerto);
			while (true) {
				socket = servidor.accept();
				System.out.println("Nuevo usuario se ha conectado");
				 ConexionCliente cc = new ConexionCliente(socket, mensajes);
				 cc.start();
			}
		} catch (IOException e) {
			System.err.println("Error en el servidor");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				servidor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
