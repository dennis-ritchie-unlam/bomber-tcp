package cliente;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import entidades.Bomba;
import entidades.Bomber;
import entidades.Mapa;
import entidades.Obstaculo;

public class PanelGrafico extends JPanel{
    
    private Mapa mapa;
    private ArrayList<Bomba> bombas;
    private final ImageIcon bombaIcon;
    private final ImageIcon obstaculoIcon;
    private final ImageIcon obstaculoDestructibleIcon;
    private final ImageIcon fuegoIcon;
    private ImageIcon matrizBomberIcons[][][];
    private ImageIcon bomberIcon;
    final int BLOCK_SIZE = 32;
    
    public PanelGrafico() {
        bomberIcon = new ImageIcon();
        bombaIcon = new ImageIcon("./Images/BomberBombs/2.gif");
        obstaculoIcon = new ImageIcon("./Images/BomberWalls/1.jpg");
        obstaculoDestructibleIcon = new ImageIcon("./Images/BomberWalls/2.png");
        matrizBomberIcons = new ImageIcon[4][4][5];
        for (int k=0; k<4; k++)
	        for (int i = 0; i < 4; i++)
	            for (int j = 0; j < 5; j++)
	                matrizBomberIcons[k][i][j] = new ImageIcon("./Images/Bombermans/Player " +(k+1)+ "/" + i + "" + (j + 1) + ".gif");
        fuegoIcon = new ImageIcon("./Images/BomberFires/C1.gif");
    }
    
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(52, 108, 108));

        dibujarMapa(g);
        dibujarBomber(g);
        dibujarFuego(g);
    }
    
    private synchronized void dibujarMapa(Graphics g) {
        int posX;
        int posY;
        for (int i = 0; i < Mapa.ALTO; i++) {
            for (int j = 0; j < Mapa.ANCHO; j++) {
                if (mapa.getEntidades()[i][j] != null) {
                    if(mapa.getEntidades()[i][j].isObstaculo()) {
                        Obstaculo obstaculito = (Obstaculo) mapa.getEntidades()[i][j];
                        posX = (obstaculito.getPosicionX() * BLOCK_SIZE);
                        posY = (obstaculito.getPosicionY() * BLOCK_SIZE);
                        if (!obstaculito.isDestructible()) {
                            g.drawImage(obstaculoIcon.getImage(), posX, posY, BLOCK_SIZE, BLOCK_SIZE, null);
                        } else {     
                        	g.drawImage(obstaculoDestructibleIcon.getImage(), posX, posY, BLOCK_SIZE, BLOCK_SIZE, null);
                        }
                    }else {
                        Bomba bomba = (Bomba) mapa.getEntidades()[i][j];
                        if (bomba.getPosicionX() % BLOCK_SIZE <= BLOCK_SIZE / 2)
                            posX = bomba.getPosicionX() - bomba.getPosicionX() % BLOCK_SIZE;
                        else
                            posX = bomba.getPosicionX() + (bomba.getPosicionX() % BLOCK_SIZE) / 3;
                        g.drawImage(bombaIcon.getImage(), posX, bomba.getPosicionY(), BLOCK_SIZE, BLOCK_SIZE, null);
                    }
                }
            }
        }
    }
    
    public void setBomberIcon(int nroBomber, int i, double j) {
		int m = (int) j;
		this.bomberIcon = matrizBomberIcons[nroBomber][i][m % 5];
	}

    private synchronized void dibujarBomber(Graphics g) {
    	int nroBomber = -1;
        for (Bomber bomber : mapa.getBombers()) {
        	nroBomber++;
            if (bomber.EstaVivo()) {
            	int pos = bomber.getDireccion() > 1? bomber.getPosicionX(): bomber.getPosicionY();
            	if( bomber.getDireccion() >= 0 && nroBomber >= 0)
            		setBomberIcon(nroBomber, bomber.getDireccion(), pos / BLOCK_SIZE);
                g.drawImage(bomberIcon.getImage(), bomber.getPosicionX(), bomber.getPosicionY(), BLOCK_SIZE, BLOCK_SIZE,
                        null); 
            }
        }
        
    }

    private synchronized void dibujarFuego(Graphics g) {
        for(Iterator<Bomba> iterator = bombas.iterator(); iterator.hasNext();) {
            Bomba bomba = iterator.next();
            if (bomba.isExploto()) {
    
                g.drawImage(fuegoIcon.getImage(), bomba.getPosicionX(), bomba.getPosicionY(), BLOCK_SIZE, BLOCK_SIZE, null);
    
                if (!mapa.hayAlgo(bomba.getPosicionX() / BLOCK_SIZE + 1, bomba.getPosicionY() / BLOCK_SIZE))
                    g.drawImage(fuegoIcon.getImage(), bomba.getPosicionX() + BLOCK_SIZE, bomba.getPosicionY(), BLOCK_SIZE,
                            BLOCK_SIZE, null);
                if (!mapa.hayAlgo(bomba.getPosicionX() / BLOCK_SIZE - 1, bomba.getPosicionY() / BLOCK_SIZE))
                    g.drawImage(fuegoIcon.getImage(), bomba.getPosicionX() - BLOCK_SIZE, bomba.getPosicionY(), BLOCK_SIZE,
                            BLOCK_SIZE, null);
                if (!mapa.hayAlgo(bomba.getPosicionX() / BLOCK_SIZE, bomba.getPosicionY() / BLOCK_SIZE + 1))
                    g.drawImage(fuegoIcon.getImage(), bomba.getPosicionX(), bomba.getPosicionY() + BLOCK_SIZE, BLOCK_SIZE,
                            BLOCK_SIZE, null);
                if (!mapa.hayAlgo(bomba.getPosicionX() / BLOCK_SIZE, bomba.getPosicionY() / BLOCK_SIZE - 1))
                    g.drawImage(fuegoIcon.getImage(), bomba.getPosicionX(), bomba.getPosicionY() - BLOCK_SIZE, BLOCK_SIZE,
                            BLOCK_SIZE, null);
    
                iterator.remove();
            }
        }
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
    
    public void setBombas(ArrayList<Bomba> bombas) {
		this.bombas = bombas;
	}
    
}
