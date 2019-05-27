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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanelGrafico();
		setContentPane(contentPane);
		setBounds(0, 0, 800, 600);
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
		double moverse = cantPosMover / contentPane.blockSize;

		if (evento.getKeyCode() == KeyEvent.VK_DOWN) {
			double posX = nuevoBomber.getPosicionX();
			double posY = nuevoBomber.getPosicionY() + moverse;

			if (!colisionador.verificarColision( (int) Math.round(posX), (int) Math.ceil(posY))) {
				nuevoBomber.moverse(0, moverse);
			}
			contentPane.setBomberIcon(0, posY);
			contentPane.setBomber(nuevoBomber);
		}
		if (evento.getKeyCode() == KeyEvent.VK_UP) {
			double posX = nuevoBomber.getPosicionX();
			double posY = nuevoBomber.getPosicionY() - moverse;

			if (!colisionador.verificarColision( (int) Math.round(posX), (int) Math.floor(posY))) {
				nuevoBomber.moverse(0, -moverse);
			}
			contentPane.setBomberIcon(1, posY);
			contentPane.setBomber(nuevoBomber);
		}
		if (evento.getKeyCode() == KeyEvent.VK_LEFT) {
			double posX = nuevoBomber.getPosicionX() - moverse;
			double posY = nuevoBomber.getPosicionY();

			if (!colisionador.verificarColision( (int) Math.floor(posX), (int) Math.round(posY + 0.45))) {
				nuevoBomber.moverse(-moverse, 0);
			}
			contentPane.setBomberIcon(2, posX);
			contentPane.setBomber(nuevoBomber);
		}
		if (evento.getKeyCode() == KeyEvent.VK_RIGHT) {
			double posX = nuevoBomber.getPosicionX() + moverse;
			double posY = nuevoBomber.getPosicionY();

			if (!colisionador.verificarColision((int) Math.ceil(posX), (int) Math.round(posY + 0.45))) {
				nuevoBomber.moverse(moverse, 0);
			}
			contentPane.setBomberIcon(3, posX);
			contentPane.setBomber(nuevoBomber);
		}
		if (evento.getKeyCode() == KeyEvent.VK_SPACE) {
			contentPane.setBomba(new Bomba(nuevoBomber.getPosicionX(), nuevoBomber.getPosicionY()));
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
