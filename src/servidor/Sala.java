package servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sala {
    private ArrayList<Usuario> usuarios;
    private String nombre;
    
    public Sala(String nombre) {
        this.nombre = nombre;
        this.usuarios = new ArrayList<>(4);
    }
    
    public String entrar(Usuario u) {
        if (!existeUsuario(u)) {
        	if(this.usuarios.size() == 4) {
        		return "401 SALA LLENA !!!!! HDP";
        	}
            usuarios.add(u);
            
            difundir(u.getNick() + " a entrado a la sala " + this.nombre);

            actualizarListadoUsuarios();
            
            //Log.log(u.getNick() + " a entrado a la sala " + this.nombre);

//          u.enviar("SALA " + this.nombre);
            
            return "200 OK";
        } else {

            return "400 El Usuario ya está en la sala";
        }
    }
    
    public void salir(Usuario u) {
        if (existeUsuario(u)) {
            usuarios.remove(u);

            difundir(u.getNick() + " a salido de la sala " + this.nombre);

            actualizarListadoUsuarios();
            
            //Log.log(u.getNick() + " a salido de la sala " + this.nombre);
        }
    }
    
    public boolean existeUsuario(Usuario u) {
        for (Usuario usr : usuarios) {
            if (usr.getNick().equalsIgnoreCase(u.getNick())) {
                return true;
            }
        }
        return false;
    }
    
    public void difundir(String mensaje) {
        for (Usuario usr : usuarios) {
            usr.enviar(mensaje);
        }
    }
    
    public void difundirSalas() {
        for (Usuario usr : usuarios) {
            usr.enviarListaSalas();
        }
    }
    
    public Usuario obtenerUsuario(String nick) {
        for (Usuario usr : usuarios) {
            if (usr.getNick().equalsIgnoreCase(nick)) {
                return usr;
            }
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getCountUsuarios() {
        return usuarios.size();
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void actualizarListadoUsuarios() {
        for (Usuario usr : usuarios) {
            //usr.enviarListaUsuarios();
        }
    }
    
//    public void enviarListaSalas() {
//        StringBuilder strb = new StringBuilder();
//        strb.append("LSALAS ");
//        Sala[] s = Servidor.obtenerSalas(); 
//        for (Sala sal : s ) {
//            strb.append(sal.getNombre());
//            strb.append(" ");
//        }
//        enviar(strb.toString());
//    }
    
}
