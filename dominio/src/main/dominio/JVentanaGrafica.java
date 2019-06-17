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
		contentPane = new JPanelGrafico();
		setContentPane(contentPane);
		setBounds(0, 0, Mapa.ANCHO * contentPane.BLOCK_SIZE + contentPane.BLOCK_SIZE / 2,
				Mapa.ALTO * contentPane.BLOCK_SIZE + contentPane.BLOCK_SIZE * 5 / 4);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Bomberman TCP");
		setSize(676, 700);
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
		if (nuevoBomber.EstaVivo()) {
			int cantPosMover = 8;
			int posX = nuevoBomber.getPosicionX();
			System.out.println("PosX: " + posX + "\n");
			int posY = nuevoBomber.getPosicionY();
			System.out.println("PosY: " + posY + "\n");

			switch (evento.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				posY = (nuevoBomber.getPosicionY() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
				System.out.println("NPos Y down: " + posY);
				if (posX % contentPane.BLOCK_SIZE == 0) {
					posX /= contentPane.BLOCK_SIZE;
					System.out.println("NPos X if down: " + posX + "\n");
				}
				else {
					posX = (nuevoBomber.getPosicionY() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
					System.out.println("NPos X else down: " + posX + "\n");
				}
				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(0, cantPosMover);

				contentPane.setBomberIcon(0, posY);
				contentPane.setBomber(nuevoBomber);
				break;
			case KeyEvent.VK_UP:
				posY = (nuevoBomber.getPosicionY() - cantPosMover) / contentPane.BLOCK_SIZE;
				System.out.println("NPos Y up: " + posY);
				if (posX % contentPane.BLOCK_SIZE == 0) {
					posX /= contentPane.BLOCK_SIZE;
					System.out.println("NPos X if up: " + posX + "\n");
				}
				else {
					posX = (nuevoBomber.getPosicionY() - cantPosMover) / contentPane.BLOCK_SIZE;
					System.out.println("NPos X else up: " + posX + "\n");
				}
				if (!colisionador.verificarColision(posX, posY)) {
					nuevoBomber.moverse(0, -cantPosMover);
				}
				contentPane.setBomberIcon(1, posY);
				contentPane.setBomber(nuevoBomber);
				break;
			case KeyEvent.VK_LEFT:
				posX = (nuevoBomber.getPosicionX() - cantPosMover) / contentPane.BLOCK_SIZE;
				System.out.println("NPos X left: " + posX);
				if (posY % contentPane.BLOCK_SIZE == 0) {
					posY /= contentPane.BLOCK_SIZE;
					System.out.println("NPos Y if left: " + posY + "\n");
				}
				else {
					posY = (nuevoBomber.getPosicionX() - cantPosMover) / contentPane.BLOCK_SIZE;
					System.out.println("NPos Y else left: " + posY + "\n");
				}
				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(-cantPosMover, 0);

				contentPane.setBomberIcon(2, posX);
				contentPane.setBomber(nuevoBomber);
				break;
			case KeyEvent.VK_RIGHT:
				posX = (nuevoBomber.getPosicionX() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
				System.out.println("NPos X right: " + posX);
				if (posY % contentPane.BLOCK_SIZE == 0) {
					posY /= contentPane.BLOCK_SIZE;
					System.out.println("NPos Y if right: " + posY + "\n");
				}
				else {
					posY = (nuevoBomber.getPosicionX() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
					System.out.println("NPos Y else right: " + posY + "\n");
				}
				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(cantPosMover, 0);

				contentPane.setBomberIcon(3, posX);
				contentPane.setBomber(nuevoBomber);
				break;
			case KeyEvent.VK_SPACE:
				Bomba bombita = nuevoBomber.ponerBomba();
				if (bombita != null) {
					contentPane.addBomba(bombita);
					timearBomba(bombita.getTiempoExplosion() * 1000, nuevoBomber, bombita);
				}
				break;
			}
		}
	}

	private void timearBomba(int tiempoMili, Bomber bomber, Bomba bomba) {
		Timer timer = new Timer(tiempoMili, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.getMapa().explotarBomba(bomba, contentPane.BLOCK_SIZE);
				bomber.setBombasDisponibles(bomber.getBombasDisponibles() + 1);
			}
		});

		timer.start();
		timer.setRepeats(false);
	}

	public static void main(String[] args) {
		JVentanaGrafica ventana = new JVentanaGrafica();
		ventana.setVisible(true);
		Thread hilo = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(45);
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
