package main.dominio;

public class Colisionador {
	private Mapa mapa;
	
	public Colisionador(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public boolean verificarColision(double posX, double posY) {
		System.out.println("posX: " + posX + " posY: " + posY);
		
		if(this.mapa.hayAlgo((int)posX, (int)posY))
			return true;
		return false;
	}
}
