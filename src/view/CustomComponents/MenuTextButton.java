package view.CustomComponents;

import javax.swing.*;

import util.constants.FontConstants;
import util.constants.UITexts;
import util.ui.UIUtils;

import java.awt.*;
import java.awt.event.*;

/**
 * A custom button component with text overlay for use in menu interfaces. This
 * class extends JButton to provide a visually appealing button with an icon and
 * optional text.
 */
@SuppressWarnings("serial")
public class MenuTextButton extends JButton {
	/**
	 * The text displayed on the button.
	 */
	private final String buttonText;

	/**
	 * The x-coordinate for positioning the text on the button.
	 */
	private final int textX;

	/**
	 * The y-coordinate for positioning the text on the button.
	 */
	private final int textY;

	/**
	 * The font size of the text displayed on the button.
	 */
	private final int fontSize;

	/**
	 * The main font of the button text.
	 */
	private final Font textFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

	/**
	 * Constructs a new MenuTextButton with the specified icon, width, and height.
	 * 
	 * @param icon   The icon to be displayed on the button.
	 * @param width  The width of the button.
	 * @param height The height of the button.
	 */
	public MenuTextButton(ImageIcon icon, int width, int height) {
		this(icon, width, height, UITexts.STRING_EMPTY, 0, 0, 20);
	}

	/**
	 * Constructs a new MenuTextButton with the specified icon, width, height, text,
	 * text position, and font size.
	 * 
	 * @param icon       The icon to be displayed on the button.
	 * @param width      The width of the button.
	 * @param height     The height of the button.
	 * @param buttonText The text to be displayed on the button.
	 * @param textX      The x-coordinate for positioning the text on the button.
	 * @param textY      The y-coordinate for positioning the text on the button.
	 * @param fontSize   The font size of the text displayed on the button.
	 */
	public MenuTextButton(ImageIcon icon, int width, int height, String buttonText, int textX, int textY,
			int fontSize) {
		this.buttonText = buttonText;
		this.textX = textX;
		this.textY = textY;
		this.fontSize = fontSize;

		Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(scaledImage));

		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setPreferredSize(new Dimension(width, height));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * Overrides the paintComponent method to customize the appearance of the
	 * button.
	 * 
	 * @param g The Graphics object used for painting.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw text on the image if buttonText is provided
		if (buttonText != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setFont(textFont.deriveFont(Font.PLAIN, fontSize));
			g2d.setColor(Color.WHITE);
			g2d.drawString(buttonText, textX, textY);
			g2d.dispose();
		}
	}
}
