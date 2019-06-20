package servidor.comando;

import java.io.IOException;

import mensaje.Paquete;
import servidor.Servidor;

public class Salir extends ComandoServer {

	@Override
	public void ejecutar() {
		// Cierro todo
		try {
			conexionCliente.getEntrada().close();
			conexionCliente.getSalida().close();
			conexionCliente.getSocket().close();
		} catch (IOException e) {
			Servidor.log.append("Falló al intentar salir \n");

		}
		Servidor.getClientesConectados().remove(this);
		Paquete paquete = gson.fromJson(cadenaLeida, Paquete.class);
		Servidor.log.append(paquete.getIp() + " se ha desconectado." + System.lineSeparator());
	}

}
