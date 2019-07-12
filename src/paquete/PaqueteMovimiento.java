package paquete;

public class PaqueteMovimiento extends Paquete {
	
	boolean direccionesKeyPressed[];
	boolean ponerBomba;
	
	public PaqueteMovimiento() {

	}
	
	public PaqueteMovimiento(String comando, boolean direcciones[]) {
		super(comando);
		direccionesKeyPressed = direcciones;
	}
	
	public PaqueteMovimiento(String comando, boolean direcciones[], boolean ponerBomba) {
		super(comando);
		direccionesKeyPressed = direcciones;
		this.ponerBomba = ponerBomba;
	}
	
	public boolean[] getDireccionesKeyPressed() {
		return direccionesKeyPressed;
	}

	public void setDireccionesKeyPressed(boolean[] direccionesKeyPressed) {
		this.direccionesKeyPressed = direccionesKeyPressed;
	}

	public boolean isPonerBomba() {
		return ponerBomba;
	}

	public void setPonerBomba(boolean ponerBomba) {
		this.ponerBomba = ponerBomba;
	}
}
