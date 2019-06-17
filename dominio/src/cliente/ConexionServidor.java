package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import comando.Comando;
import comando.ComandoEscucha;
import comando.ComandoServer;
import partida.Partida;
import mensaje.Paquete;
import mensaje.PaqueteUsuario;

public class ConexionServidor extends Thread {

	private Partida partida;
	private Cliente cliente;
	private ObjectInputStream entrada;
	private final Gson gson = new Gson();
//
//	private Map<Integer, PaqueteMovimiento> ubicacionPersonajes;
//	private Map<Integer, PaquetePersonaje> personajesConectados;

	/**
	 * Constructor de EsuchaMensaje 
	 * @param juego juego del que se escucha el mensaje
	 */
	public ConexionServidor(final Partida partida) {
		this.partida = partida;
		cliente = partida.getCliente();
		entrada = cliente.getEntrada();
	}

	@Override
	public void run() {

		try {
			Paquete paquete;
			ComandoEscucha comando;

			while (true) {
				String objetoLeido = (String) entrada.readObject();

				paquete = gson.fromJson(objetoLeido, Paquete.class);
				comando = (ComandoEscucha) paquete.getObjeto(Comando.NOMBREPAQUETE);
				comando.setPartida(partida);
				comando.setCadena(objetoLeido);
				comando.ejecutar();

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexión con el servidor (EscuchaMensajes)");
		}
	}
}
