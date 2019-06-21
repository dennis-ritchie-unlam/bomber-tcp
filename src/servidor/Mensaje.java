package servidor;

import java.util.Arrays;
import java.util.Observable;

public class Mensaje extends Observable {
	
	private boolean[] comandos;
    private String mensaje;
    
    public Mensaje() {
    	comandos =  new boolean[4];
    }
    
    public boolean[] getComando() {
    	return comandos;
    }
    
    public String getValorMensaje() {
        return mensaje;
    }

    public void setMensaje(int comando, String mensaje) {
    	Arrays.fill(comandos, false);
    	this.comandos[comando] = true;
        this.mensaje = mensaje;
        this.setChanged();
        this.notifyObservers(mensaje);
    }
}
