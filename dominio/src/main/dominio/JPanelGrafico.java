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
    private Bomba bomba;
    
    public JPanelGrafico() {
        bomberIcon = new ImageIcon("./Images/Bombermans/Player 1/01.gif");
        bombaIcon = new ImageIcon("./Images/BomberBombs/2.gif");
        matrixBomberIcons = new ImageIcon[4][5];
        for (int i = 0; i < 4; i++)
        	for (int j = 0; j < 5; j++)
        		matrixBomberIcons[i][j] = new ImageIcon("./Images/Bombermans/Player 1/" + i + "" + (j+1) + ".gif");
        
        bomber = new Bomber(1, 1);
       
        mapa = new Mapa();
        mapa.añadirBomber(bomber);
    }
    
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(52,108,108));
       
        double escala = Mapa.ESCALA;
        g.drawImage(bomberIcon.getImage(),  (int) (bomber.getPosicionX() * escala), (int) (bomber.getPosicionY() * escala), null);
        if(bomba != null)
        	g.drawImage(bombaIcon.getImage(), 
                    (int) (bomba.getPosicionX() * escala + bomberIcon.getIconWidth() - bombaIcon.getIconWidth()), 
                    (int) (bomba.getPosicionY() * escala + bomberIcon.getIconHeight() - bombaIcon.getIconHeight()), 20, 20, null);
//        g.drawImage(bomberIcon.getImage(),  Mapa.descalarPosicion(bomber.getPosicionX()), Mapa.escalarPosicion(bomber.getPosicionY()), null);
//        if(bomba != null)
//        	g.drawImage(bombaIcon.getImage(), 
//                    Mapa.descalarPosicion(bomba.getPosicionX()) + bomberIcon.getIconWidth() - bombaIcon.getIconWidth(), 
//                    Mapa.descalarPosicion(bomba.getPosicionY()) + bomberIcon.getIconHeight() - bombaIcon.getIconHeight(), 20, 20, null);
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
