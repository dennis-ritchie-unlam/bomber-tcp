package comando;

import java.io.IOException;

import mensaje.Paquete;

public class Salir extends ComandoServer {

	@Override
	public void ejecutar() {
		try {
		    escuchaCliente.getEntrada().close();
		    escuchaCliente.getSalida().close();
		    escuchaCliente.getSocket().close();
		} catch (IOException e) {
//		    Servidor.log.append("Falló al intentar salir \n");
	
		}
		// Lo elimino de los clientes conectados
//		Servidor.getClientesConectados().remove(this);
		Paquete paquete = gson.fromJson(cadenaLeida, Paquete.class);
		// Indico que se desconecto
//		Servidor.log.append(paquete.getIp() + " se ha desconectado." + System.lineSeparator());
	}
}
