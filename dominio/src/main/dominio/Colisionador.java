package main.dominio;

public class Colisionador {
	private Mapa mapa;
	
	public Colisionador(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public boolean verificarColision(int posX, int posY) {
		if(this.mapa.hayAlgo(posX, posY))
			return true;
		return false;
	}
}
