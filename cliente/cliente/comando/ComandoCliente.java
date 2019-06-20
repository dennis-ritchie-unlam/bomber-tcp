package cliente.comando;

import cliente.Cliente;

public class ComandoCliente extends Comando {
	protected Cliente cliente;

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public void ejecutar() {
	}
}
