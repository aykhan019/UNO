package view;

import javax.swing.*;

import util.constants.FontConstants;
import util.constants.ImagePath;
import util.constants.UITexts;
import util.constants.WindowConstants;
import util.ui.UIUtils;
import view.CustomComponents.ButtonWithImage;
import view.CustomComponents.GradientPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The NumberOfPlayersView class represents a frame for selecting the number of
 * players in a game.
 */
@SuppressWarnings("serial")
public class NumberOfPlayersView extends BaseFrame {

	/** Label for displaying the number of players. */
	private JLabel numberOfPlayersLabel;

	/** The number of players selected. */
	private int numberOfPlayers = 2;

	/**
	 * The main font of the leaderboard page.
	 */
	private final Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

	/**
	 * Constructs a new NumberOfPlayersView.
	 */

	public NumberOfPlayersView() {
		super(WindowConstants.NUMBER_OF_PLAYERS_WINDOW_TITLE);
		initializeFrame();
	}

	/**
	 * Initializes the frame components.
	 */
	@Override
	void initializeFrame() {
		JPanel mainPanel = new GradientPanel();

		mainPanel.setLayout(new GridLayout(5, 1));

		JLabel playAgainstBotsLabel = new JLabel(UITexts.PLAY_AGAINST_BOTS.toUpperCase());
		playAgainstBotsLabel.setFont(customFont.deriveFont(Font.PLAIN, 50));
		playAgainstBotsLabel.setForeground(Color.cyan);
		playAgainstBotsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playAgainstBotsLabel.setOpaque(false);
		mainPanel.add(playAgainstBotsLabel);

		JLabel numberOfBotsTextLabel = new JLabel(UITexts.NUMBER_OF_PLAYERS);
		numberOfBotsTextLabel.setFont(customFont.deriveFont(Font.PLAIN, 30));
		numberOfBotsTextLabel.setForeground(Color.white);
		numberOfBotsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfBotsTextLabel.setOpaque(false);
		mainPanel.add(numberOfBotsTextLabel);

		var thirdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 20));
		var buttonSize = 70;

		ImageIcon decreaseIcon = new ImageIcon(new ImageIcon(ImagePath.NUMBER_OF_PLAYERS_DECREMENT).getImage()
				.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH));
		JButton decreaseButton = new ButtonWithImage(decreaseIcon, buttonSize, buttonSize);
		decreaseButton.setOpaque(false);
		decreaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (numberOfPlayers > 2) {
					numberOfPlayers--;
					numberOfPlayersLabel.setText(String.valueOf(numberOfPlayers));
				}
			}
		});
		thirdPanel.add(decreaseButton);

		numberOfPlayersLabel = new JLabel(String.valueOf(numberOfPlayers));
		numberOfPlayersLabel.setFont(customFont.deriveFont(Font.PLAIN, 23));
		numberOfPlayersLabel.setForeground(Color.white);
		numberOfPlayersLabel.setOpaque(false);
		numberOfPlayersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		thirdPanel.add(numberOfPlayersLabel);

		ImageIcon increaseIcon = new ImageIcon(new ImageIcon(ImagePath.NUMBER_OF_PLAYERS_INCREMENT).getImage()
				.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH));
		JButton increaseButton = new ButtonWithImage(increaseIcon, buttonSize, buttonSize);
		increaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (numberOfPlayers < 10) {
					numberOfPlayers++;
					numberOfPlayersLabel.setText(String.valueOf(numberOfPlayers));
				}
			}
		});
		thirdPanel.add(increaseButton);
		thirdPanel.setOpaque(false);

		mainPanel.add(thirdPanel);

		var buttonWidth = 240;
		var buttonHeight = 90;
		ImageIcon logoutButtonIcon = new ImageIcon(new ImageIcon(ImagePath.START_BUTTON).getImage()
				.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));
		JButton startGameButton = new ButtonWithImage(logoutButtonIcon, buttonWidth, buttonHeight);
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				new GameTable(numberOfPlayers);
			}
		});
		startGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(startGameButton);

		getContentPane().add(mainPanel);
		setVisible(true);
	}
}
