package main.dominio;

public class Colisionador {
	private Mapa mapa;
	
	public Colisionador(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public boolean verificarColision(double posX, double posY) {
		System.out.println("Posición x real: " + posX); 
		System.out.println("Posición y real: " + posY);
		System.out.println("Posición x entera: " + (int)Math.round(posX)); 
		System.out.println("Posición y entera: " + (int)Math.round(posY));
		if(this.mapa.hayAlgo((int)Math.round(posX), (int)Math.round(posY)))
			return true;
		return false;
	}
}
