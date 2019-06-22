package servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import com.google.gson.Gson;
import entidades.Bomba;
import entidades.Bomber;
import entidades.Colisionador;
import entidades.Mapa;

public class ConexionCliente extends Thread implements Observer {
    private Socket socket;
    private Mensaje mensaje;
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;
    private Gson gson;
    private Bomber bomber;
    private Mapa mapa;
    private Colisionador colisionador;
    private ArrayList<Bomba> bombas;
    private Usuario user;
    private boolean[] direccion;
    final int BLOCK_SIZE = 32;

    public ConexionCliente(Socket socket, Mensaje mensajes, Mapa mapa/*, Sala sala*/) {
        try {
            this.mensaje = mensajes;
            this.socket = socket;
            this.entradaDatos = new DataInputStream(socket.getInputStream());
            this.salidaDatos = new DataOutputStream(socket.getOutputStream());
            gson = new Gson();
            salidaDatos.writeUTF(gson.toJson(new PaqueteEnviado(mapa, bombas)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void ingresoValido() {
        this.mapa = mapa;
        bomber = new Bomber(32, 32);
        colisionador = new Colisionador(this.mapa);
        bombas = new ArrayList<Bomba>();
        direccion = new boolean[4];
        this.mapa.añadirBomber(bomber);
    }
    
    public boolean validarSesion(String usuario, String contraseña) throws FileNotFoundException {
    	LAT lector = new LAT();
    	HashMap<String, String> mapaDeUsuarios = lector.leerArch("archivoSeguroYEncriptado.txt");
    	
        for (String key : mapaDeUsuarios.keySet()) {
        	if(usuario.equals(key) && contraseña.equals(mapaDeUsuarios.get(key)))
        		return true;
        }
        return false;
    	    	
    }

    @Override
    public void run() {
        String mensajeRecibido;
        boolean conectado = true;
        mensaje.addObserver(this);

        while (conectado) {
            try {
                mensajeRecibido = entradaDatos.readUTF();
                accion(Integer.parseInt(mensajeRecibido), direccion);
                mensaje.setMensaje(gson.toJson(new PaqueteEnviado(mapa, bombas)));
            } catch (IOException e) {
                conectado = false;
                JOptionPane.showMessageDialog(null, "El cliente ha salido del servidor");
                try {
                    entradaDatos.close();
                    salidaDatos.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
        }
    }
    
    private void refrescar() {
    	Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	mensaje.setMensaje(gson.toJson(new PaqueteEnviado(mapa, bombas)));
            }
        });

        timer.start();
	}

    public void accion(int comando, boolean[] direccion) {

        Bomber nuevoBomber = bomber;
        int posX = nuevoBomber.getPosicionX();
        int posY = nuevoBomber.getPosicionY();
        int cantPosMover = 8;
        if (nuevoBomber.EstaVivo()) {

            if (comando == 5) {
                if (nuevoBomber.getBombasDisponibles() > 0
                        && !colisionador.verificarColision(posX / BLOCK_SIZE, posY / BLOCK_SIZE)) {
                    Bomba bombita = nuevoBomber.ponerBomba();
                    if (bombita != null) {
                        addBomba(bombita);

                        timearBomba(bombita.getTiempoExplosion() * 1000, nuevoBomber, bombita);
                    }
                }

            }

            if (comando == 3) {

                posY = (nuevoBomber.getPosicionY() + cantPosMover * 4) / BLOCK_SIZE;
                if (posX % BLOCK_SIZE == 0) {
                    posX /= BLOCK_SIZE;
                } else {
                    posX = (nuevoBomber.getPosicionY() + cantPosMover * 4) / BLOCK_SIZE;
                }
                if (!colisionador.verificarColision(posX, posY))
                    nuevoBomber.moverse(0, cantPosMover);
                nuevoBomber.setDireccion(0);
            } else if (comando == 1) {

                posY = (nuevoBomber.getPosicionY() - cantPosMover) / BLOCK_SIZE;
                if (posX % BLOCK_SIZE == 0) {
                    posX /= BLOCK_SIZE;
                } else {
                    posX = (nuevoBomber.getPosicionY() - cantPosMover) / BLOCK_SIZE;
                }
                if (!colisionador.verificarColision(posX, posY)) {
                    nuevoBomber.moverse(0, -cantPosMover);
                }
                nuevoBomber.setDireccion(1);
            } else if (comando == 2) {
                posX = (nuevoBomber.getPosicionX() - cantPosMover) / BLOCK_SIZE;
                if (posY % BLOCK_SIZE == 0) {
                    posY /= BLOCK_SIZE;
                } else {
                    posY = (nuevoBomber.getPosicionX() - cantPosMover) / BLOCK_SIZE;
                }
                if (!colisionador.verificarColision(posX, posY))
                    nuevoBomber.moverse(-cantPosMover, 0);
                nuevoBomber.setDireccion(2);
            } else if (comando == 4) {
                posX = (nuevoBomber.getPosicionX() + cantPosMover * 4) / BLOCK_SIZE;
                if (posY % BLOCK_SIZE == 0) {
                    posY /= BLOCK_SIZE;
                } else {
                    posY = (nuevoBomber.getPosicionX() + cantPosMover * 4) / BLOCK_SIZE;
                }

                if (!colisionador.verificarColision(posX, posY))
                    nuevoBomber.moverse(cantPosMover, 0);
                nuevoBomber.setDireccion(3);
            }

        }
    }

    private void addBomba(Bomba bomba) {
        if (bomba != null) {
            if (bomba.getPosicionX() % BLOCK_SIZE <= BLOCK_SIZE / 2)
                bomba.setPosicionX(bomba.getPosicionX() - bomba.getPosicionX() % BLOCK_SIZE);
            else
                bomba.setPosicionX(bomba.getPosicionX() + (bomba.getPosicionX() % BLOCK_SIZE) / 3);

            if (bomba.getPosicionY() % BLOCK_SIZE <= BLOCK_SIZE / 2)
                bomba.setPosicionY(bomba.getPosicionY() - bomba.getPosicionY() % BLOCK_SIZE);
            else
                bomba.setPosicionY(bomba.getPosicionY() + (bomba.getPosicionY() % BLOCK_SIZE) / 3);
            if (this.mapa.getEntidades()[bomba.getPosicionY() / BLOCK_SIZE][bomba.getPosicionX() / BLOCK_SIZE] == null) {
                this.mapa.añadirBomba(bomba, BLOCK_SIZE);
                bombas.add(bomba);
            }
        }
    }

    private void timearBomba(int tiempoMili, Bomber bomber, Bomba bomba) {
        Timer timer = new Timer(tiempoMili, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mapa.explotarBomba(bomba, BLOCK_SIZE);
                bomber.setBombasDisponibles(bomber.getBombasDisponibles() + 1);
                mensaje.setMensaje(gson.toJson(new PaqueteEnviado(mapa, bombas)));
                bombas.remove(bomba);
            }
        });

        timer.start();
        timer.setRepeats(false);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            salidaDatos.writeUTF(arg.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
