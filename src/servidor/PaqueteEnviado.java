package servidor;

import java.util.ArrayList;

import entidades.Bomba;
import entidades.Mapa;

public class PaqueteEnviado {

	private Mapa mapa;
	private ArrayList<Bomba> bombas;

	public PaqueteEnviado(Mapa mapa, ArrayList<Bomba> bombas) {
		this.mapa = mapa;
		this.bombas = bombas;
	}

}
