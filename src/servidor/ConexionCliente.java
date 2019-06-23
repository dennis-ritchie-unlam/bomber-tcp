package servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import comando.Comando;
import entidades.Bomba;
import entidades.Bomber;
import entidades.Colisionador;
import entidades.Mapa;
import paquete.Paquete;
import paquete.PaqueteJuego;
import paquete.PaqueteSession;

public class ConexionCliente extends Thread implements Observer {
	private Socket socket;
	private Mensaje mensaje;
	private DataInputStream entradaDatos;
	private DataOutputStream salidaDatos;
	private Gson gson;
	private Bomber bomber;
	private Mapa mapa;
	private Colisionador colisionador;
	private ArrayList<Bomba> bombas;
	private boolean[] direccion;
	final int BLOCK_SIZE = 32;

	public ConexionCliente(Socket socket, Mensaje mensajes, Mapa mapa/* , Sala sala */, final DataInputStream entrada,
			final DataOutputStream salida) {
		this.mensaje = mensajes;
		this.socket = socket;
		this.entradaDatos = entrada;
		this.salidaDatos = salida;
		this.mapa = mapa;
		gson = new Gson();
        colisionador = new Colisionador(this.mapa);
        bombas = new ArrayList<Bomba>();
        direccion = new boolean[4];
	}

//	 @Override
//	    public void run() {
//	        String mensajeRecibido;
//	        boolean conectado = true;
//	        mensaje.addObserver(this);
//	        
//	        while (conectado) {
//	            try {
//	                mensajeRecibido = entradaDatos.readUTF();
//	                accion(Integer.parseInt(mensajeRecibido), direccion);
//	                mensaje.setMensaje(gson.toJson(new PaqueteEnviado(mapa, bombas)));
//	            } catch (IOException e) {
//	                conectado = false;
//	                JOptionPane.showMessageDialog(null, "El cliente ha salido del servidor");
//	                try {
//	                    entradaDatos.close();
//	                    salidaDatos.close();
//	                } catch (IOException e1) {
//	                	e1.printStackTrace();
//	                }
//	            }
//	        }
//	    }
	 
	@Override
	public void run() {
		boolean conectado = true;
		// mensaje.addObserver(this);

		JOptionPane.showMessageDialog(null, "ANTES DE LEER");

		while (conectado) {
			try {
				String datos = entradaDatos.readUTF();
				Paquete paquete = gson.fromJson(datos, Paquete.class);
				String comando = paquete.getComando();
				comando = (comando == null || comando.isEmpty()) ? "" : comando;
				switch (comando) {
				case Comando.INICIAR_SESION:
					PaqueteSession paqueteSession = gson.fromJson(datos, PaqueteSession.class);
					iniciarSesion(paqueteSession);
					break;
				case Comando.VALIDAR_SESION:

					JOptionPane.showMessageDialog(null, "VALIDAR_SESION CONEXION CLIENTE");
					PaqueteSession paqueteSessionValidar = gson.fromJson(datos, PaqueteSession.class);
					boolean sesionValida =validarSesion(paqueteSessionValidar.getUsuario(), paqueteSessionValidar.getContraseña());
					paqueteSessionValidar.setEsValido(sesionValida);
					mensaje.setMensaje(gson.toJson(paqueteSessionValidar));
					salidaDatos.writeUTF(gson.toJson(paqueteSessionValidar));
				break;
				case Comando.CERRAR_SESION:
					conectado = false;
					break;
				default:
//					resolverJuego(datos);
					PaqueteSession paqueteSession2 = gson.fromJson(datos, PaqueteSession.class);
					iniciarSesion(paqueteSession2);
				}
			} catch (IOException e) {
				conectado = false;
				JOptionPane.showMessageDialog(null, "El cliente ha salido del servidor");
				try {
					entradaDatos.close();
					salidaDatos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

//		String cadenaLeida = null;
//
//		Paquete paquete;
//		cadenaLeida = (String) entradaDatos.readUTF();
//
//		// while (!((paquete = gson.fromJson(cadenaLeida, Paquete.class)).getComando()
//		// == Comando.CERRAR_SESION)) {
//		while ((paquete = gson.fromJson(cadenaLeida, Paquete.class)) == null) {
//			PaqueteSession paq = new PaqueteSession("", "");
//
//			salidaDatos.writeUTF(gson.toJson(paq, PaqueteSession.class));
//			cadenaLeida = (String) entradaDatos.readUTF();
//
//		}
//		PaqueteSession paqueteS = new PaqueteSession("", "");
//		paqueteS.setEsValido(true);
//		paqueteS.setComando(Comando.CERRAR_SESION);
//		getSalidaDatos().writeUTF(gson.toJson(paqueteS, PaqueteSession.class));
//
//		getEntradaDatos().close();
//		getSalidaDatos().close();
//		getSocket().close();

//		while (conectado) {
//			
//			try {
//				
//				paquete = gson.fromJson(cadenaLeida, Paquete.class);
//				JOptionPane.showMessageDialog(null, "DESPUÉS DE LEER");
//				// mensaje.setMensaje(gson.toJson(paquete, Paquete.class));
////				JOptionPane.showMessageDialog(null, "ANTES DE ESCRIBIR");
//				PaqueteSession paq = new PaqueteSession("", "");
//				salidaDatos.writeUTF(gson.toJson(paq, PaqueteSession.class));
////				JOptionPane.showMessageDialog(null, "DESPUÉS DE ESCRIBIR");
//			} catch (JsonSyntaxException /* | IOException */ e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(null, "Exploto TODO");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		
	}
	
	public void crearBomber( ) {
        int cantBombers = this.mapa.getBombers().size();
        switch(cantBombers) {
		case 0:
			bomber = new Bomber(32, 32);
			this.mapa.añadirBomber(bomber);
			break;
		case 1:
			bomber = new Bomber(32, 608);
			this.mapa.añadirBomber(bomber);
			break;
		case 2:
			bomber = new Bomber(608, 32);
			this.mapa.añadirBomber(bomber);
			break;
		case 3:
			bomber = new Bomber(608, 608);
			this.mapa.añadirBomber(bomber);
			break;
		}
        //salidaDatos.writeUTF(gson.toJson(new PaqueteEnviado(mapa, bombas)));
	}
	
	public void resolverJuego(String datos) throws IOException {
		PaqueteJuego paqueteJuego = gson.fromJson(datos, PaqueteJuego.class);
		String comando = paqueteJuego.getComando();
		accionJuego(comando, direccion);
		paqueteJuego.setMapa(mapa);
		paqueteJuego.setBombas(bombas);
		mensaje.setMensaje(gson.toJson(paqueteJuego));
		salidaDatos.writeUTF(gson.toJson(paqueteJuego));
	}
	
	public void iniciarSesion(Paquete paquete) throws IOException {
		PaqueteSession paqueteSession = (PaqueteSession) paquete;
		String usuario = paqueteSession.getUsuario();
		String contraseña = paqueteSession.getContraseña();
		new PaqueteSession(usuario, contraseña);
		boolean esValido = validarSesion(usuario, contraseña);
		paqueteSession.setEsValido(esValido);
		mensaje.setMensaje(gson.toJson(paqueteSession));
		salidaDatos.writeUTF(gson.toJson(paqueteSession));
	}

	public void ingresoValido() {
		this.mapa = mapa;
		bomber = new Bomber(32, 32);
		colisionador = new Colisionador(this.mapa);
		bombas = new ArrayList<Bomba>();
		direccion = new boolean[4];
		this.mapa.añadirBomber(bomber);
	}

	public boolean validarSesion(String usuario, String contraseña) throws FileNotFoundException {
		LAT lector = new LAT();
		HashMap<String, String> mapaDeUsuarios = lector.leerArch("archivoSeguroYEncriptado.txt");

		for (String key : mapaDeUsuarios.keySet()) {
			if (usuario.equals(key) && contraseña.equals(mapaDeUsuarios.get(key)))
				return true;
		}
		return false;

	}

	private void refrescar() {
		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mensaje.setMensaje(gson.toJson(new PaqueteJuego(mapa, bombas)));
			}
		});

		timer.start();
	}

	public void accionJuego(String comando, boolean[] direccion) {
		Bomber nuevoBomber = bomber;
		int posX = nuevoBomber.getPosicionX();
		int posY = nuevoBomber.getPosicionY();
		int cantPosMover = 8;
		if (nuevoBomber.EstaVivo()) {
			if (comando == Comando.VK_SPACE) {
				if (nuevoBomber.getBombasDisponibles() > 0
						&& !colisionador.verificarColision(posX / BLOCK_SIZE, posY / BLOCK_SIZE)) {
					Bomba bombita = nuevoBomber.ponerBomba();
					if (bombita != null) {
						addBomba(bombita);

						timearBomba(bombita.getTiempoExplosion() * 1000, nuevoBomber, bombita);
					}
				}
			}
			if (comando == Comando.VK_DOWN) {

				posY = (nuevoBomber.getPosicionY() + cantPosMover * 4) / BLOCK_SIZE;
				if (posX % BLOCK_SIZE == 0) {
					posX /= BLOCK_SIZE;
				} else {
					posX = (nuevoBomber.getPosicionY() + cantPosMover * 4) / BLOCK_SIZE;
				}
				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(0, cantPosMover);
				nuevoBomber.setDireccion(0);
			} else if (comando == Comando.VK_UP) {

				posY = (nuevoBomber.getPosicionY() - cantPosMover) / BLOCK_SIZE;
				if (posX % BLOCK_SIZE == 0) {
					posX /= BLOCK_SIZE;
				} else {
					posX = (nuevoBomber.getPosicionY() - cantPosMover) / BLOCK_SIZE;
				}
				if (!colisionador.verificarColision(posX, posY)) {
					nuevoBomber.moverse(0, -cantPosMover);
				}
				nuevoBomber.setDireccion(1);
			} else if (comando == Comando.VK_LEFT) {
				posX = (nuevoBomber.getPosicionX() - cantPosMover) / BLOCK_SIZE;
				if (posY % BLOCK_SIZE == 0) {
					posY /= BLOCK_SIZE;
				} else {
					posY = (nuevoBomber.getPosicionX() - cantPosMover) / BLOCK_SIZE;
				}
				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(-cantPosMover, 0);
				nuevoBomber.setDireccion(2);
			} else if (comando == Comando.VK_RIGHT) {
				posX = (nuevoBomber.getPosicionX() + cantPosMover * 4) / BLOCK_SIZE;
				if (posY % BLOCK_SIZE == 0) {
					posY /= BLOCK_SIZE;
				} else {
					posY = (nuevoBomber.getPosicionX() + cantPosMover * 4) / BLOCK_SIZE;
				}

				if (!colisionador.verificarColision(posX, posY))
					nuevoBomber.moverse(cantPosMover, 0);
				nuevoBomber.setDireccion(3);
			}

		}

	}

	private void addBomba(Bomba bomba) {
		if (bomba != null) {
			if (bomba.getPosicionX() % BLOCK_SIZE <= BLOCK_SIZE / 2)
				bomba.setPosicionX(bomba.getPosicionX() - bomba.getPosicionX() % BLOCK_SIZE);
			else
				bomba.setPosicionX(bomba.getPosicionX() + (bomba.getPosicionX() % BLOCK_SIZE) / 3);

			if (bomba.getPosicionY() % BLOCK_SIZE <= BLOCK_SIZE / 2)
				bomba.setPosicionY(bomba.getPosicionY() - bomba.getPosicionY() % BLOCK_SIZE);
			else
				bomba.setPosicionY(bomba.getPosicionY() + (bomba.getPosicionY() % BLOCK_SIZE) / 3);
			if (this.mapa.getEntidades()[bomba.getPosicionY() / BLOCK_SIZE][bomba.getPosicionX()
					/ BLOCK_SIZE] == null) {
				this.mapa.añadirBomba(bomba, BLOCK_SIZE);
				bombas.add(bomba);
			}
		}
	}

	private void timearBomba(int tiempoMili, Bomber bomber, Bomba bomba) {
		Timer timer = new Timer(tiempoMili, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mapa.explotarBomba(bomba, BLOCK_SIZE);
				bomber.setBombasDisponibles(bomber.getBombasDisponibles() + 1);
				mensaje.setMensaje(gson.toJson(new PaqueteJuego(mapa, bombas)));
				bombas.remove(bomba);
			}
		});

		timer.start();
		timer.setRepeats(false);
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			salidaDatos.writeUTF(arg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataOutputStream getSalidaDatos() {
		return this.salidaDatos;
	}

	public DataInputStream getEntradaDatos() {
		return this.entradaDatos;
	}

	public Socket getSocket() {
		return this.socket;
	}
}
