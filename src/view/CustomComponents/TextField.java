package view.CustomComponents;

import javax.swing.*;

import util.constants.UIColors;
import util.constants.UIFonts;
import util.ui.UIUtils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom text field component with rounded corners and custom styling. This
 * class extends JTextField to provide enhanced visual appearance and
 * functionality.
 */
@SuppressWarnings("serial")
public class TextField extends JTextField {
	/**
	 * The shape used for drawing the border of the text field.
	 */
	private Shape shape;

	/**
	 * The color of the border.
	 */
	private Color borderColor = UIColors.COLOR_INTERACTIVE;

	/**
	 * Constructs a new TextField with default settings.
	 */
	public TextField() {
		setOpaque(false);
		setBackground(UIColors.COLOR_BACKGROUND);
		setForeground(UIColors.OFFBLACK);
		setCaretColor(Color.white);
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