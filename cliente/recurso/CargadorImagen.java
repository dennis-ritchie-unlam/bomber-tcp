package recurso;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class CargadorImagen {

	public static BufferedImage cargarImagen(final String path) {
		try {
			return ImageIO.read(CargadorImagen.class.getResource(path));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el archivo " + path);
		}

		return null;
	}
}