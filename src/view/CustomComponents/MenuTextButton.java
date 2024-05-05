package view.CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MenuTextButton extends JButton {
    private int buttonWidth;
    private int buttonHeight;
    private Image icon;
    private String buttonText;
    private Color borderColor;
    private int borderWidth;
    
    public MenuTextButton(int buttonWidth, int buttonHeight, Image icon, String buttonText, Color borderColor, int borderWidth, Color backgroundColor) {
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.icon = icon;
        this.buttonText = buttonText;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        setBackground(backgroundColor);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));

        // Draw border
        setBorder(new RoundedBorder(10, 5, Color.black)); //10 is the radius

        // Calculate width for part A and part B
        int partAWidth = buttonWidth / 3;
        int partBWidth = 2 * buttonWidth / 3;

        // Draw icon in part A
        if (icon != null) {
            int iconHeight = getHeight() - 4; // Adjust height to center vertically
            int iconWidth = iconHeight * icon.getWidth(null) / icon.getHeight(null); // Calculate width based on height and aspect ratio
            g2d.drawImage(icon, 2, (getHeight() - iconHeight) / 2, iconWidth, iconHeight, null);
        }

        // Draw text in part B
        if (buttonText != null) {
            g2d.setFont(getFont());
            g2d.setColor(Color.WHITE);
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(buttonText);
            int textHeight = fontMetrics.getHeight();
            g2d.drawString(buttonText, partAWidth + (partBWidth - textWidth) / 2, (getHeight() + textHeight) / 2);
        }

        g2d.dispose();
    }


    // Optional: Override getPreferredSize to set preferred size based on buttonWidth
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(buttonWidth, buttonHeight);
    }
}
