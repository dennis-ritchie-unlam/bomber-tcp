package entidades;

import java.util.Arrays;

public class Bomber implements Entidad {
	private boolean estaVivo;
	private int bombasDisponibles;
	private int posicionX;
	private int posicionY;
	private boolean[] direccion;
	private final String className;

	public Bomber(int posX, int posY) {
		this.posicionX = posX;
		this.posicionY = posY;
		this.estaVivo = true;
		this.bombasDisponibles = 4;
		this.direccion = new boolean[4];
		className = getClass().getName();
	}

	public Bomba ponerBomba() {
		if (bombasDisponibles > 0) {
			this.bombasDisponibles--;
			return new Bomba(this.posicionX, this.posicionY);
		}
		return null;
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

	public void moverse(int despX, int despY) {
		setPosicionX(posicionX + despX);
		setPosicionY(posicionY + despY);
	}

	@Override
	public boolean explotar() {
		this.estaVivo = false;
		return true;
	}

	@Override
	public boolean isObstaculo() {
		return false;
	}

    @Override
    public String getClassName() {
        return className;
    }	
    
    public int getDireccion() {
		int dir = -1;
    	for(int i = 0; i < direccion.length; i++) {
    		if(direccion[i] == true)
				dir = i;
		}
    	return dir;
	}

	public void setDireccion(int cod) {
		Arrays.fill(direccion, false);
		this.direccion[cod] = true;
	}
}
