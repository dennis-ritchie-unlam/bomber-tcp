package comando;

import partida.Partida;
import comando.Comando;

public abstract class ComandoEscucha extends Comando {
	protected Partida partida;

	public void setPartida(final Partida partida) {
		this.partida = partida;
	}
}