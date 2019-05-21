package main.dominio;

public class Bomba implements Entidad {
    private int tiempoExplosion;
    private int duracionExplosion;
    private int rango;
    private boolean exploto;
    private int posicionX;
    private int posicionY;

    public Bomba(double posX, double posY) {
    	posicionX = (int)Math.round(posX);
    	posicionY = (int)Math.round(posY);
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

	@Override
	public double getPosicionX() {
		return posicionX;
	}

	@Override
	public void setPosicionX(double posicionX) {
		this.posicionX = (int)Math.round(posicionX);		
	}

	@Override
	public double getPosicionY() {
		return posicionY;
	}

	@Override
	public void setPosicionY(double posicionY) {
		this.posicionY = (int)Math.round(posicionY);		
	}

}
