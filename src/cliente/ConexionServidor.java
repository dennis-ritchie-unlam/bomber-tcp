package cliente;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexionServidor implements KeyListener{
    
    private Socket socket;
    private String usuario;
    private DataOutputStream salidaDatos;
    
    public ConexionServidor(Socket socket, String usuario) {
        this.socket = socket;
        this.usuario = usuario;
//        this.gson = gson;
        this.paqueteMovimiento = new PaqueteMovimiento();
        try {
            this.salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {

        } catch (NullPointerException ex) {
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        try {
            switch (arg0.getKeyCode()) {
            case KeyEvent.VK_UP:
                salidaDatos.writeUTF("1");
                break;
            case KeyEvent.VK_LEFT:
                salidaDatos.writeUTF("2");
                break;
            case KeyEvent.VK_DOWN:
                salidaDatos.writeUTF("3");
                break;
            case KeyEvent.VK_RIGHT:
                salidaDatos.writeUTF("4");
                break;
            case KeyEvent.VK_SPACE:
                salidaDatos.writeUTF("5");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        try {
            switch (arg0.getKeyCode()) {
            case KeyEvent.VK_UP:
                salidaDatos.writeUTF("7");
                break;
            case KeyEvent.VK_LEFT:
                salidaDatos.writeUTF("8");
                break;
            case KeyEvent.VK_DOWN:
                salidaDatos.writeUTF("9");
                break;
            case KeyEvent.VK_RIGHT:
                salidaDatos.writeUTF("10");
                break;
            case KeyEvent.VK_SPACE:
                salidaDatos.writeUTF("11");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    
}
