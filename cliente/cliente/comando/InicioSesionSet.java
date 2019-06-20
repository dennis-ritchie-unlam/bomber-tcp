package cliente.comando;

public class InicioSesionSet extends ComandoCliente {

	@Override
	public void ejecutar() {
		cliente.getPaqueteUsuario().setComando(Comando.INICIOSESION);
	}

}
