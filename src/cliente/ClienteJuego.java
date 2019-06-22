package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entidades.Entidad;
import entidades.Mapa;
import servidor.ArchivoDePropiedades;

public class ClienteJuego extends JFrame {

    private Socket socket;
    private int puerto;
    private String host;
    private PanelGrafico contentPane;
    private Gson gson;
    private GsonBuilder builder;
    private DataInputStream entradaDatos;
    static final int ALTO = 21;
    static final int ANCHO = 21;
    static final int BLOCK_SIZE = 32;

    public ClienteJuego() {
        super("Bomberman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, ANCHO * BLOCK_SIZE + BLOCK_SIZE / 2, ALTO * BLOCK_SIZE + BLOCK_SIZE * 5 / 4);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Bomberman TCP");
        setSize(676, 700);
        this.setVisible(true);
        ArchivoDePropiedades archivo = new ArchivoDePropiedades("config.properties");
		archivo.lectura();
        puerto = archivo.getPuerto();
        host = archivo.getIp();
        contentPane = new PanelGrafico();
        setContentPane(contentPane);
        builder = new GsonBuilder();
        builder.registerTypeAdapter(Entidad.class, new InterfaceAdapter<Entidad>());
        gson = builder.create();
        try {
            socket = new Socket(host, puerto);
            entradaDatos = new DataInputStream(socket.getInputStream());
            PaqueteRecibido paquete = gson.fromJson(entradaDatos.readUTF(), PaqueteRecibido.class);
            contentPane.setMapa(paquete.getMapa());
            contentPane.setBombas(paquete.getBombas());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addKeyListener(new ConexionServidor(socket, host));
        
    }

    public void recibirMensajeServidor() {
        String mensaje;
        boolean conectado = true;
        while (conectado) {
            try {
                mensaje = entradaDatos.readUTF();
                PaqueteRecibido paquete = gson.fromJson(mensaje, PaqueteRecibido.class);
                contentPane.setMapa(paquete.getMapa());
                contentPane.setBombas(paquete.getBombas());
                contentPane.repaint();
            } catch (IOException e) {
                conectado = false;
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ClienteJuego c = new ClienteJuego();
        c.setVisible(true);
        Thread hilo = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(45);
                        c.recibirMensajeServidor();
                    } catch (InterruptedException e) {
                        Logger.getLogger("Error");
                    }
                    c.contentPane.repaint();
                }

            }
        });

        hilo.start();
    }
}