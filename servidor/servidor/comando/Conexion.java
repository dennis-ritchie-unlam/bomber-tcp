package servidor.comando;

import mensaje.PaquetePersonaje;
import servidor.Servidor;

public class Conexion extends ComandoServer {

	@Override
	public void ejecutar() {
		conexionCliente
				.setPaquetePersonaje((PaquetePersonaje) (gson.fromJson(cadenaLeida, PaquetePersonaje.class)).clone());

		Servidor.getPersonajesConectados().put(conexionCliente.getPaquetePersonaje().getId(),
				(PaquetePersonaje) conexionCliente.getPaquetePersonaje().clone());

		synchronized (Servidor.atencionConexiones) {
			Servidor.atencionConexiones.notify();
		}

	}

}
