package cliente.comando;

import javax.swing.JOptionPane;

import mensaje.Paquete;
import mensaje.PaquetePersonaje;

public class InicioSesion extends ComandoCliente {

	@Override
	public void ejecutar() {
		Paquete paquete = gson.fromJson(cadenaLeida, Paquete.class);
		if (paquete.getMensaje().equals(Paquete.msjExito)) {

			cliente.getPaqueteUsuario().setInicioSesion(true);

			//cliente.setPaquetePersonaje(gson.fromJson(cadenaLeida, PaquetePersonaje.class));

		} else {
			if (paquete.getMensaje().equals(Paquete.msjFracaso)) {
				JOptionPane.showMessageDialog(null,
						"Error al iniciar sesión." + " Revise el usuario y la contraseña");
			}
			cliente.getPaqueteUsuario().setInicioSesion(false);
		}

	}

}
