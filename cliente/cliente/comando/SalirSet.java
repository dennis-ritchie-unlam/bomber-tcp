package cliente.comando;

public class SalirSet extends ComandoCliente {

	@Override
	public void ejecutar() {
		cliente.getPaqueteUsuario().setIp(cliente.getIp());
		cliente.getPaqueteUsuario().setComando(Comando.SALIR);
	}

}
