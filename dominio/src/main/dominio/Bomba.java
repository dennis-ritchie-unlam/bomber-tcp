package main.dominio;

public class Bomba extends Entidad {
	private int tiempoExplosion;
	private int duracionExplosion;
	private int rango;
	private boolean exploto;

	public Bomba(int posX, int posY) {
		super(posX, posY);
		this.tiempoExplosion = 2;
		this.duracionExplosion = 1;
		this.rango = 1;
		this.setExploto(false);
	}

	public boolean isExploto() {
		return exploto;
	}

	public void explotar() {
		this.setExploto(true);
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

	public void setExploto(boolean exploto) {
		this.exploto = exploto;
	}

}
