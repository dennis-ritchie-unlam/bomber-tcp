package servidor;

import mensaje.PaquetePersonaje;
import mensaje.PaqueteUsuario;

public class Conector {
	public PaquetePersonaje getPersonaje(PaqueteUsuario user) {
		PaquetePersonaje paquete = new PaquetePersonaje();

		return paquete;
	}

	public boolean loguearUsuario(final PaqueteUsuario user) {
		boolean pudeLoguear = false;

		try {
			getUsuario(user.getUsername());
			pudeLoguear = true;
			Servidor.log.append("El usuario " + user.getUsername() + " ha iniciado sesión." + System.lineSeparator());
		} catch (Exception e) {
			e.printStackTrace();
			Servidor.log.append("El usuario " + user.getUsername()
					+ " ha realizado un intento fallido de inicio de sesión." + System.lineSeparator());
		}

		return pudeLoguear;
	}

	public PaqueteUsuario getUsuario(String usuario) {
		PaqueteUsuario paquete = new PaqueteUsuario();
		paquete.setUsername("ernesto1");
		paquete.setIdPj(1);
		paquete.setPassword("123");
		
		return paquete;
	}
}
