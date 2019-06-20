package cliente.comando;

import cliente.Cliente;

public abstract class ComandoCliente extends Comando {
	protected Cliente cliente;

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}
}
