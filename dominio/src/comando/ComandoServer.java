package comando;

import comando.Comando;
import servidor.ConexionCliente;

public abstract class ComandoServer extends Comando {
	protected ConexionCliente escuchaCliente;

	public void setEscuchaCliente(final ConexionCliente escuchaCliente) {
		this.escuchaCliente = escuchaCliente;
	}

}