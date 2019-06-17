package mensaje;

import java.io.Serializable;

public class PaqueteUsuario extends Paquete implements Serializable, Cloneable {

	private int idPj;
	private String username;
	private String password;
	private boolean inicioSesion;

	public PaqueteUsuario() {

	}

	public PaqueteUsuario(final int pj, final String user, final String pw) {
		idPj = pj;
		username = user;
		password = pw;
		inicioSesion = false;
	}

	public int getIdPj() {
		return idPj;
	}

	public void setIdPj(final int idPj) {
		this.idPj = idPj;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean isInicioSesion() {
		return inicioSesion;
	}

	public void setInicioSesion(final boolean inicioSesion) {
		this.inicioSesion = inicioSesion;
	}

	@Override
	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}

}
