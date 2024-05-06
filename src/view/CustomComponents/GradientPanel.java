package view.CustomComponents;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel with a gradient background. This class extends JPanel to create a
 * panel with a gradient background, allowing for smooth transitions between two
 * specified colors.
 */
@SuppressWarnings("serial")
public class GradientPanel extends JPanel {
	/**
	 * The starting color of the gradient background.
	 */
	private Color color1;

	/**
	 * The ending color of the gradient background.
	 */
	private Color color2;

	/**
	 * Constructs a GradientPanel with the specified gradient colors.
	 * 
	 * @param color1 The starting color of the gradient.
	 * @param color2 The ending color of the gradient.
	 */
	public GradientPanel(Color color1, Color color2) {
		this.color1 = color1;
		this.color2 = color2;
	}

	/**
	 * Constructs a GradientPanel with default gradient colors. Uses default colors
	 * if no colors are provided.
	 */
	public GradientPanel() {
		this(new Color(20, 136, 204), new Color(43, 50, 178));
	}

	/**
	 * Overrides the paintComponent method to paint the gradient background. This
	 * method is called by Swing to draw the component.
	 * 
	 * @param g The Graphics context for painting.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

		g2d.setPaint(gradient);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.dispose();
	}
}
