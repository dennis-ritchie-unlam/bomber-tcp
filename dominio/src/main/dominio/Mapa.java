package main.dominio;

import java.util.ArrayList;
import java.util.Random;

public class Mapa {
	Entidad[][] entidades;
	ArrayList<Bomber> bombers;
	static final int ALTO = 21;
	static final int ANCHO = 21;

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
		Random randomGenerator = new Random();
		for (int i = 1; i < (ALTO - 1); i++) {
			for (int j = 1; j < (ANCHO - 1); j++) {
				if (randomGenerator.nextInt(10) >= 7 && !(i % 2 == 0 && j % 2 == 0)  && !(i < 3 && j < 3)
						&& !(i > ALTO - 4 && j > ANCHO - 4) && !(i > ALTO - 4 && j < 3) && !(i < 3 && j > ANCHO - 4)
				)
					entidades[i][j] = new Obstaculo(i, j, true);
			}
		}
	}
	
	public void eliminarObstaculo(int x, int y) {
		x /= 32;
		y /= 32;
		if(entidades[y][x] instanceof Bomba) 
			entidades[y][x] = null;
		if (entidades[y][x] instanceof Obstaculo && ((Obstaculo) entidades[y][x]).isDestructible())
			entidades[y][x] = null;
	}

	public void añadirBomba(Bomba bombita) {
		entidades[bombita.getPosicionY() / 32][bombita.getPosicionX() / 32] = bombita;
	}

	public void eliminarBomba(Bomba bombita) {
		entidades[bombita.getPosicionY() / 32][bombita.getPosicionX() / 32] = null;
	}

	public Entidad[][] getEntidades() {
		return entidades;
	}

	public ArrayList<Bomber> getBombers() {
		return bombers;
	}

	public void añadirBomber(Bomber personaje) {
		bombers.add(personaje);
	}

	public void explotarBomba(Bomba bomb) {
		if (bomb == null)
			return;
		for (Bomber bomber : bombers) {
			if (Math.abs(bomber.getPosicionX() - bomb.getPosicionX()) <= 32
					&& bomb.getPosicionY() == bomber.getPosicionY())
				bomber.explotar();

			if (Math.abs(bomber.getPosicionY() - bomb.getPosicionY()) <= 32
					&& bomb.getPosicionX() == bomber.getPosicionX())
				bomber.explotar();

			if (bomb.getPosicionY() == bomber.getPosicionY() && bomb.getPosicionX() == bomber.getPosicionX())
				bomber.explotar();
		}

		for (int i = 1; i <= bomb.getRango(); i++) {
			eliminarObstaculo(bomb.getPosicionX() - bomb.getRango() * 32, bomb.getPosicionY());
			eliminarObstaculo(bomb.getPosicionX() + bomb.getRango() * 32, bomb.getPosicionY());
			eliminarObstaculo(bomb.getPosicionX(), bomb.getPosicionY() - bomb.getRango() * 32);
			eliminarObstaculo(bomb.getPosicionX(), bomb.getPosicionY() + bomb.getRango() * 32);
		}

		eliminarBomba(bomb);
		bomb.setExploto(true);

	}

	public boolean hayAlgo(int posX, int posY) {
		if (posX < 0 || posY < 0 || (ANCHO < posX) || (ALTO < posY))
			return true;

		if (entidades[posY][posX] != null)
			return true;

		return false;
	}
}
