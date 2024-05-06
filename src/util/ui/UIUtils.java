package util.ui;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

/**
 * Utility methods for UI-related tasks.
 */
public class UIUtils {

	/**
	 * The roundness value used for drawing rounded shapes.
	 */
	public static final int ROUNDNESS = 8;

	/**
	 * Retrieves a Graphics2D object with antialiasing enabled.
	 *
	 * @param g The Graphics object.
	 * @return The Graphics2D object with antialiasing enabled.
	 */
	@SuppressWarnings("serial")
	public static Graphics2D get2dGraphics(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {
			{
				put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			}
		});
		return g2;
	}

	/**
	 * Loads a custom font from the specified path.
	 *
	 * @param path The path to the font file.
	 * @return The custom font loaded from the specified path.
	 */
	public static Font loadCustomFont(String path) {
		try {
			File fontFile = new File(path);
			return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 14);
		} catch (Exception e) {
			// TODO: Log the error
			e.printStackTrace();
			return null;
		}
	}
}
