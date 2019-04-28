package tpa.dominio;

public class Bomba extends Entidad{
	private int tiempoExplosion;
	private int duracionExplosion;
	private int rango;
	private boolean exploto;
	
	public Bomba(double posX, double posY) {
		super(posX, posY);
		this.tiempoExplosion = 2;
		this.duracionExplosion = 1;
		this.rango = 1;
		this.exploto = false;
	}
	
	public void explotar() {
		this.exploto = true;
	}

	public int getTiempoExplosion() {
		return tiempoExplosion;
	}

	public void setTiempoExplosion(int tiempoExplosion) {
		this.tiempoExplosion = tiempoExplosion;
	}

	public int getDuracionExplosion() {
		return duracionExplosion;
	}

	public void setDuracionExplosion(int duracionExplosion) {
		this.duracionExplosion = duracionExplosion;
	}

	public int getRango() {
		return rango;
	}
		
}
