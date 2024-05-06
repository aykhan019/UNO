package view.CustomComponents;

import javax.swing.*;

import util.constants.UIColors;
import util.constants.UIFonts;
import util.ui.UIUtils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom text field component specialized for password input. This class
 * extends JPasswordField to provide a visually appealing and functional
 * password input field with rounded corners and custom styling.
 */
@SuppressWarnings("serial")
public class TextFieldPassword extends JPasswordField {
	/**
	 * The shape used for drawing the border of the text field.
	 */
	private Shape shape;

	/**
	 * The color of the border.
	 */
	private Color borderColor = UIColors.COLOR_OUTLINE;

	/**
	 * Constructs a new TextFieldPassword with default settings.
	 */
	public TextFieldPassword() {
		setOpaque(false);
		setBackground(UIColors.COLOR_BACKGROUND);
		setForeground(UIColors.OFFBLACK);
		setCaretColor(UIColors.OFFBLACK);
		setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		setMargin(new Insets(2, 10, 2, 2));
		setHorizontalAlignment(SwingConstants.LEFT);
		setFont(UIFonts.FONT_GENERAL_UI);
	}

	/**
	 * Overrides the paintComponent method to customize the appearance of the text
	 * field.
	 * 
	 * @param g The Graphics object used for painting.
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = UIUtils.get2dGraphics(g);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
		super.paintComponent(g2);
	}

	/**
	 * Overrides the paintBorder method to customize the border of the text field.
	 * 
	 * @param g The Graphics object used for painting.
	 */
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = UIUtils.get2dGraphics(g);
		g2.setColor(borderColor);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
	}

	/**
	 * Checks if the specified coordinates are contained within the bounds of the
	 * text field.
	 * 
	 * @param x The x-coordinate of the point to check.
	 * @param y The y-coordinate of the point to check.
	 * @return True if the point is contained within the bounds of the text field,
	 *         otherwise false.
	 */
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, UIUtils.ROUNDNESS,
					UIUtils.ROUNDNESS);
		}
		return shape.contains(x, y);
	}

	/**
	 * Sets the color of the border.
	 * 
	 * @param color The color to set for the border.
	 */
	public void setBorderColor(Color color) {
		borderColor = color;
		repaint();
	}
}