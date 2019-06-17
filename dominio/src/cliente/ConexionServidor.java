package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.Gson;

public class ConexionServidor extends Thread {

	private Socket socket;
	private ObjectInputStream entradaDatos;
	private ObjectOutputStream salidaDatos;
	
    private final Gson gson = new Gson();
	
	public ConexionServidor(Socket socket) {
		this.socket = socket;

		try {
			entradaDatos = new ObjectInputStream(socket.getInputStream());
			salidaDatos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException ex) {
		}
	}
	
	public synchronized void run() {
		try {
			
		} catch(IOException e) {
			
		}
	}
}
