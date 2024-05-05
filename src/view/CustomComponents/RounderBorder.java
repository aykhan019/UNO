package view.CustomComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

class RoundedBorder implements Border {

    private int radius;
    private int borderWidth;
    private Color borderColor;

    RoundedBorder(int radius, int borderWidth, Color borderColor) {
        this.radius = radius;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    public Insets getBorderInsets(Component c) {
        int totalBorderWidth = radius + borderWidth; 
        return new Insets(totalBorderWidth, totalBorderWidth, totalBorderWidth, totalBorderWidth);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(borderColor);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
