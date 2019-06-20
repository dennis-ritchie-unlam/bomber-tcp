package mensaje;

import java.io.Serializable;

import javax.swing.JOptionPane;

import cliente.comando.Comando;
import cliente.comando.Nada;

public class Paquete implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	public static String msjExito = "1";
	public static String msjFracaso = "0";

	private String mensaje;
	private String ip;
	private int comando;

	public Paquete() {

	}

	public Paquete(final String mensaje, final String nick, final String ip, final int comando) {
		this.mensaje = mensaje;
		this.ip = ip;
		this.comando = comando;
	}

	public Paquete(final String mensaje, final int comando) {
		this.mensaje = mensaje;
		this.comando = comando;
	}

	public Paquete(final int comando) {
		this.comando = comando;
	}

	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

	public void setIp(final String ip) {
		this.ip = ip;
	}

	public void setComando(final int comando) {
		this.comando = comando;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getIp() {
		return ip;
	}

	public int getComando() {
		return comando;
	}

	@Override
	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (final CloneNotSupportedException ex) {
			JOptionPane.showMessageDialog(null, "Error al clonar");

		}
		return obj;
	}

	public Comando getObjeto(final String nombrePaquete) {
		try {
			return (Comando) Class.forName(nombrePaquete + "." + Comando.CLASSNAMES[comando]).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "nombrePaquete " + nombrePaquete + " excepción " + e.getMessage());

			return new Nada();
		}
	}

	public static Comando getObjetoSet(final String nombrePaquete, final int accion) {
		try {
			return (Comando) Class.forName(nombrePaquete + "." + Comando.CLASSNAMESBIS[accion]).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "nombrePaquete " + nombrePaquete + " accion " +  accion + " excepción " + e.getMessage());
			
			return new Nada();
		}
	}

}
