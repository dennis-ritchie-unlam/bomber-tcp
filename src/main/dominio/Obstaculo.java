package main.dominio;

public class Obstaculo extends Entidad {
	private boolean destructible;

	public Obstaculo(double posX, double posY, boolean destructible) {
		super(posX, posY);
		this.destructible = destructible;
	}

	public boolean isDestructible() {
		return destructible;
	}

}
