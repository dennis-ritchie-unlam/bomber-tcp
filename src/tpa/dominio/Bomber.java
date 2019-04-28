package tpa.dominio;

public class Bomber extends Entidad {
	private boolean estaVivo;
	private int bombasDisponibles;

	public Bomber(double posX, double posY) {
		super(posX, posY);
		this.estaVivo = true;
		this.bombasDisponibles = 1;
	}

	public Bomba ponerBomba() {
		return new Bomba((double) Math.round(this.getPosicionX()), (double) Math.round(this.getPosicionY()));
	}

	public void morir() {
		this.estaVivo = false;
	}

	public boolean EstaVivo() {
		return estaVivo;
	}

	public void revivir() {
		this.estaVivo = true;
	}

	public int getBombasDisponibles() {
		return bombasDisponibles;
	}

	public void setBombasDisponibles(int bombasDisponibles) {
		this.bombasDisponibles = bombasDisponibles;
	}

}
