package mensaje;

import java.io.Serializable;

public class PaquetePersonaje extends PaqueteUsuario implements Serializable, Cloneable {
	private int id;
	
	public int getId() {
		return id;
	}
}
