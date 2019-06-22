package servidor;

import com.google.gson.Gson;

import paquete.Paquete;

/**
 * Clase AtencionConexion
 */
public class AtencionConexiones extends Thread {

	private final Gson gson = new Gson();

	public AtencionConexiones() {

	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				while (true){
					wait();
					for (ConexionCliente conectado : ServidorJuego.getClientesConectados()) {
//						if (conectado.getPaquetePersonaje().getEstado() != Estado.getEstadoOffline()) {
//							PaqueteDePersonajes pdp = (PaqueteDePersonajes) new PaqueteDePersonajes(
//									Servidor.getPersonajesConectados()).clone();
//							pdp.setComando(Comando.CONEXION);
							synchronized (conectado) {
								conectado.getSalidaDatos().writeUTF(gson.toJson(new Paquete(), Paquete.class));
							}
//						}
					}
				}
			} catch (Exception e) {
				ServidorJuego.log.append("Falló al intentar enviar paqueteDePersonajes\n");
			}
		}
	}
}