package main.dominio;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class JVentanaGrafica extends JFrame {

	private JPanelGrafico contentPane;

	private Colisionador colisionador;

	public JVentanaGrafica() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanelGrafico();
		setContentPane(contentPane);
		setBounds(0, 0, Mapa.ANCHO * contentPane.BLOCK_SIZE + contentPane.BLOCK_SIZE / 2,
				Mapa.ALTO * contentPane.BLOCK_SIZE + contentPane.BLOCK_SIZE * 5 / 4);
		setLocationRelativeTo(null);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				setMovimiento(arg0);
			}
		});

		colisionador = new Colisionador(contentPane.getMapa());
	}

	public void setMovimiento(KeyEvent evento) {
		Bomber nuevoBomber = contentPane.getBomber();

		double cantPosMover = 5;
		double moverse = cantPosMover / contentPane.BLOCK_SIZE;
		double posX;
		double posY;

		switch(evento.getKeyCode()) {
			case KeyEvent.VK_DOWN: 
				posX = nuevoBomber.getPosicionX();
				posY = nuevoBomber.getPosicionY() + moverse;

				if (!colisionador.verificarColision( (int) Math.round(posX), (int) Math.ceil(posY))) {
					nuevoBomber.moverse(0, moverse);
				}
				contentPane.setBomberIcon(0, posY);
				contentPane.setBomber(nuevoBomber);
			break;
			case KeyEvent.VK_UP:
				posX = nuevoBomber.getPosicionX();
				posY = nuevoBomber.getPosicionY() - moverse;

				if (!colisionador.verificarColision( (int) Math.round(posX), (int) Math.floor(posY))) {
					nuevoBomber.moverse(0, -moverse);
				}
				contentPane.setBomberIcon(1, posY);
				contentPane.setBomber(nuevoBomber);
			break;
			case KeyEvent.VK_LEFT:
				posX = nuevoBomber.getPosicionX() - moverse;
				posY = nuevoBomber.getPosicionY();

				if (!colisionador.verificarColision( (int) Math.floor(posX), (int) Math.round(posY + 0.4))) {
					nuevoBomber.moverse(-moverse, 0);
				}
				contentPane.setBomberIcon(2, posX);
				contentPane.setBomber(nuevoBomber);
				break;
			case KeyEvent.VK_RIGHT:
				posX = nuevoBomber.getPosicionX() + moverse;
				posY = nuevoBomber.getPosicionY();

				if (!colisionador.verificarColision((int) Math.ceil(posX), (int) Math.round(posY + 0.4))) {
					nuevoBomber.moverse(moverse, 0);
				}
				contentPane.setBomberIcon(3, posX);
				contentPane.setBomber(nuevoBomber);
				break;
			case KeyEvent.VK_SPACE:
				Bomba bombita = nuevoBomber.ponerBomba();
				if(bombita != null) {
					contentPane.setBomba(bombita);
				}
				break;
		}
		repaint();
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
