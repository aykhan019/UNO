package view;

import javax.swing.Box;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.UserStatisticRepository;
import util.constants.FileConstants;
import util.constants.FontConstants;
import util.constants.ImagePath;
import util.constants.UIColors;
import util.constants.UITexts;
import util.constants.WindowConstants;
import util.helpers.Logger;
import util.session.CurrentUserManager;
import util.ui.UIUtils;
import util.ui.toaster.Toaster;
import view.CustomComponents.ButtonWithImage;
import view.CustomComponents.GradientPanel;
import view.CustomComponents.MenuTextButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents the main menu interface of the application, providing access
 * to various features and functionalities through a user-friendly interface.
 */
@SuppressWarnings("serial")
public class MainMenu extends BaseFrame {
	/**
	 * The toaster object for displaying notifications.
	 */
	private final Toaster toaster;

	/**
	 * The top panel of the main menu.
	 */
	private JPanel topPanel = new GradientPanel();

	/**
	 * The custom font used in the main menu.
	 */
	private final Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

	/**
	 * Constructs a new MainMenu object.
	 */
	public MainMenu() {
		super(WindowConstants.MAIN_MENU_WINDOW_TITLE_PREFIX);
		toaster = new Toaster(topPanel);
		initializeFrame();
	}

	/**
	 * Initializes the graphical components of the main menu.
	 */
	@Override
	void initializeFrame() {
		setLayout(new BorderLayout());
		topPanel.setLayout(new BorderLayout());

		// TOP PANEL
		JPanel topPanelLeft = new JPanel();
		JPanel topPanelMiddle = new JPanel();
		JPanel topPanelRight = new JPanel();

		topPanelLeft.setOpaque(false);
		topPanelMiddle.setOpaque(false);
		topPanelRight.setOpaque(false);

		int topPanelWidth = getWidth();
		int topPanelHeight = 100;
		int leftPanelWidth = topPanelWidth / 4 + 70;
		int middlePanelWidth = topPanelWidth / 2;
		int rightPanelWidth = topPanelWidth / 4;

		topPanelLeft.setPreferredSize(new Dimension(leftPanelWidth, topPanelHeight));
		topPanelMiddle.setPreferredSize(new Dimension(middlePanelWidth, topPanelHeight));
		topPanelRight.setPreferredSize(new Dimension(rightPanelWidth, topPanelHeight));

		topPanel.add(topPanelLeft, BorderLayout.WEST);
		topPanel.add(topPanelMiddle, BorderLayout.CENTER);
		topPanel.add(topPanelRight, BorderLayout.EAST);

		// TOP PANEL LEFT
		topPanelLeft.setLayout(new BoxLayout(topPanelLeft, BoxLayout.X_AXIS));

		JPanel imagePanel = new JPanel();
		imagePanel.setOpaque(false);

		ImageIcon imageIcon = new ImageIcon(ImagePath.AVATAR);
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT)));
		imagePanel.add(imageLabel);

		JPanel textFieldsPanel = new JPanel();
		textFieldsPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		textFieldsPanel.setLayout(new BoxLayout(textFieldsPanel, BoxLayout.Y_AXIS));
		textFieldsPanel.setOpaque(false);

		var currentUser = CurrentUserManager.getInstance().getCurrentUser();
		JLabel upperTextField = new JLabel(UITexts.CURRENT_USER + currentUser.getUsername());
		upperTextField.setOpaque(false);
		upperTextField.setFont(customFont.deriveFont(Font.PLAIN, 17));
		upperTextField.setForeground(UIColors.OFFWHITE);
		upperTextField.setPreferredSize(new Dimension(300, 50));
		textFieldsPanel.add(upperTextField);

		try {
			var userStatistic = UserStatisticRepository.getUserStatisticById(currentUser.getId());
			JLabel lowerTextField = new JLabel(
					UITexts.CURRENT_USER_SCORE.toUpperCase() + userStatistic.getTotalScore());
			lowerTextField.setOpaque(false);
			lowerTextField.setForeground(UIColors.OFFWHITE);
			lowerTextField.setBorder(new EmptyBorder(7, 0, 0, 0));
			lowerTextField.setFont(customFont.deriveFont(Font.PLAIN, 10));
			lowerTextField.setPreferredSize(new Dimension(200, 100));
			textFieldsPanel.add(lowerTextField);

		} catch (IOException e) {
			Logger.log(e.getMessage(), FileConstants.ERROR_LOGS_FILE_PATH);
		}

		topPanelLeft.add(imagePanel);
		topPanelLeft.add(textFieldsPanel);

		add(topPanel, BorderLayout.NORTH);

		// TOP PANEL RIGHT
		topPanelRight.setLayout(new BoxLayout(topPanelRight, BoxLayout.X_AXIS));
		topPanelRight.setBorder(new EmptyBorder(0, 75, 0, 25));

		ImageIcon logoutButtonIcon = new ImageIcon(
				new ImageIcon(ImagePath.MENU_LOGOUT).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));

		ImageIcon infoButtonIcon = new ImageIcon(
				new ImageIcon(ImagePath.MENU_INFO).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));

		ImageIcon menuListButtonIcon = new ImageIcon(
				new ImageIcon(ImagePath.MENU_LIST).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));

		ButtonWithImage logoutButton = new ButtonWithImage(logoutButtonIcon, 50, 50);
		ButtonWithImage infoButton = new ButtonWithImage(infoButtonIcon, 50, 50);
		ButtonWithImage menuListButton = new ButtonWithImage(menuListButtonIcon, 50, 50);

		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		infoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showInformationDialog();
			}
		});

		menuListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openMenuList();
			}
		});

		topPanelRight.add(menuListButton);
		topPanelRight.add(infoButton);
		topPanelRight.add(logoutButton);

		// MIDDLE PANEL
		JPanel middlePanel = new GradientPanel();
		middlePanel.setLayout(new GridLayout(1, 2));

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setBorder(new EmptyBorder(40, 230, 0, 0));

		ImageIcon gameImageIcon = new ImageIcon(ImagePath.MENU_GAME_ICON);
		Image scaledImage = gameImageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel gameImageLabel = new JLabel(scaledIcon);
		leftPanel.add(gameImageLabel);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(new EmptyBorder(100, 100, 0, 0));
		rightPanel.setOpaque(false);

		var playButtonWidth = 280;
		var playButtonHeight = 100;

		ImageIcon playOfflineIcon = new ImageIcon(new ImageIcon(ImagePath.MENU_PLAY_OFFLINE_BUTTON_IMAGE).getImage()
				.getScaledInstance(playButtonWidth, playButtonHeight, Image.SCALE_SMOOTH));
		ImageIcon playOnlineIcon = new ImageIcon(new ImageIcon(ImagePath.MENU_PLAY_ONLINE_BUTTON_IMAGE).getImage()
				.getScaledInstance(playButtonWidth, playButtonHeight, Image.SCALE_SMOOTH));

		JButton playOfflineButton = new ButtonWithImage(playOfflineIcon, playButtonWidth, playButtonHeight);
		JButton playOnlineButton = new ButtonWithImage(playOnlineIcon, playButtonWidth, playButtonHeight);

		playOfflineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new NumberOfPlayersView();
			}
		});

		playOnlineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toaster.warn(UITexts.I_DO_NOT_WORK);
			}
		});

		rightPanel.add(playOfflineButton);
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(playOnlineButton);

		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		add(middlePanel, BorderLayout.CENTER);

		// BOTTOM PANEL
		JPanel bottomPanel = new GradientPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		JPanel bottomPanelLeft = new JPanel();
		JPanel bottomPanelRight = new JPanel();
		bottomPanelLeft.setLayout(new BoxLayout(bottomPanelLeft, BoxLayout.X_AXIS));
		bottomPanelLeft.setBorder(new EmptyBorder(0, 15, 0, 0));

		int bottomPanelWidth = getWidth();
		bottomPanelLeft.setPreferredSize(new Dimension(bottomPanelWidth / 2 + 100, 100));
		bottomPanelRight.setPreferredSize(new Dimension(bottomPanelWidth / 2 - 100, 100));

		bottomPanelLeft.setOpaque(false);
		bottomPanelRight.setOpaque(false);

		bottomPanel.add(bottomPanelLeft);
		bottomPanel.add(bottomPanelRight);

		// BOTTOM PANEL LEFT
		ImageIcon icon1 = new ImageIcon(ImagePath.SETTINGS);
		ImageIcon icon2 = new ImageIcon(ImagePath.SHARE);
		ImageIcon icon3 = new ImageIcon(ImagePath.LEADERBOARD);

		var buttonWidth = 210;
		var buttonHeight = 70;

		MenuTextButton btn1 = new MenuTextButton(icon1, buttonWidth, buttonHeight,
				UITexts.MENU_BUTTON_SETTINGS.toUpperCase(), 63, 45, 19);
		MenuTextButton btn2 = new MenuTextButton(icon2, buttonWidth, buttonHeight,
				UITexts.MENU_BUTTON_SHARE.toUpperCase(), 80, 45, 19);
		MenuTextButton btn3 = new MenuTextButton(icon3, buttonWidth + 40, buttonHeight,
				UITexts.MENU_BUTTON_LEADERBOARD.toUpperCase(), 78, 45, 15);

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openSettingsWindow();
			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shareContent();
			}
		});

		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLeaderboard();
			}
		});

		bottomPanelLeft.add(Box.createHorizontalGlue());
		bottomPanelLeft.add(btn1);
		bottomPanelLeft.add(Box.createHorizontalStrut(20));
		bottomPanelLeft.add(btn2);
		bottomPanelLeft.add(Box.createHorizontalStrut(20));
		bottomPanelLeft.add(btn3);
		bottomPanelLeft.add(Box.createHorizontalGlue());

		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	/**
	 * Logs out the current user and navigates to the login page.
	 */
	private void logout() {
		CurrentUserManager.getInstance().setCurrentUser(null);
		dispose();
		new LoginPageView();
	}

	/**
	 * Opens the menu list.
	 */
	private void openMenuList() {
		toaster.warn(UITexts.I_DO_NOT_WORK);
	}

	/**
	 * Displays information dialog.
	 */
	private void showInformationDialog() {
		toaster.warn(UITexts.I_DO_NOT_WORK);
	}

	/**
	 * Opens the settings window.
	 */
	private void openSettingsWindow() {
		toaster.warn(UITexts.I_DO_NOT_WORK);
	}

	/**
	 * Shares content.
	 */
	private void shareContent() {
		toaster.warn(UITexts.I_DO_NOT_WORK);
	}

	/**
	 * Shows the leaderboard.
	 */
	private void showLeaderboard() {
		dispose();
		new LeaderboardView();
	}
}
