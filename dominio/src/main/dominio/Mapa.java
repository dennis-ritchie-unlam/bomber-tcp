package main.dominio;

import java.util.ArrayList;

public class Mapa {
	private Entidad[][] entidades;
	private ArrayList<Bomber> bombers;
	private static final int ALTO = 13;
	private static final int ANCHO = 15;

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
		for (int i = 1; i < (ALTO - 1); i++) {
			entidades[i][5] = new Obstaculo(i, 5, true);
			entidades[i][9] = new Obstaculo(i, 9, true);
		}
		
	}

	public ArrayList<Bomber> getBombers() {
		return bombers;
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

	public void eliminarBomba(Bomba bombita) {
		entidades[bombita.getPosicionX()][bombita.getPosicionY()] = null;
	}

	public void añadirBomber(Bomber personaje) {
		bombers.add(personaje);
	}
	
	public void moverBomber(Bomber personaje, double despX, double despY) {
		double personajeX = personaje.getPosicionX();
		double personajeY = personaje.getPosicionY();
		
		if( entidades[(int)(personajeX + despX)][(int)(personajeY + despY)] == null ) {
			personaje.setPosicionX(personajeX+despX);
			personaje.setPosicionY(personajeY+despY);
		}

	}
	
	public void explotarBomba(Bomba bomb) {
		for(Bomber bomber: bombers) {
			if((Math.abs(bomber.getPosicionX()-bomb.getPosicionX()) <= bomb.getRango()) && (Math.abs(bomber.getPosicionY()-bomb.getPosicionY()) <= bomb.getRango())) {
				bomber.morir();
			}
		}
		//SE CONSIDERA SOLO EL RANGO 1
		eliminarObstaculo(bomb.getPosicionX() - bomb.getRango(), bomb.getPosicionY());
		eliminarObstaculo(bomb.getPosicionX() + bomb.getRango(), bomb.getPosicionY());
		eliminarObstaculo(bomb.getPosicionX(), bomb.getPosicionY() - bomb.getRango()); 
		eliminarObstaculo(bomb.getPosicionX(), bomb.getPosicionY() + bomb.getRango());	
		
		eliminarBomba(bomb);
	}
	
	
}
