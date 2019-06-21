package entidades;

import java.util.ArrayList;
import java.util.Random;

public class Mapa {
	Entidad[][] entidades;
	ArrayList<Bomber> bombers;
	public static final int ALTO = 21;
	public static final int ANCHO = 21;

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
	
	public void setPosicionNull(int x, int y) {
		entidades[y][x] = null;
	}
	
	public void añadirBomba(Bomba bombita, int blockSize) {
		entidades[bombita.getPosicionY() / blockSize][bombita.getPosicionX() / blockSize] = bombita;
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

	public void explotarBomba(Bomba bomb, int blockSize) {
		int x;
		int y;
		if (bomb == null)
			return;
		for (Bomber bomber : bombers) {
			if (Math.abs(bomber.getPosicionX() - bomb.getPosicionX()) <= blockSize
					&& bomb.getPosicionY() == bomber.getPosicionY())
				bomber.explotar();

			if (Math.abs(bomber.getPosicionY() - bomb.getPosicionY()) <= blockSize
					&& bomb.getPosicionX() == bomber.getPosicionX())
				bomber.explotar();

			if (bomb.getPosicionY() == bomber.getPosicionY() && bomb.getPosicionX() == bomber.getPosicionX())
				bomber.explotar();
		}
		setPosicionNull(bomb.getPosicionX() / blockSize, bomb.getPosicionY() / blockSize);
		for (int i = 1; i <= bomb.getRango(); i++) {
			x = (bomb.getPosicionX() - bomb.getRango() * blockSize) / blockSize;
			y = bomb.getPosicionY() / blockSize;
			if(entidades[y][x] != null && entidades[y][x].explotar()) {
				
				if(!entidades[y][x].isObstaculo())
					explotarBomba((Bomba) entidades[y][x], blockSize);
				setPosicionNull(x, y);
			}
			x = (bomb.getPosicionX() + bomb.getRango() * blockSize)/blockSize;
			if(entidades[y][x] != null && entidades[y][x].explotar()) {
				if(!entidades[y][x].isObstaculo())
					explotarBomba((Bomba) entidades[y][x], blockSize);
				setPosicionNull(x, y);
			}
			x = bomb.getPosicionX()/blockSize;
			y = (bomb.getPosicionY() - bomb.getRango() * blockSize)/blockSize;
			if(entidades[y][x] != null && entidades[y][x].explotar()) {
				if(!entidades[y][x].isObstaculo())
					explotarBomba((Bomba) entidades[y][x], blockSize);
				setPosicionNull(x, y);
			}
			y = (bomb.getPosicionY() + bomb.getRango() * blockSize)/blockSize;
			if(entidades[y][x] != null && entidades[y][x].explotar()) {
				if(!entidades[y][x].isObstaculo())
					explotarBomba((Bomba) entidades[y][x], blockSize);
				setPosicionNull(x, y);
			}
		}
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
