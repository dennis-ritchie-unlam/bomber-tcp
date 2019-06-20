package servidor.comando;

import cliente.comando.Comando;
import servidor.ConexionCliente;

public abstract class ComandoServer extends Comando {
	protected ConexionCliente conexionCliente;

	public void setEscuchaCliente(final ConexionCliente conexionCliente) {
		this.conexionCliente = conexionCliente;
	}

}