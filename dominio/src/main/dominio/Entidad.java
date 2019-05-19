package main.dominio;

public abstract class Entidad {
	private double posicionX;
	private double posicionY;

	public Entidad(double posX, double posY) {
		this.posicionX = posX;
		this.posicionY = posY;
	}

	public double getPosicionX() {
		return posicionX;
	}

	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}

}
