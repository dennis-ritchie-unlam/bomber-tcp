package paquete;

public class PaqueteSession extends Paquete {
	
	private String usuario;
	private String contraseña;
	private boolean esValido;

	public PaqueteSession(String usuario, String contraseña) {
		this.contraseña = contraseña;
		this.usuario = usuario;
		this.esValido = false;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}	
	
	public boolean esValido() {
		return esValido;
	}

	public void setEsValido(boolean esValido) {
		this.esValido = esValido;
	}

}
