package view;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import util.constants.ImagePath;
import util.constants.WindowConstants;
import view.CustomComponents.MenuTextButton;

import java.awt.*;

public class MainMenu extends BaseFrame {

	public MainMenu() {
		super(WindowConstants.MAIN_MENU_WINDOW_TITLE_PREFIX);
		initializeFrame();
	}

	@Override
	void initializeFrame() {
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new BorderLayout());

		// Create three sub-panels for the top panel
		JPanel topPanelLeft = new JPanel();
		JPanel topPanelMiddle = new JPanel();
		JPanel topPanelRight = new JPanel();

		// Set background colors for clarity (you can remove these if not needed)
		topPanelLeft.setBackground(Color.RED);
		topPanelMiddle.setBackground(Color.yellow);
		topPanelRight.setBackground(Color.BLUE);

		// Calculate the sizes for each sub-panel
		int topPanelWidth = getWidth();
		int topPanelHeight = 100;
		int leftPanelWidth = topPanelWidth / 4; // 1/4 of the width
		int middlePanelWidth = topPanelWidth / 2; // 1/2 of the width
		int rightPanelWidth = topPanelWidth / 4; // 1/4 of the width

		// Set preferred sizes for each sub-panel
		topPanelLeft.setPreferredSize(new Dimension(leftPanelWidth, topPanelHeight));
		topPanelMiddle.setPreferredSize(new Dimension(middlePanelWidth, topPanelHeight));
		topPanelRight.setPreferredSize(new Dimension(rightPanelWidth, topPanelHeight));

		// Add sub-panels to the top panel with BorderLayout.CENTER for middle panel
		topPanel.add(topPanelLeft, BorderLayout.WEST);
		topPanel.add(topPanelMiddle, BorderLayout.CENTER);
		topPanel.add(topPanelRight, BorderLayout.EAST);

		// Adding the top panel to the main frame
		add(topPanel, BorderLayout.NORTH);

		// Other panels remain the same
		JPanel middlePanel = new JPanel();
		middlePanel.setBackground(Color.GREEN);
		add(middlePanel, BorderLayout.CENTER);

		// Creating a bottom panel with horizontal BoxLayout
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		// Creating two sub-panels for the bottom panel
		JPanel bottomPanelLeft = new JPanel();
		JPanel bottomPanelRight = new JPanel();

		// Set preferred sizes for the sub-panels based on the width of the bottom panel
		int bottomPanelWidth = getWidth();
		bottomPanelLeft.setPreferredSize(new Dimension(bottomPanelWidth / 2, 100));
		bottomPanelRight.setPreferredSize(new Dimension(bottomPanelWidth / 2, 100));

		// Set background colors for clarity (you can remove these if not needed)
		bottomPanelLeft.setBackground(Color.YELLOW);
		bottomPanelRight.setBackground(Color.pink);

		// Add the sub-panels to the bottom panel
		bottomPanel.add(bottomPanelLeft);
		bottomPanel.add(bottomPanelRight);

		// Create an icon for the button (you can load an image or use a placeholder)
		ImageIcon icon1 = new ImageIcon(ImagePath.SHARE);
		ImageIcon icon2 = new ImageIcon(ImagePath.RATE);
		ImageIcon icon3 = new ImageIcon(ImagePath.REMOVE);

		var buttonWidth = 170;
		var buttonHeight = 60;
		var borderWidth = 5;

		// Create MenuTextButton instances
		MenuTextButton button1 = new MenuTextButton(buttonWidth, buttonHeight, icon1.getImage(), "Button 1",
				Color.WHITE, borderWidth, Color.red);
		MenuTextButton button2 = new MenuTextButton(buttonWidth, buttonHeight, icon2.getImage(), "Button 2",
				Color.BLACK, borderWidth, Color.cyan);
		MenuTextButton button3 = new MenuTextButton(buttonWidth, buttonHeight, icon3.getImage(), "Button 2",
				Color.BLACK, borderWidth, Color.magenta);

		// Add the buttons to the bottomPanelLeft
		bottomPanelLeft.add(button1);
		 bottomPanelLeft.add(button2);
		 bottomPanelLeft.add(button3);

		// Adding the bottom panel to the main frame
		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

}
