package main.dominio;

public class Obstaculo implements Entidad {
    private boolean destructible;
    private int posicionX;
    private int posicionY;

    public Obstaculo(int posY, int posX, boolean destructible) {
    	posicionY = posY;
        posicionX = posX;
        this.destructible = destructible;
    }

    public boolean isDestructible() {
        return destructible;
    }

	@Override
	public int getPosicionX() {
		return posicionX;
	}

	@Override
	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	@Override
	public int getPosicionY() {
		return posicionY;
	}

	@Override
	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;	
	}

	@Override
	public void explotar() {
		//if(isDestructible())
			
	}
}
