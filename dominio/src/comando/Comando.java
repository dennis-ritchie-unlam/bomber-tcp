package comando;

import com.google.gson.Gson;

public abstract class Comando {
	public static final String NOMBREPAQUETE = "comandos";
	public static final String[] CLASSNAMES = { "Conexion", "CrearPersonaje", "Desconectar", "InicioSesion",
			"Movimiento", "Registro", "Salir", "Nada" };
	public static final String[] CLASSNAMESBIS = { "Conexion", "CrearPersonaje", "Desconectar", "InicioSesionSet",
			"Movimiento", "RegistroSet", "SalirSet", "Nada" };

	public static final int CONEXION = 0;
	public static final int CREACIONPJ = 1;
	public static final int DESCONECTAR = 2;
	public static final int INICIOSESION = 3;
	public static final int MOVIMIENTO = 4;
	public static final int REGISTRO = 5;
	public static final int SALIR = 6;
	public static final int NADA = 7;

	protected final Gson gson = new Gson();
	protected String cadenaLeida;

	public void setCadena(final String cadenaLeidaBis) {
		this.cadenaLeida = cadenaLeidaBis;
	}

	public abstract void ejecutar();
}
