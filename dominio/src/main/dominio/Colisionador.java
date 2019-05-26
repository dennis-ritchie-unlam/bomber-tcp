package main.dominio;

public class Colisionador {
	private Mapa mapa;
	
	public Colisionador(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public boolean verificarColision(int posX, int posY) {
		System.out.println("Posición x real: " + posX); 
		System.out.println("Posición y real: " + posY);
		if(this.mapa.hayAlgo(posX, posY))
			return true;
		return false;
	}
}
