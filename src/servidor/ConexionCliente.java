package servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
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
    final int BLOCK_SIZE = 32;

    public ConexionCliente(Socket socket, Mensaje mensajes, Mapa mapa, Sala sala) {
        try {
            this.mensaje = mensajes;
            this.socket = socket;
            this.entradaDatos = new DataInputStream(socket.getInputStream());
            this.salidaDatos = new DataOutputStream(socket.getOutputStream());
            gson = new Gson();
            this.mapa = mapa;
            bomber = new Bomber(32, 32);
            colisionador = new Colisionador(this.mapa);
            bombas = new ArrayList<Bomba>();
            this.mapa.añadirBomber(bomber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String mensajeRecibido;
        boolean conectado = true;
        mensaje.addObserver(this);

        while (conectado) {
            try {
                mensajeRecibido = entradaDatos.readUTF();
                accion(Integer.parseInt(mensajeRecibido));
                mensaje.setMensaje(gson.toJson(mapa));
            } catch (IOException e) {
                conectado = false;
                e.printStackTrace();
                try {
                    entradaDatos.close();
                    salidaDatos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void accion(int comando) {

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

//                contentPane.setBomberIcon(0, posY);
//                contentPane.setBomber(nuevoBomber);
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
//                contentPane.setBomberIcon(1, posY);
//                contentPane.setBomber(nuevoBomber);
            } else if (comando == 2) {
                posX = (nuevoBomber.getPosicionX() - cantPosMover) / BLOCK_SIZE;
                if (posY % BLOCK_SIZE == 0) {
                    posY /= BLOCK_SIZE;
                } else {
                    posY = (nuevoBomber.getPosicionX() - cantPosMover) / BLOCK_SIZE;
                }
                if (!colisionador.verificarColision(posX, posY))
                    nuevoBomber.moverse(-cantPosMover, 0);
//                contentPane.setBomberIcon(2, posX);
//                contentPane.setBomber(nuevoBomber);
            } else if (comando == 4) {
                posX = (nuevoBomber.getPosicionX() + cantPosMover * 4) / BLOCK_SIZE;
                if (posY % BLOCK_SIZE == 0) {
                    posY /= BLOCK_SIZE;
                } else {
                    posY = (nuevoBomber.getPosicionX() + cantPosMover * 4) / BLOCK_SIZE;
                }

                if (!colisionador.verificarColision(posX, posY))
                    nuevoBomber.moverse(cantPosMover, 0);
//                contentPane.setBomberIcon(3, posX);
//                contentPane.setBomber(nuevoBomber);
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
            if (this.mapa.getEntidades()[bomba.getPosicionY() / 32][bomba.getPosicionX() / 32] == null) {
                this.mapa.añadirBomba(bomba);
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
                mensaje.setMensaje(gson.toJson(mapa));
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
