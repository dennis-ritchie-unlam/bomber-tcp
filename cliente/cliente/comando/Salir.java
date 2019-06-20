package cliente.comando;

import java.io.IOException;

import javax.swing.JOptionPane;

import mensaje.Paquete;

public class Salir extends ComandoCliente {

	@Override
	public void ejecutar() {
		try {
			
			cliente.getPaqueteUsuario().setInicioSesion(false);
		    cliente.getSalida().writeObject(gson.toJson(new Paquete(Comando.DESCONECTAR), Paquete.class));
		    cliente.getCliente().close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al salir");
		}
	}
}
