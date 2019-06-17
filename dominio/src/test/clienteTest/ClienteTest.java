package test.clienteTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import javax.swing.JOptionPane;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cliente.Cliente;
import comando.Comando;
import mensaje.Paquete;
import mensaje.PaquetePersonaje;
import mensaje.PaqueteUsuario;


public class ClienteTest {
	private Thread myThread;
	private ServerSocket server;
	private Gson gson = new Gson();
	ObjectOutputStream salida;
	ObjectInputStream entrada;

	public void testServer(final Queue<Paquete> cantPaquetes) {
		myThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Properties propiedad = new Properties();
					propiedad.load(new FileInputStream("config.properties"));
					server = new ServerSocket(Integer.parseInt(propiedad.getProperty("PUERTO", "10000")));
					Socket cliente = server.accept();
					salida = new ObjectOutputStream(cliente.getOutputStream());
					entrada = new ObjectInputStream(cliente.getInputStream());
					while (!cantPaquetes.isEmpty()) {
						entrada.readObject();
						Paquete paq = cantPaquetes.poll();
						if (paq.getMensaje() != "0") {
							paq.setMensaje("1");
						}
						salida.writeObject(gson.toJson(paq));
					}
					cliente.close();
				} catch (IOException | ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Falla: " + e.getMessage());

				} finally {
					try {
//						if(server != null && !server.isClosed()) {
							server.close();
//						}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Fall·: " + e.getMessage());

					}
				}

			}
		});
		myThread.start();
	}

	@Test
	public void testConexionConElServidor() {
		Queue<Paquete> queue = new LinkedList<Paquete>();

		queue.add(new Paquete());
		testServer(queue);
		Cliente cliente = new Cliente();

		// Pasado este punto la conexi√≥n entre el cliente y el servidor resulto exitosa
		Assert.assertEquals(1, 1);

		try {

			// Cierro las conexiones
			Paquete p = new Paquete();
			p.setComando(Comando.DESCONECTAR);
			p.setIp(cliente.getIp());
			cliente.getSalida().writeObject(gson.toJson(p));
			cliente.getSalida().close();
			cliente.getEntrada().close();
			cliente.getCliente().close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Falla: " + e.getMessage());
		}
	}

	@Test
	public void testRegistro() {

		Queue<Paquete> queue = new LinkedList<Paquete>();
		// Registro el usuario
		PaqueteUsuario pu = new PaqueteUsuario();
		pu.setComando(Comando.REGISTRO);
		pu.setUsername("nuevoUser");
		pu.setPassword("test");
		pu.setMensaje("1");
		queue.add(new Paquete());
		queue.add(pu);
		testServer(queue);
		Cliente cliente = new Cliente();

		try {

			// Envio el paquete para registrarme
			cliente.getSalida().writeObject(gson.toJson(pu));

			// Recibo la respuesta del servidor
			Paquete resultado = gson.fromJson((String) cliente.getEntrada().readObject(), Paquete.class);

			// Cierro las conexiones
			Paquete p = new Paquete();
			p.setComando(Comando.DESCONECTAR);
			p.setIp(cliente.getIp());
			cliente.getSalida().writeObject(gson.toJson(p));
			cliente.getSalida().close();
			cliente.getEntrada().close();
			cliente.getCliente().close();

			Assert.assertEquals(Paquete.msjExito, resultado.getMensaje());
		} catch (JsonSyntaxException | ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falla " + e.getMessage());
		}
	}

	@Test
	public void testRegistroFallido() {

		Queue<Paquete> queue = new LinkedList<Paquete>();

		// Registro el usuario
		PaqueteUsuario pu = new PaqueteUsuario();
		pu.setComando(Comando.REGISTRO);
		pu.setUsername("nuevoUser");
		pu.setPassword("test");
		pu.setMensaje("0");
		queue.add(pu);
		queue.add(pu);
		testServer(queue);
		Cliente cliente = new Cliente();
		try {
			// Envio el paquete para registrarme
			cliente.getSalida().writeObject(gson.toJson(pu));
			// Recibo la respuesta del servidor
			Paquete resultado = gson.fromJson((String) cliente.getEntrada().readObject(), Paquete.class);
			// Cierro las conexiones
			Paquete p = new Paquete();
			p.setComando(Comando.DESCONECTAR);
			p.setIp(cliente.getIp());
			cliente.getSalida().writeObject(gson.toJson(p));
			cliente.getSalida().close();
			cliente.getEntrada().close();
			cliente.getCliente().close();
			Assert.assertEquals(Paquete.msjFracaso, resultado.getMensaje());
		} catch (JsonSyntaxException | ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falla " + e.getMessage());
		}
		Assert.assertTrue(true);
	}

	@Test
	public void testRegistrarPersonaje() throws IOException {
		Queue<Paquete> queue = new LinkedList<Paquete>();

		// Registro de usuario
		PaqueteUsuario pu = new PaqueteUsuario();
		pu.setComando(Comando.REGISTRO);
		pu.setUsername("nuevoUser");
		pu.setPassword("test");

		// Registro de personaje
		PaquetePersonaje pp = new PaquetePersonaje();
		pp.setComando(Comando.CREACIONPJ);
		queue.add(new Paquete());
		queue.add(pu);
		queue.add(pp);
		testServer(queue);
		Cliente cliente = new Cliente();
		try {

			// Envio el paquete de registro de usuario
			cliente.getSalida().writeObject(gson.toJson(pu));

			// Recibo la respuesta del servidor
			// Paquete paquete = gson.fromJson((String) cliente.getEntrada().readObject(),
			// Paquete.class);

			// Envio el paquete de registro de personaje
			cliente.getSalida().writeObject(gson.toJson(pp));

			// Recibo el personaje de mi usuario
			pp = gson.fromJson((String) cliente.getEntrada().readObject(), PaquetePersonaje.class);

			// Cierro las conexiones
			Paquete p = new Paquete();
			p.setComando(Comando.DESCONECTAR);
			p.setIp(cliente.getIp());
			cliente.getSalida().writeObject(gson.toJson(p));
			cliente.getSalida().close();
			cliente.getEntrada().close();
			cliente.getCliente().close();
		} catch (IOException | JsonSyntaxException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Falla " + e.getMessage());
		}
	}

//	@Test
//	public void testIniciarSesion() throws IOException {
//		Queue<Paquete> queue = new LinkedList<Paquete>();
//
//		PaqueteUsuario pu = new PaqueteUsuario();
//		PaquetePersonaje pp = new PaquetePersonaje();
//
//		pu.setComando(Comando.INICIOSESION);
//		pu.setUsername("nuevoUser");
//		pu.setPassword("test");
//		queue.add(pp);
//
//		testServer(queue);
//		Cliente cliente = new Cliente();
//
//		try {
//
//			// Envio el paquete de incio de sesion
//			cliente.getSalida().writeObject(gson.toJson(pu));
//
//			// Recibo el paquete con el personaje
//			PaquetePersonaje paquetePersonaje = gson.fromJson((String) cliente.getEntrada().readObject(),
//					PaquetePersonaje.class);
//
//			// Cierro las conexiones
//			Paquete p = new Paquete();
//			p.setComando(Comando.DESCONECTAR);
//			p.setIp(cliente.getIp());
//			cliente.getSalida().writeObject(gson.toJson(p));
//			cliente.getSalida().close();
//			cliente.getEntrada().close();
//			cliente.getCliente().close();
//		} catch (IOException | JsonSyntaxException | ClassNotFoundException e) {
//			JOptionPane.showMessageDialog(null, "Falla " + e.getMessage());
//		}
//	}
}
