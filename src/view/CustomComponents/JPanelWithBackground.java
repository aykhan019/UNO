package view.CustomComponents;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JPanelWithBackground extends JPanel {

	  private Image backgroundImage;

	  public JPanelWithBackground(String fileName) throws IOException {
	    backgroundImage = ImageIO.read(new File(fileName));
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    g.drawImage(backgroundImage, 0, 0, this);
	  }
	}
