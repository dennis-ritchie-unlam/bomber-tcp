package main.dominio;

import java.util.ArrayList;
import java.util.Random;

public class Mapa {
	Entidad[][] entidades;
	ArrayList<Bomber> bombers;
	static final int ALTO = 24;
	static final int ANCHO = 25;

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
		int tope = 0;
		int desde = 0;
		for (int i = 3; i < (ALTO - 3); i++) {
			desde = randomGenerator.nextInt(ANCHO - 20 ) + 3;
			tope = randomGenerator.nextInt(ANCHO + 3 ) - 3;
			for (int j = desde; j < tope; j++) {
				if (i % 2 == 1 && j % 2 == 1 || i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0) {
					entidades[i][j] = new Obstaculo(i, j, true);
					entidades[i][j] = new Obstaculo(i, j, true);
				}
			}
		}
	}

	public void eliminarObstaculo(int x, int y) {
		if (entidades[y][x] instanceof Obstaculo && ((Obstaculo) entidades[y][x]).isDestructible())
			entidades[y][x] = null;
	}

	public void añadirBomba(Bomba bombita) {
		entidades[(int) Math.round(bombita.getPosicionY())][(int) Math.round(bombita.getPosicionX())] = bombita;
	}

	public void eliminarBomba(Bomba bombita) {
		entidades[(int) Math.round(bombita.getPosicionY())][(int) Math.round(bombita.getPosicionX())] = null;
	}

	public void añadirBomber(Bomber personaje) {
		bombers.add(personaje);
	}

	public void explotarBomba(Bomba bomb) {
		for (Bomber bomber : bombers) {
			if ((Math.abs(bomber.getPosicionX() - bomb.getPosicionX()) <= bomb.getRango())
					&& (Math.abs(bomber.getPosicionY() - bomb.getPosicionY()) <= bomb.getRango())) {
				bomber.explotar();
			}
		}

		for (int i = 1; i <= bomb.getRango(); i++) {
			eliminarObstaculo((int) Math.round(bomb.getPosicionX() - bomb.getRango()),
					(int) Math.round(bomb.getPosicionY()));
			eliminarObstaculo((int) Math.round(bomb.getPosicionX() + bomb.getRango()),
					(int) Math.round(bomb.getPosicionY()));
			eliminarObstaculo((int) Math.round(bomb.getPosicionX()),
					(int) Math.round(bomb.getPosicionY() - bomb.getRango()));
			eliminarObstaculo((int) Math.round(bomb.getPosicionX()),
					(int) Math.round(bomb.getPosicionY() + bomb.getRango()));
		}

		eliminarBomba(bomb);
	}

	public boolean hayAlgo(int posX, int posY) {
		if (posX < 0 || posY < 0 || (ANCHO < posX) || (ALTO < posY))
			return true;

		if (entidades[posY][posX] != null)
			return true;

		return false;
	}
}
