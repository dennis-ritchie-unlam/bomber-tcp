package cliente;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import paquete.PaqueteMovimiento;

public class ConexionServidor implements KeyListener{
    
    private Socket socket;
    private String usuario;
    private DataOutputStream salidaDatos;
	private boolean[] direccionesKeyPressed;
	private boolean bombKeyPressed;
	private Gson gson;
    
    public ConexionServidor(Socket socket, String usuario) {
        this.socket = socket;
        this.usuario = usuario;
        direccionesKeyPressed = new boolean[4];
        gson = new Gson();
        try {
            this.salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {

        } catch (NullPointerException ex) {
        }
    }
    
    @Override
	public void keyPressed(KeyEvent e) {
		try {
			addKeyPressed(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    
	@Override
	public void keyReleased(KeyEvent e) {
		try {
			removeKeyPressed(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    
	private void removeKeyPressed(KeyEvent evento) throws IOException {
			switch (evento.getKeyCode()) {
			case KeyEvent.VK_UP:
				direccionesKeyPressed[0] = false;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_LEFT:
				direccionesKeyPressed[1] = false;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_DOWN:
				direccionesKeyPressed[2] = false;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_RIGHT:
				direccionesKeyPressed[3] = false;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_SPACE:
				bombKeyPressed = false;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
		}
	}

	private void addKeyPressed(KeyEvent evento) throws IOException {
			switch (evento.getKeyCode()) {
			case KeyEvent.VK_UP:
				direccionesKeyPressed[0] = true;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_LEFT:
				direccionesKeyPressed[1] = true;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_DOWN:
				direccionesKeyPressed[2] = true;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_RIGHT:
				direccionesKeyPressed[3] = true;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
				break;
			case KeyEvent.VK_SPACE:
				bombKeyPressed = true;
				salidaDatos.writeUTF(gson.toJson(new PaqueteMovimiento("2", direccionesKeyPressed, bombKeyPressed), PaqueteMovimiento.class));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
