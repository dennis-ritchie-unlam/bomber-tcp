package mensaje;

import java.io.Serializable;
import java.util.Map;

public class PaqueteDePersonaje extends Paquete implements Serializable, Cloneable {

	private Map<Integer, PaquetePersonaje> personajes;

	public PaqueteDePersonaje() {

	}

	public PaqueteDePersonaje(final Map<Integer, PaquetePersonaje> personajes) {
		this.personajes = personajes;
	}

	public Map<Integer, PaquetePersonaje> getPersonajes() {
		return personajes;
	}

	@Override
	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}

}
