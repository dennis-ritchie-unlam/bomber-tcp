package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import entidades.Mapa;

public class ServidorJuego {
	public static ArrayList<Sala> listadoSalas;
	
	public static void main(String[] args) {
		int maximoConexiones = 4;
		ServerSocket servidor = null;
		Socket socket = null;
		Mensaje mensajes = new Mensaje();
		Mapa mapa = new Mapa();
		

		try {
			ArchivoDePropiedades archivo = new ArchivoDePropiedades("config.properties");
			archivo.lectura();
			int puerto = archivo.getPuerto();
			servidor = new ServerSocket(puerto, maximoConexiones);
			System.out.println("Servidor funcionando en el puerto: " + puerto);
			listadoSalas = new ArrayList<>();
	        Sala sala = new Sala("Principal");
	        agregarSala(sala);
			while (true) {
				socket = servidor.accept();
				System.out.println("Nuevo usuario se ha conectado");
				ConexionCliente cc = new ConexionCliente(socket, mensajes, mapa);
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
	
	public static void agregarSala(Sala s) {
        //if (obtenerSala(s.getNombre()) == null) {
            listadoSalas.add(s);
            System.out.println(("Se ha creado la sala con nombre " + s.getNombre()));
//            for(Sala sa: listadoSalas) {
//            	sa.difundirSalas();
//            }
        //}
    }
}
