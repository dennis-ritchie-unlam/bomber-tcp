package main.dominio;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelGrafico extends JPanel {

    private final ImageIcon bombaIcon;
    private final ImageIcon obstaculoIcon;
    private final ImageIcon obstaculoDestructibleIcon;
    private final Mapa mapa;
    private final ImageIcon fuegoIcon;
    private ImageIcon matrizBomberIcons[][];
    private ImageIcon bomberIcon;
    private Bomber bomber;
    private Bomba bomba;
    private boolean dibujarFuego;
    final int BLOCK_SIZE = 32;

    public JPanelGrafico() {
        bomberIcon = new ImageIcon("./Images/Bombermans/Player 1/01.gif");
        bombaIcon = new ImageIcon("./Images/BomberBombs/2.gif");
        obstaculoIcon = new ImageIcon("./Images/BomberWalls/1.jpg");
        obstaculoDestructibleIcon = new ImageIcon("./Images/BomberWalls/2.png");
        matrizBomberIcons = new ImageIcon[4][5];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                matrizBomberIcons[i][j] = new ImageIcon("./Images/Bombermans/Player 1/" + i + "" + (j + 1) + ".gif");
        fuegoIcon = new ImageIcon("./Images/BomberFires/C1.gif");
        dibujarFuego = false;
        bomber = new Bomber(BLOCK_SIZE, BLOCK_SIZE);
        mapa = new Mapa();
        mapa.aņadirBomber(bomber);
    }

    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(52, 108, 108));

        dibujarObstaculos(g);
        dibujarBomber(g);
        dibujarBomba(g);
        dibujarFuego(g);
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
            int posX;
            if (bomba.getPosicionX() % BLOCK_SIZE <= BLOCK_SIZE / 2)
                posX = bomba.getPosicionX() - bomba.getPosicionX() % BLOCK_SIZE;
            else
                posX = bomba.getPosicionX() + (bomba.getPosicionX() % BLOCK_SIZE) / 3;
            g.drawImage(bombaIcon.getImage(), posX, bomba.getPosicionY(), BLOCK_SIZE, BLOCK_SIZE, null);
        }
    }

    private void dibujarFuego(Graphics g) {
        if (dibujarFuego) {
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
            
            dibujarFuego = false;
            bomba = null;
        }
    }

    public void setBomberIcon(int i, double j) {
        int m = (int) j;
        this.bomberIcon = matrizBomberIcons[i][m % 5];
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
        if (bomba != null) {
            if (bomba.getPosicionX() % BLOCK_SIZE <= BLOCK_SIZE / 2)
                bomba.setPosicionX(bomba.getPosicionX() - bomba.getPosicionX() % BLOCK_SIZE);
            else
                bomba.setPosicionX(bomba.getPosicionX() + (bomba.getPosicionX() % BLOCK_SIZE) / 3);

            if (bomba.getPosicionY() % BLOCK_SIZE <= BLOCK_SIZE / 2)
                bomba.setPosicionY(bomba.getPosicionY() - bomba.getPosicionY() % BLOCK_SIZE);
            else
                bomba.setPosicionY(bomba.getPosicionY() + (bomba.getPosicionY() % BLOCK_SIZE) / 3);
            this.mapa.aņadirBomba(bomba);
        }
        this.bomba = bomba;
    }

    public void setDibujarFuego(boolean dibujarFuego) {
        this.dibujarFuego = dibujarFuego;
    }

}
