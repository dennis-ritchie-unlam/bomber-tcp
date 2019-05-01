package main.dominio;

public abstract class Entidad {
	private int posicionX;
	private int posicionY;

	public Entidad(int posX, int posY) {
		this.posicionX = posX;
		this.posicionY = posY;
	}

	public int getPosicionX() {
		return posicionX;
	}

	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	public int getPosicionY() {
		return posicionY;
	}

	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}

}
