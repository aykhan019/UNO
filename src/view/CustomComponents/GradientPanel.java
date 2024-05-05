package view.CustomComponents;

import javax.swing.*;
import java.awt.*;
	
@SuppressWarnings("serial")
public class GradientPanel extends JPanel {
    private Color color1;
    private Color color2;

    public GradientPanel(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }
    
    public GradientPanel() {
        this(new Color(20, 136, 204), new Color(43, 50, 178));
    }

    // paintComponent method remains unchanged
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
