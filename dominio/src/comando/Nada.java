package comando;


/**
 * Clase que consiste en un comando que no hace nada. Es devuelto por algunos
 * métodos cuando se produce un error al intentar obtener el comando del
 * paquete.
 */
public class Nada extends ComandoEscucha {

    /**
     * Constructor vacío
     */
    public Nada() {

    }

    /**
     * No hace nada
     */
    @Override
    public void ejecutar() {
    }
}