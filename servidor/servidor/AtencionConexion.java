package servidor;

import com.google.gson.Gson;

import cliente.comando.Comando;
import mensaje.PaqueteDePersonaje;

public class AtencionConexion extends Thread {

	private final Gson gson = new Gson();

	public AtencionConexion() {

	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				while (true){
					wait();
					for (ConexionCliente conectado : Servidor.getClientesConectados()) {
//						if (conectado.getPaquetePersonaje().getEstado() != Estado.getEstadoOffline()) {
							PaqueteDePersonaje pdp = (PaqueteDePersonaje) new PaqueteDePersonaje(
									Servidor.getPersonajesConectados()).clone();
							pdp.setComando(Comando.CONEXION);
							synchronized (conectado) {
								conectado.getSalida().writeObject(gson.toJson(pdp));
							}
//						}
					}
				}
			} catch (Exception e) {
				Servidor.log.append("Falló al intentar enviar paqueteDePersonajes\n");
			}
		}
	}
}
