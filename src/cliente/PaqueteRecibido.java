package cliente;

import java.util.ArrayList;

import entidades.Bomba;
import entidades.Mapa;

public class PaqueteRecibido {
	
	private Mapa mapa;
	private ArrayList<Bomba> bombas;
	
	public PaqueteRecibido(Mapa mapa, ArrayList<Bomba> bombas) {
		this.mapa = mapa;
		this.bombas = bombas;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}
	
	
}
