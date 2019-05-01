package main.dominio;

public class Obstaculo extends Entidad {
	private boolean destructible;

	public Obstaculo(int posX, int posY, boolean destructible) {
		super(posX, posY);
		this.destructible = destructible;
	}

	public boolean isDestructible() {
		return destructible;
	}

}
