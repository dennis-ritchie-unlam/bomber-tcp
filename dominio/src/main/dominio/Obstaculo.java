package main.dominio;

public class Obstaculo implements Entidad {
    private boolean destructible;
    private int posicionX;
    private int posicionY;

    public Obstaculo(int posX, int posY, boolean destructible) {
        posicionX = posX;
        posicionY = posY;
        this.destructible = destructible;
    }

    public boolean isDestructible() {
        return destructible;
    }

	@Override
	public double getPosicionX() {
		return posicionX;
	}

	@Override
	public void setPosicionX(double posicionX) {
		this.posicionX = (int) posicionX;
	}

	@Override
	public double getPosicionY() {
		return posicionY;
	}

	@Override
	public void setPosicionY(double posicionY) {
		this.posicionY = (int) posicionY;	
	}

	@Override
	public void explotar() {
		//if(isDestructible())
			
	}
}
