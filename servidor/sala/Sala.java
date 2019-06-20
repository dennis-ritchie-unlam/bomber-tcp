package sala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import cliente.Cliente;
import servidor.Servidor;

public class Sala {
    private ArrayList<Cliente> clientes;
    private String nombre;
    
    public Sala(String nombre) {
        this.nombre = nombre;
        this.clientes = new ArrayList<>(4);
    }
    
    public String entrar(Cliente u) {
        if (!existeCliente(u)) {
        	if(this.clientes.size() == 4) {
        		return "401 SALA LLENA !!!!! HDP";
        	}
            clientes.add(u);
            
            difundir(u.getPaqueteUsuario().getUsername() + " a entrado a la sala " + this.nombre);

            actualizarListadoClientes();
            
            //Log.log(u.getPaqueteUsuario().getUsername() + " a entrado a la sala " + this.nombre);

//            u.enviar("SALA " + this.nombre);
            
            return "200 OK";
        } else {

            return "400 El Cliente ya está en la sala";
        }
    }
    
    public void salir(Cliente u) {
        if (existeCliente(u)) {
            clientes.remove(u);

            difundir(u.getPaqueteUsuario().getUsername() + " a salido de la sala " + this.nombre);

            actualizarListadoClientes();
            
            //Log.log(u.getPaqueteUsuario().getUsername() + " a salido de la sala " + this.nombre);
        }
    }
    
    public boolean existeCliente(Cliente u) {
        for (Cliente usr : clientes) {
            if (usr.getPaqueteUsuario().getUsername().equalsIgnoreCase(u.getPaqueteUsuario().getUsername())) {
                return true;
            }
        }
        return false;
    }
    
    public void difundir(String mensaje) {
        for (Cliente usr : clientes) {
            usr.enviar(mensaje);
        }
    }
    
    public void difundirSalas() {
        for (Cliente usr : clientes) {
            //usr.enviarListaSalas();
        }
    }
    
    public Cliente obtenerCliente(String nick) {
        for (Cliente usr : clientes) {
            if (usr.getPaqueteUsuario().getUsername().equalsIgnoreCase(nick)) {
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
    
    public int getCountClientes() {
        return clientes.size();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void actualizarListadoClientes() {
        for (Cliente usr : clientes) {
            //usr.enviarListaClientes();
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
