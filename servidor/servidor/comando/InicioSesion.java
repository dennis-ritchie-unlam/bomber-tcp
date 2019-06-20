package servidor.comando;

import java.io.IOException;

import cliente.comando.Comando;
import mensaje.Paquete;
import mensaje.PaquetePersonaje;
import mensaje.PaqueteUsuario;
import servidor.Servidor;

public class InicioSesion extends ComandoServer {

	@Override
	public void ejecutar() {
		Paquete paqueteSv = new Paquete(null, 0);
		paqueteSv.setComando(Comando.INICIOSESION);

		conexionCliente.setPaqueteUsuario((gson.fromJson(cadenaLeida, PaqueteUsuario.class)));

		try {
			if (Servidor.getConector().loguearUsuario(conexionCliente.getPaqueteUsuario())) {

				PaquetePersonaje paquetePersonaje = new PaquetePersonaje();
				paquetePersonaje = Servidor.getConector().getPersonaje(conexionCliente.getPaqueteUsuario());
				paquetePersonaje.setComando(Comando.INICIOSESION);
				paquetePersonaje.setMensaje(Paquete.msjExito);
				//conexionCliente.setIdPersonaje(paquetePersonaje.getId());

				conexionCliente.getSalida().writeObject(gson.toJson(paquetePersonaje));
			} else {
				paqueteSv.setMensaje(Paquete.msjFracaso);
				conexionCliente.getSalida().writeObject(gson.toJson(paqueteSv));
			}
		} catch (IOException e) {
			Servidor.log.append("Fallá al intentar iniciar sesión \n");
		}

	}

}
