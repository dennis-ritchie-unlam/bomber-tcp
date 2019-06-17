package partida;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JOptionPane;

//import chat.MiChat;
import cliente.Cliente;
import servidor.ConexionCliente;

public class Partida implements Runnable {

//	private Pantalla pantalla;

	private final String nOMBRE;

	private final int aNCHO;

	private final int aLTO;

	private static final int REFRESCO = 1000000000;

	private static final int FPS = 60;

	private static final int FRAMESBUFFERED = 3;

	private static final int TAMFUENTE = 15;

	private Thread hilo;

	private boolean corriendo;

	private BufferStrategy bs;

	private Graphics g;

//    private Estado estadoJuego;

	private Cliente cliente;

	private ConexionCliente escuchaMensajes;

//	private PaquetePersonaje paquetePersonaje;
//
//	private PaqueteMovimiento ubicacionPersonaje;
//
//	private Map<Integer, PaquetePersonaje> personajesConectados;
//
//	private Map<Integer, PaqueteMovimiento> ubicacionPersonajes;
//
//	private Map<String, MiChat> chatsActivos = new HashMap<>();

	public Partida(final String nombre, final int ancho, final int alto, final Cliente cliente/*,
			final PaquetePersonaje pp*/) {
		this.nOMBRE = nombre;
		this.aLTO = alto;
		this.aNCHO = ancho;
		this.cliente = cliente;
//		this.paquetePersonaje = pp;

		// Inicializo la ubicacion del personaje
//		ubicacionPersonaje = new PaqueteMovimiento();
//		ubicacionPersonaje.setIdPersonaje(paquetePersonaje.getId());
//		ubicacionPersonaje.setFrame(0);
//		ubicacionPersonaje.setDireccion((1 + 1 + 1 + 1 + 1 + 1));

		// Creo el escucha de mensajes
//		escuchaMensajes = new ConexionServidor(this);
//		escuchaMensajes.start();

		iniciar();

//		cargarRecursos = new CargarRecursos(cliente);
//		cargarRecursos.start();
	}

	public void iniciar() {
//		pantalla = new Pantalla(nombre, ancho, alto, cliente);
	}

	private void actualizar() {
//		if (Estado.getEstado() != null) {
//			Estado.getEstado().actualizar();
//		}
	}

	/**
	 * Metodo para graficar los objetos
	 */
	private void graficar() {
		// Grafica los objetos y sus posiciones
//		bs = pantalla.getCanvas().getBufferStrategy();
//		if (bs == null) {
//			// Seteo una estrategia para el canvas en caso de que no tenga una
//			pantalla.getCanvas().createBufferStrategy(FRAMESBUFFERED);
//			return;
//		}
//
//		g = bs.getDrawGraphics(); // Permite graficar el buffer mediante g
//
//		g.clearRect(0, 0, aNCHO, aLTO); // Limpiamos la pantalla
//
//		// Graficado de imagenes
//		g.setFont(new Font("Book Antiqua", 1, TAMFUENTE));
//
//		if (Estado.getEstado() != null) {
//			Estado.getEstado().graficar(g);
//		}
//
//		// Fin de graficado de imagenes
//
//		bs.show(); // Hace visible el próximo buffer disponible
//		g.dispose();
	}

	@Override
	public void run() {
//		// Hilo principal del juego
//
//		// Cantidad de actualizaciones por segundo que se desean
//		int fps = FPS;
//		// Cantidad de nanosegundos en FPS deseados
//		double tiempoPorActualizacion = REFRESCO / fps;
//		double delta = 0;
//		long ahora;
//		long ultimoTiempo = System.nanoTime();
//		long timer = 0;
//		// Timer para mostrar fps cada un segundo
//		int actualizaciones = 0;
//		// Cantidad de actualizaciones que se realizan realmente
//
//		while (corriendo) {
//			ahora = System.nanoTime();
//			// Calculo para determinar cuando realizar la actualizacion y el
//			// graficado
//			delta += (ahora - ultimoTiempo) / tiempoPorActualizacion;
//			// Sumo el tiempo transcurrido hasta que se acumule 1 segundo y
//			// mostrar los FPS
//			timer += ahora - ultimoTiempo;
//			// Para las proximas corridas del bucle
//			ultimoTiempo = ahora;
//
//			if (delta >= 1) {
//				actualizar();
//				graficar();
//				actualizaciones++;
//				delta--;
//			}
//
//			if (timer >= REFRESCO) {
//				// Si paso 1 segundo muestro los FPS
//				pantalla.getFrame().setTitle(nOMBRE + " | " + "FPS: " + actualizaciones);
//				actualizaciones = 0;
//				timer = 0;
//			}
//		}

		stop();
	}

	/**
	 * Metodo para iniciar el hilo del juego
	 * 
	 * @throws IOException exception lanzada por el hilo
	 */
	public synchronized void start() throws IOException {
//		// Inicia el juego
//		if (corriendo) {
//			return;
//		}
//
//		estadoJuego = new EstadoJuego(this);
//		Estado.setEstado(estadoJuego);
//		pantalla.mostrar();
//		corriendo = true;
//		hilo = new Thread(this);
//		hilo.start();
	}

	/**
	 * Metodo para cerrar el hilo corriendo del juego
	 */
	public synchronized void stop() {
		// Detiene el juego
		if (!corriendo) {
			return;
		}
		try {
			corriendo = false;
			hilo.join();
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Fallo al intentar detener el juego.");
		}
	}

	/**
	 * @return ancho del juego
	 */
	public int getAncho() {
		return aNCHO;
	}

	/**
	 * @return alto del juego
	 */
	public int getAlto() {
		return aLTO;
	}

//	/**
//	 * @return estadoJuego
//	 */
//	public EstadoJuego getEstadoJuego() {
//		return (EstadoJuego) estadoJuego;
//	}

	/**
	 * @return cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

//	/**
//	 * @return escuchaMensajes
//	 */
//	public ConexionServidor getConexionServidor() {
//		return conexionServidor;
//	}

//	/**
//	 * @return paquetePersonaje
//	 */
//	public PaquetePersonaje getPersonaje() {
//		return paquetePersonaje;
//	}

//	/**
//	 * @return ubicacionPersonaje
//	 */
//	public PaqueteMovimiento getUbicacionPersonaje() {
//		return ubicacionPersonaje;
//	}

//	/**
//	 * @param paquetePersonajeParam paquete del Personaje
//	 */
//	public void setPersonaje(final PaquetePersonaje paquetePersonajeParam) {
//		this.paquetePersonaje = paquetePersonajeParam;
//	}

//	/**
//	 * Metodo para actualizar el paquete Personaje
//	 */
//	public void actualizarPersonaje() {
//		paquetePersonaje = (PaquetePersonaje) (personajesConectados.get(paquetePersonaje.getId()).clone());
//	}

//	/**
//	 * @return personajesConectados mapa con los personajes Conectados
//	 */
//	public Map<Integer, PaquetePersonaje> getPersonajesConectados() {
//		return personajesConectados;
//	}

//	/**
//	 * Metodo para setear los personajes conectados
//	 * 
//	 * @param map mapa que contiene los personajes
//	 */
//	public void setPersonajesConectados(final Map<Integer, PaquetePersonaje> map) {
//		this.personajesConectados = map;
//	}

//	/**
//	 * @return ubicacionPersonaje mapa de las ubicaciones
//	 */
//	public Map<Integer, PaqueteMovimiento> getUbicacionPersonajes() {
//		return ubicacionPersonajes;
//	}

//	/**
//	 * Metodo para setear las posiciones de los NPC
//	 * 
//	 * @param ubicacionPersonajes mapa que contiene los paqueteMovimiento de los
//	 *                            Personajes
//	 */
//	public void setUbicacionPersonajes(final Map<Integer, PaqueteMovimiento> ubicacionPersonajes) {
//		this.ubicacionPersonajes = ubicacionPersonajes;
//	}
}
