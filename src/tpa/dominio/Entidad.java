package tpa.dominio;

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

	public void setPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public void setPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}
	
}
