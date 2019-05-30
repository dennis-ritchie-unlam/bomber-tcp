package main.dominio;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.Timer;

public class JVentanaGrafica extends JFrame {

	private JPanelGrafico contentPane;

	private Colisionador colisionador;

	public JVentanaGrafica() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		contentPane = new JPanelGrafico();
		setContentPane(contentPane);
		setBounds(0, 0, Mapa.ANCHO * contentPane.BLOCK_SIZE + contentPane.BLOCK_SIZE / 2,
				Mapa.ALTO * contentPane.BLOCK_SIZE + contentPane.BLOCK_SIZE * 5 / 4);
		setLocationRelativeTo(null);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				run(arg0);
			}
		});

		colisionador = new Colisionador(contentPane.getMapa());
	}

    public void run(KeyEvent evento) {
        Bomber nuevoBomber = contentPane.getBomber();

        int cantPosMover = 32;
        int posX = nuevoBomber.getPosicionX();
        int posY = nuevoBomber.getPosicionY();
        
        switch (evento.getKeyCode()) {
        case KeyEvent.VK_DOWN:
            posY = (nuevoBomber.getPosicionY() + cantPosMover) / contentPane.BLOCK_SIZE;

            if (!colisionador.verificarColision(posX / 32, posY)) {
                nuevoBomber.moverse(0, cantPosMover);
            }
            contentPane.setBomberIcon(0, posY);
            contentPane.setBomber(nuevoBomber);
            break;
        case KeyEvent.VK_UP:
            posY = (nuevoBomber.getPosicionY() - cantPosMover) / contentPane.BLOCK_SIZE;

            if (!colisionador.verificarColision(posX / 32, posY)) {
                nuevoBomber.moverse(0, -cantPosMover);
            }
            contentPane.setBomberIcon(1, posY);
            contentPane.setBomber(nuevoBomber);
            break;
        case KeyEvent.VK_LEFT:
            posX = (nuevoBomber.getPosicionX() - cantPosMover) / contentPane.BLOCK_SIZE;

            if (!colisionador.verificarColision(posX, posY / 32)) {
                nuevoBomber.moverse(-cantPosMover, 0);
            }
            contentPane.setBomberIcon(2, posX);
            contentPane.setBomber(nuevoBomber);
            break;
        case KeyEvent.VK_RIGHT:
            posX = (nuevoBomber.getPosicionX() + cantPosMover) / contentPane.BLOCK_SIZE;

            if (!colisionador.verificarColision(posX, posY / 32)) {
                nuevoBomber.moverse(cantPosMover, 0);
            }
            contentPane.setBomberIcon(3, posX);
            contentPane.setBomber(nuevoBomber);
            break;
        case KeyEvent.VK_SPACE:
            Bomba bombita = nuevoBomber.ponerBomba();
            if(bombita != null) {
            	contentPane.setBomba(bombita);
                timearBomba(bombita.getTiempoExplosion() * 1000, nuevoBomber);
            }
            break;
        }
    }
	
	private void timearBomba(int tiempoMili, Bomber bomber) {
	    Timer timer = new Timer(tiempoMili,new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.getMapa().explotarBomba(contentPane.getBomba());
                contentPane.setBomba(null);
                bomber.setBombasDisponibles(bomber.getBombasDisponibles() + 1);
            }
        });
	    
	    timer.start();
	}
	
	public static void main(String[] args) {
		JVentanaGrafica ventana = new JVentanaGrafica();
		ventana.setVisible(true);
		Thread hilo = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						Logger.getLogger("Error");
					}
					ventana.contentPane.repaint();
				}

			}
		});

		hilo.start();
	}
}
