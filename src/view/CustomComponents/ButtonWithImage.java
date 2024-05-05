package view.CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ButtonWithImage extends JButton {

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

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		super.paintComponent(g);
	}

}
