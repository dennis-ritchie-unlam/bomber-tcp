package cliente;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import comando.*;
import paquete.Paquete;
import paquete.PaqueteMovimiento;

public class ConexionServidor implements KeyListener{
    
    private Socket socket;
    private String usuario;
    private DataOutputStream salidaDatos;
    private Gson gson;
    private PaqueteMovimiento paqueteMovimiento;
    
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
            	escribirToJson(Comando.VK_UP);
                break;
            case KeyEvent.VK_LEFT:
            	escribirToJson(Comando.VK_LEFT);
                break;
            case KeyEvent.VK_DOWN:
            	escribirToJson(Comando.VK_DOWN);
                break;
            case KeyEvent.VK_RIGHT:
            	escribirToJson(Comando.VK_RIGHT);
                break;
            case KeyEvent.VK_SPACE:
            	escribirToJson(Comando.VK_SPACE);

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
            	escribirToJson("7");
                break;
            case KeyEvent.VK_LEFT:
            	escribirToJson("8");
                break;
            case KeyEvent.VK_DOWN:
            	escribirToJson("9");
                break;
            case KeyEvent.VK_RIGHT:
            	escribirToJson("10");
                break;
            case KeyEvent.VK_SPACE:
            	escribirToJson("11");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    
    public void escribirToJson(String cadena) throws IOException {
    	paqueteMovimiento.setComando(cadena);
    	salidaDatos.writeUTF(gson.toJson(paqueteMovimiento, PaqueteMovimiento.class));
    }
    
}
