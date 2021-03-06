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
	boolean[] direccionesKeyPressed = new boolean[4]; // 0 arriba, 1 izquierda, 2 abajo, 3 derecha
	boolean bombKeyPressed;

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
				addKeyPressed(arg0);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				removeKeyPressed(arg0);
			}
		});

		colisionador = new Colisionador(contentPane.getMapa());
	}

	public void accion() {
		
		Bomber nuevoBomber = contentPane.getBomber();
		int posX = nuevoBomber.getPosicionX();
		int posY = nuevoBomber.getPosicionY();
		int cantPosMover = 8;
		if (nuevoBomber.EstaVivo()) {

			if (bombKeyPressed ) {
				if(nuevoBomber.getBombasDisponibles()>0 && !colisionador.verificarColision(posX / contentPane.BLOCK_SIZE, posY / contentPane.BLOCK_SIZE)) {
					Bomba bombita = nuevoBomber.ponerBomba();
					if (bombita != null) {
						contentPane.addBomba(bombita);
						
						timearBomba(bombita.getTiempoExplosion() * 1000, nuevoBomber, bombita);
					}
				}
				
			}



			if (direccionesKeyPressed[2]) {
				System.out.println("PosX: " + posX + "\n");

				System.out.println("PosY: " + posY + "\n");

				posY = (nuevoBomber.getPosicionY() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
				System.out.println("NPos Y down: " + posY);
				if (posX % contentPane.BLOCK_SIZE == 0) {
					posX /= contentPane.BLOCK_SIZE;
					System.out.println("NPos X if down: " + posX + "\n");
				} else {
					posX = (nuevoBomber.getPosicionY() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
					System.out.println("NPos X else down: " + posX + "\n");
				}
				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(0, cantPosMover);

				contentPane.setBomberIcon(0, posY);
				contentPane.setBomber(nuevoBomber);
			} else if (direccionesKeyPressed[0]) {

				posY = (nuevoBomber.getPosicionY() - cantPosMover) / contentPane.BLOCK_SIZE;
				System.out.println("NPos Y up: " + posY);
				if (posX % contentPane.BLOCK_SIZE == 0) {
					posX /= contentPane.BLOCK_SIZE;
					System.out.println("NPos X if up: " + posX + "\n");
				} else {
					posX = (nuevoBomber.getPosicionY() - cantPosMover) / contentPane.BLOCK_SIZE;
					System.out.println("NPos X else up: " + posX + "\n");
				}
				if (!colisionador.verificarColision(posX, posY)) {
					nuevoBomber.moverse(0, -cantPosMover);
				}
				contentPane.setBomberIcon(1, posY);
				contentPane.setBomber(nuevoBomber);
			} else if (direccionesKeyPressed[1]) {
				posX = (nuevoBomber.getPosicionX() - cantPosMover) / contentPane.BLOCK_SIZE;
				System.out.println("NPos X left: " + posX);
				if (posY % contentPane.BLOCK_SIZE == 0) {
					posY /= contentPane.BLOCK_SIZE;
					System.out.println("NPos Y if left: " + posY + "\n");
				} else {
					posY = (nuevoBomber.getPosicionX() - cantPosMover) / contentPane.BLOCK_SIZE;
					System.out.println("NPos Y else left: " + posY + "\n");
				}
				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(-cantPosMover, 0);
				contentPane.setBomberIcon(2, posX);
				contentPane.setBomber(nuevoBomber);
			} else if (direccionesKeyPressed[3]) {
				posX = (nuevoBomber.getPosicionX() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
				System.out.println("NPos X right: " + posX);
				if (posY % contentPane.BLOCK_SIZE == 0) {
					posY /= contentPane.BLOCK_SIZE;
					System.out.println("NPos Y if right: " + posY + "\n");
				} else {
					posY = (nuevoBomber.getPosicionX() + cantPosMover * 4) / contentPane.BLOCK_SIZE;
					System.out.println("NPos Y else right: " + posY + "\n");
				}

				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(cantPosMover, 0);
				contentPane.setBomberIcon(3, posX);
				contentPane.setBomber(nuevoBomber);
			}

		}
	}

	private void removeKeyPressed(KeyEvent evento) {
		Bomber nuevoBomber = contentPane.getBomber();
		if (nuevoBomber.EstaVivo()) {
			switch (evento.getKeyCode()) {
			case KeyEvent.VK_UP:
				direccionesKeyPressed[0] = false;
				break;
			case KeyEvent.VK_LEFT:
				direccionesKeyPressed[1] = false;
				break;
			case KeyEvent.VK_DOWN:
				direccionesKeyPressed[2] = false;
				break;
			case KeyEvent.VK_RIGHT:
				direccionesKeyPressed[3] = false;
				break;
			case KeyEvent.VK_SPACE:
				bombKeyPressed = false;

			}
		}
	}

	private void addKeyPressed(KeyEvent evento) {
		Bomber nuevoBomber = contentPane.getBomber();
		if (nuevoBomber.EstaVivo()) {
			switch (evento.getKeyCode()) {
			case KeyEvent.VK_UP:
				direccionesKeyPressed[0] = true;
				break;
			case KeyEvent.VK_LEFT:
				direccionesKeyPressed[1] = true;
				break;
			case KeyEvent.VK_DOWN:
				direccionesKeyPressed[2] = true;
				break;
			case KeyEvent.VK_RIGHT:
				direccionesKeyPressed[3] = true;
				break;
			case KeyEvent.VK_SPACE:
				bombKeyPressed = true;
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
						ventana.accion();
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
