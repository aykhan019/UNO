package view;

import javax.swing.JFrame;
import util.constants.WindowConstants;

import java.awt.Dimension;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {
    // Overloaded constructor allowing custom backgroundImagePath
    public BaseFrame(String title) {
        super(title); // Call the constructor of the superclass (JFrame) with the specified title
        
        // Set default close operation to exit the application when the frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame to the default window width and height specified in the WindowConstants class
        setSize(WindowConstants.DEFAULT_WINDOW_WIDTH, WindowConstants.DEFAULT_WINDOW_HEIGHT);
        
        // Center the window on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    
        // Make the frame not resizable
        setResizable(false);  
    }
    
    abstract void initializeFrame();
}
