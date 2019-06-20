package cliente.comando;

import cliente.comando.Comando;
import partida.Partida;

public abstract class ComandoEscucha extends Comando {
	protected Partida partida;

	public void setPartida(final Partida partida) {
		this.partida = partida;
	}
}