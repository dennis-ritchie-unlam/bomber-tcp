package entidades;

public class Obstaculo implements Entidad {
    private boolean destructible;
    private int posicionX;
    private int posicionY;
    private final String className;

    public Obstaculo(int posY, int posX, boolean destructible) {
    	posicionY = posY;
        posicionX = posX;
        this.destructible = destructible;
        className = getClass().getName();
    }

    public boolean isObstaculo() {
		return true;
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
	public boolean explotar() {
		if(isDestructible())
			return true;
		return false;
	}

    @Override
    public String getClassName() {
        return className;
    }
}
