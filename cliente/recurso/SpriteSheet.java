package recurso;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private final BufferedImage sprite;

	public SpriteSheet(final BufferedImage sprite) {
		this.sprite = sprite;
	}

	public BufferedImage getTile(final int x, final int y, final int ancho, final int alto) {
		return sprite.getSubimage(x, y, ancho, alto);
	}
}