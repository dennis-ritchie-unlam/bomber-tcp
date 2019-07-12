package paquete;

import java.util.ArrayList;

import entidades.Bomba;
import entidades.Mapa;

public class PaqueteMapa extends Paquete {
	
	private Mapa mapa;
	private ArrayList<Bomba> bombas;
	
	public PaqueteMapa(String comando, Mapa mapa, ArrayList<Bomba> bombas) {
		super(comando);
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
