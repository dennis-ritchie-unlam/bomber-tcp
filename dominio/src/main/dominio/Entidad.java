package main.dominio;

public interface Entidad {
	
	int getPosicionX();

	void setPosicionX(int posicionX);

	int getPosicionY();

	void setPosicionY(int posicionY);

	boolean explotar();
	
	boolean isObstaculo();

}
