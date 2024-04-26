package view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import util.constants.WindowConstants;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class BaseFrame extends JFrame {

    // Constructor fo r the BaseFrame class with a default value for backgroundImagePath
    public BaseFrame(String title) {
        // Call the overloaded constructor with the default background image path
        this(title, WindowConstants.DEFAULT_BACKGROUND_IMAGE_PATH);
    }

    // Overloaded constructor allowing custom backgroundImagePath
    public BaseFrame(String title, String backgroundImagePath) {
        super(title); // Call the constructor of the superclass (JFrame) with the specified title

        // Set default close operation to exit the application when the frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame to the default window width and height specified in the WindowConstants class
        setSize(WindowConstants.DEFAULT_WINDOW_WIDTH, WindowConstants.DEFAULT_WINDOW_HEIGHT);

        // Set the background image
        try {
            Image backgroundImage = ImageIO.read(new File(backgroundImagePath));
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            setContentPane(backgroundLabel);
        } catch (IOException e) {
            // TODO: Log error if image loading fails
            System.err.println("Error loading background image: " + e.getMessage());
        }
        
        // Center the window on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        // Make the frame visible
        setVisible(true);
    }
}
