package paquete;

import java.util.ArrayList;

import entidades.Bomba;
import entidades.Mapa;

public class PaqueteJuego extends Paquete {
	
	private Mapa mapa;
	private ArrayList<Bomba> bombas;
	
	public PaqueteJuego(Mapa mapa, ArrayList<Bomba> bombas) {
		this.mapa = mapa;
		this.bombas = bombas;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public void setBombas(ArrayList<Bomba> bombas) {
		this.bombas = bombas;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}
	
	
}
