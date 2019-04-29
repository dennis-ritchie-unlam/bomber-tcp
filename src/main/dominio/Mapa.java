package main.dominio;

public class Mapa {
	private Entidad[][] entidades;
	static final int ALTO = 13;
	static final int ANCHO = 15;

	public Mapa() {
		entidades = new Entidad[ALTO][ANCHO];
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
		entidades[(int) bombita.getPosicionX()][(int) bombita.getPosicionY()] = bombita;
	}

	public void eliminarBomba(int x, int y) {
		if (entidades[x][y] instanceof Bomba)
			entidades[x][y] = null;
	}

	public void añadirBomber(Bomber personaje) {
		entidades[(int) personaje.getPosicionX()][(int) personaje.getPosicionY()] = personaje;
	}
}
