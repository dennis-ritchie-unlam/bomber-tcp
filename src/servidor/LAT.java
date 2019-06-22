package servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
// Lector de Archivo de texto (Usuario y contraseña)
public class LAT {
	
	public HashMap<String, String> leerArch(String path) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(path));
		sc.useLocale(Locale.ENGLISH);
		HashMap<String, String> mapa = new HashMap<String, String>();
		
		while(sc.hasNext()) {
			String usuarioYContra = sc.next();
			String[] listaUserYContra = usuarioYContra.split("|");
			mapa.put(listaUserYContra[0], listaUserYContra[1]);
		}
		
		sc.close();
		return mapa;
	}

}

