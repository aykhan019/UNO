package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import util.constants.ImagePath;
import util.constants.WindowConstants;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * The BaseFrame class represents a base frame for creating Swing-based frames.
 * It provides common functionality such as setting default close operation,
 * setting window size, centering the window on the screen, and making the frame
 * not resizable. Subclasses can extend this class to create specific frames for
 * their application.
 */
@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {
	protected BaseFrame previousFrame;

	/**
	 * Constructs a new BaseFrame with the specified title, previous frame and other
	 * details.
	 * 
	 * @param title         The title of the frame.
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

		changeWindowIcon();

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
	 * Changes the window icon to a custom image. The custom image is loaded from
	 * the specified path and resized to 128x128 pixels using Image.SCALE_SMOOTH
	 * algorithm before setting it as the window icon.
	 */
	public void changeWindowIcon() {
		// Load the icon image
		ImageIcon icon = new ImageIcon(ImagePath.WINDOW_ICON);

		// Resize the icon image
		Image scaledIcon = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);

		// Set the resized icon image as the window icon
		setIconImage(scaledIcon);
	}

	/**
	 * Abstract method to initialize the frame. Subclasses must implement this
	 * method to set up the frame components.
	 */
	abstract void initializeFrame();
}
