package view.CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A button with an image icon.
 */
@SuppressWarnings("serial")
public class ButtonWithImage extends JButton {

	/**
	 * Constructs a ButtonWithImage with the specified icon, width, and height.
	 *
	 * @param icon   The ImageIcon to be displayed on the button.
	 * @param width  The width of the button.
	 * @param height The height of the button.
	 */
	public ButtonWithImage(ImageIcon icon, int width, int height) {
		super(icon);

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
}
