package view.CustomComponents;

import javax.swing.*;

import util.constants.UIColors;
import util.constants.UIFonts;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Cursor.*;

/**
 * A custom JLabel component representing a hyperlink-like text. This class
 * extends JLabel to create clickable text with hyperlink styling.
 */
@SuppressWarnings("serial")
public class HyperlinkText extends JLabel {
	/**
	 * Creates a new HyperlinkText instance with the specified text, position, and
	 * action to perform when clicked.
	 * 
	 * @param hyperlinkText   The text to be displayed as a hyperlink.
	 * @param xPos            The x-coordinate of the hyperlink text.
	 * @param yPos            The y-coordinate of the hyperlink text.
	 * @param hyperlinkAction The action to be performed when the hyperlink is
	 *                        clicked.
	 */
	public HyperlinkText(String hyperlinkText, int xPos, int yPos, Runnable hyperlinkAction) {
		super(hyperlinkText);
		setForeground(UIColors.COLOR_OUTLINE);
		setFont(UIFonts.FONT_FORGOT_PASSWORD);
		setCursor(getPredefinedCursor(HAND_CURSOR));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				hyperlinkAction.run();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(UIColors.COLOR_OUTLINE.darker());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(UIColors.COLOR_OUTLINE);
			}
		});

		Dimension prefSize = getPreferredSize();
		setBounds(xPos, yPos, prefSize.width, prefSize.height);
	}
}
