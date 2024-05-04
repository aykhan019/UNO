package view;

import javax.swing.JFrame;
import util.constants.WindowConstants;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * The BaseFrame class represents a base frame for creating Swing-based frames.
 * It provides common functionality such as setting default close operation,
 * setting window size, centering the window on the screen, and making the frame not resizable.
 * Subclasses can extend this class to create specific frames for their application.
 */
@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {
    protected BaseFrame previousFrame;

    /**
     * Constructs a new BaseFrame with the specified title and previous frame.
     * 
     * @param title The title of the frame.
     * @param previousFrame The previous frame that this frame is associated with.
     *                      It can be null if there is no previous frame.
     */
    public BaseFrame(String title, BaseFrame previousFrame) {
        super(title); 
        
        this.previousFrame = previousFrame;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(WindowConstants.DEFAULT_WINDOW_WIDTH, WindowConstants.DEFAULT_WINDOW_HEIGHT);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    
        setResizable(false);  
    }
    
    /**
     * Constructs a new BaseFrame with the specified title.
     * 
     * @param title The title of the frame.
     */
    public BaseFrame(String title) {
        this(title, null); // Call the other constructor with null for previousFrame
    }
    
    /**
     * Abstract method to initialize the frame.
     * Subclasses must implement this method to set up the frame components.
     */
    abstract void initializeFrame();
}
