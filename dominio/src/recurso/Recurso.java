package recurso;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.imageio.ImageIO;

import frame.MenuCarga;

//import frames.MenuCarga;
//import frames.MenuMapas;

public class Recurso {

	private static final int CANT_ELEMENTOS = 65;
	private static final int ANCHO = 256;
	private static final int ALTO = 256;
	private static final int TAM_FILAS_SOLIDEZ = 10;
	private static final int TAM_COLUMNAS_SOLIDEZ = 8;
	private static final int ALTO_TILE = 64;
	private static final int ANCHO_TILE = 64;
	private static final int TAM_SOLIDEZ_MAPA = 81;
	private static final int SPRITE_ABAJO_IZQ = 7;
	private static final int SPRITE_ABAJO = 6;
	private static final int SPRITE_ABAJO_DER = 5;
	private static final int SPRITE_DER = 4;
	private static final int SPRITE_ARRIBA_DER = 3;
	private static final int SPRITE_ARRIBA = 2;
	private static int elementos = CANT_ELEMENTOS;
	private static final int TAM_BUFFER_IMAGE = SPRITE_DER;

	private static int ancho; // ancho del frame a obtener
	private static int alto; // alto del frame a obtener

	// Inicio Personajes
	// Hash de imagenes para los personajes (humano, ogro, elfo)
	private static Map<String, LinkedList<BufferedImage[]>> personaje = new HashMap<>();

	// Entorno
	private static BufferedImage background;
	private static BufferedImage marco;
	private static BufferedImage botonMenu;
	
	private static BufferedImage estadoPersonaje;
	private static BufferedImage menu;
	private static BufferedImage chat;

	// Se cargan todos los recursos del juego una sola vez al inicio
	public static void cargar(final MenuCarga menuCarga) throws IOException {
		int elementosCargados = 0;

		ancho = ANCHO;
		alto = ALTO;
//
//		menu = ImageIO.read(new File("recursos//menu.png"));
//		chat = ImageIO.read(new File("recursos//chat.png"));

		// Inicio Entorno
//		background = CargadorImagen.cargarImagen("/background.jpg");
//		
//		marco = CargadorImagen.cargarImagen("/marco.png");
//	
//		botonMenu = CargadorImagen.cargarImagen("/botonMenu.png");
//		
	}

	/**
	 * @return the background
	 */
	public static BufferedImage getBackground() {
		return background;
	}

	/**
	 * @return the marco
	 */
	public static BufferedImage getMarco() {
		return marco;
	}

	/**
	 * @return the botonMenu
	 */
	public static BufferedImage getBotonMenu() {
		return botonMenu;
	}

	/**
	 * @return the estadoPersonaje
	 */
	public static BufferedImage getEstadoPersonaje() {
		return estadoPersonaje;
	}

	/**
	 * @return the menu
	 */
	public static BufferedImage getMenu() {
		return menu;
	}

	/**
	 * @return the chat
	 */
	public static BufferedImage getChat() {
		return chat;
	}

	/**
	 * @return the personaje
	 */
	public static Map<String, LinkedList<BufferedImage[]>> getPersonaje() {
		return personaje;
	}
}
