package main.dominio;

import java.awt.Color;
import java.awt.Graphics;

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
	
	final int BLOCK_SIZE = 32; 

	public JPanelGrafico() {
		bomberIcon = new ImageIcon("./Images/Bombermans/Player 1/01.gif");
		bombaIcon = new ImageIcon("./Images/BomberBombs/2.gif");
		obstaculoIcon = new ImageIcon("./Images/BomberWalls/1.jpg");
		obstaculoDestructibleIcon = new ImageIcon("./Images/BomberWalls/2.png");
		matrixBomberIcons = new ImageIcon[4][5];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 5; j++)
				matrixBomberIcons[i][j] = new ImageIcon("./Images/Bombermans/Player 1/" + i + "" + (j + 1) + ".gif");

		bomber = new Bomber(BLOCK_SIZE,BLOCK_SIZE);

		mapa = new Mapa();
		mapa.añadirBomber(bomber);
	}

	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(52, 108, 108));

		dibujarObstaculos(g);
		dibujarBomber(g);
		dibujarBomba(g);
		
	}

	private void dibujarObstaculos(Graphics g) {
		int posX;
		int posY;
		for (int i = 0; i < Mapa.ALTO; i++) {
			for (int j = 0; j < Mapa.ANCHO; j++) {
				if (mapa.entidades[i][j] != null && mapa.entidades[i][j] instanceof Obstaculo) {
					Obstaculo obstaculito = (Obstaculo) mapa.entidades[i][j];
					posX = (obstaculito.getPosicionX() * BLOCK_SIZE);
					posY = (obstaculito.getPosicionY() * BLOCK_SIZE);
					if (!obstaculito.isDestructible()) {
						g.drawImage(obstaculoIcon.getImage(), posX, posY, BLOCK_SIZE, BLOCK_SIZE, null);
					} else {
						g.drawImage(obstaculoDestructibleIcon.getImage(), posX, posY, BLOCK_SIZE, BLOCK_SIZE, null);
					}
				}
			}
		}
	}

    private void dibujarBomber(Graphics g) {
        if (bomber.EstaVivo())
            g.drawImage(bomberIcon.getImage(), bomber.getPosicionX(), bomber.getPosicionY(), BLOCK_SIZE, BLOCK_SIZE,
                    null);
    }

    private void dibujarBomba(Graphics g) {
        if (bomba != null) {
            g.drawImage(bombaIcon.getImage(), bomba.getPosicionX(), bomba.getPosicionY(),
                    BLOCK_SIZE, BLOCK_SIZE, null);
        }
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
	
	public Bomba getBomba() {
        return bomba;
    }
	
	public void setBomba(Bomba bomba) {
		this.bomba = bomba;
		if(bomba != null)
		    this.mapa.añadirBomba(bomba);
	}

}
