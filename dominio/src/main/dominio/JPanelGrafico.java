package main.dominio;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelGrafico extends JPanel {

	private ImageIcon matrixBomberIcons[][];
	private ImageIcon bomberIcon;
	private Bomber bomber;
	private Mapa mapa;

	private ImageIcon bombaIcon;
	private ImageIcon obstaculoIcon;
	private ImageIcon obstaculoDestructibleIcon;
	private Bomba bomba;
	int blockSize;
	int bombSize;

	public JPanelGrafico() {
		blockSize = 40;
		bombSize = 31;
		bomberIcon = new ImageIcon("./Images/Bombermans/Player 1/01.gif");
		bombaIcon = new ImageIcon("./Images/BomberBombs/2.gif");
		obstaculoIcon = new ImageIcon("./Images/BomberWalls/1.jpg");
		obstaculoDestructibleIcon = new ImageIcon("./Images/BomberWalls/2.png");
		matrixBomberIcons = new ImageIcon[4][5];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 5; j++)
				matrixBomberIcons[i][j] = new ImageIcon("./Images/Bombermans/Player 1/" + i + "" + (j + 1) + ".gif");

		bomber = new Bomber(1, 1);

		mapa = new Mapa();
		mapa.añadirBomber(bomber);
	}

	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(52, 108, 108));

		int posX;
		int posY;
		for (int i = 0; i < Mapa.ALTO; i++) {
			for (int j = 0; j < Mapa.ANCHO; j++) {
				if (mapa.entidades[i][j] != null) {
					Obstaculo obstaculito = (Obstaculo) mapa.entidades[i][j];
					posX = ((int) Math.round(obstaculito.getPosicionX() * blockSize)) ;
					posY = ((int) Math.round(obstaculito.getPosicionY() * blockSize)) ;
					if (!obstaculito.isDestructible()) {
						g.drawImage(obstaculoIcon.getImage(), posX, posY, blockSize, blockSize, null);
					} else {
						g.drawImage(obstaculoDestructibleIcon.getImage(), posX, posY, blockSize, blockSize, null);
					}
				}
			}
		}
		g.drawImage(bomberIcon.getImage(), (int) Math.round(bomber.getPosicionX() * blockSize),
				(int) Math.round(bomber.getPosicionY() * blockSize), 30, 60, null);
		if (bomba != null)
			g.drawImage(bombaIcon.getImage(),
					(int) (bomba.getPosicionX() * blockSize + bomberIcon.getIconWidth() - bombaIcon.getIconWidth()),
					(int) (bomba.getPosicionY() * blockSize + bomberIcon.getIconHeight() - bombaIcon.getIconHeight()),
					bombSize, bombSize, null);
	}

	public void setBomberIcon(int i, double j) {
		int m = (int) j;
		this.bomberIcon = matrixBomberIcons[i][m % 5];
	}

	public Mapa getMapa() {
		return mapa;
	}

	public Bomber getBomber() {
		return bomber;
	}

	public void setBomber(Bomber bomber) {
		this.bomber = bomber;
	}

	public void setBomba(Bomba bomba) {
		this.bomba = bomba;
	}

}
