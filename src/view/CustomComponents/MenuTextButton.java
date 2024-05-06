package view.CustomComponents;

import javax.swing.*;

import util.constants.FontConstants;
import util.ui.UIUtils;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MenuTextButton extends JButton {

    private final ImageIcon icon;
    private final String buttonText;
    private final int textX;
    private final int textY;
    private final int fontSize;
    
    /**
    * The main font of the button.
    */
    private final Font textFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

    public MenuTextButton(ImageIcon icon, int width, int height) {
        this(icon, width, height, "", 0, 0, 20);
    }

    public MenuTextButton(ImageIcon icon, int width, int height, String buttonText, int textX, int textY, int fontSize) {
        this.icon = icon;
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

