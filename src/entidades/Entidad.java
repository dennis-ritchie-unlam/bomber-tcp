package entidades;

public interface Entidad extends Convertible{
	
	int getPosicionX();

	void setPosicionX(int posicionX);

	int getPosicionY();

	void setPosicionY(int posicionY);

	boolean explotar();
	
	boolean isObstaculo();

}
