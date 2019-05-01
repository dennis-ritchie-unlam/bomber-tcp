package main.dominio;

import java.util.ArrayList;

public class Mapa {
	private Entidad[][] entidades;
	private ArrayList<Bomber> bombers;
	static final int ALTO = 13;
	static final int ANCHO = 15;

	public Mapa() {
		entidades = new Entidad[ALTO][ANCHO];
		bombers = new ArrayList<Bomber>();
		generarObstaculosIndestructibles();
		generarObstaculosDestructibles();
	}

	private void generarObstaculosIndestructibles() {
		for (int i = 0; i < ALTO; i++)
			for (int j = 0; j < ANCHO; j++)
				if (i == 0 || i == (ALTO - 1) || j == 0 || j == (ANCHO - 1) || (i % 2 == 0 && j % 2 == 0))
					entidades[i][j] = new Obstaculo(i, j, false);
	}

	private void generarObstaculosDestructibles() {
		for (int i = 1; i < (ANCHO - 1); i++)
			entidades[i][4] = new Obstaculo(i, 4, true);
		for (int i = 1; i < (ANCHO - 1); i++)
			entidades[i][10] = new Obstaculo(i, 10, true);
	}

	public void eliminarObstaculo(int x, int y) {
		if (entidades[x][y] instanceof Obstaculo && ((Obstaculo) entidades[x][y]).isDestructible())
			entidades[x][y] = null;
	}

	public Entidad[][] getEntidades() {
		return entidades;
	}

	public void añadirBomba(Bomba bombita) {
		entidades[bombita.getPosicionX()][bombita.getPosicionY()] = bombita;
	}

	public void eliminarBomba(int x, int y) {
		if (entidades[x][y] instanceof Bomba)
			entidades[x][y] = null;
	}

	public void añadirBomber(Bomber personaje) {
		bombers.add(personaje);
	}
	
	public void moverBomber(Bomber personaje, double despX, double despY) {
		double personajeX = personaje.getPosicionX();
		double personajeY = personaje.getPosicionY();
		
		if( entidades[(int)(personajeX + despX)][(int)(personajeY + despY)] == null )
		
		entidades[personajeX][personajeY] = null;
		
		personaje.setPosicionX(personajeX + despX);
		personaje.setPosicionY(personajeY + despY);
		
		entidades[(int) personaje.getPosicionX()][(int) personaje.getPosicionY()] = personaje;
	}
}
