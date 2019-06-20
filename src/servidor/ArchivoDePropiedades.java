package servidor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ArchivoDePropiedades {
	
	private Properties propiedades;
	private String ip;
	private int puerto;
	private String archivo;
	
	public ArchivoDePropiedades(String archivo) {
		propiedades = new Properties();
		this.archivo = archivo;
	}
	
	public void lectura(){
		try {
			propiedades.load(new FileInputStream(archivo));
			ip = propiedades.getProperty("IP","localhost");
			puerto = Integer.parseInt(propiedades.getProperty("PUERTO","10000"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void escritura(String ip, int puerto){
		try {
			propiedades.store(new FileOutputStream(archivo), null);
			propiedades.setProperty("IP", ip);
			propiedades.setProperty("PUERTO", "" + puerto);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIp() {
		return ip;
	}

	public int getPuerto() {
		return puerto;
	}
	
}
