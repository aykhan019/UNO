package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data.UserStatisticRepository;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.GameSession;
import model.cards.ActionCard;
import model.cards.Card;
import model.cards.WildCard;
import model.enums.ActionType;
import model.enums.WildType;
import model.player.Bot;
import model.player.Player;
import model.user.User;
import util.constants.FileConstants;
import util.constants.FontConstants;
import util.constants.ImagePath;
import util.constants.UnoStatusMessages;
import util.constants.WarningConstants;
import util.constants.WindowConstants;
import util.helpers.Logger;
import util.session.CurrentUserManager;
import util.ui.GameTableLayoutHelper;
import util.ui.UIUtils;
import util.ui.toaster.Toaster;
import view.CustomComponents.ButtonWithImage;
import view.CustomComponents.GradientPanel;
import view.Popups.ColorSelectionPopup;

/**
 * Represents the main game table where the Uno game is played.
 */
@SuppressWarnings("serial")
public class GameTable extends BaseFrame {

	/**
	 * Toaster object for displaying notifications.
	 */
	private Toaster toaster;

	/**
	 * GameSession object representing the current game session.
	 */
	private GameSession gameSession;

	/**
	 * 2D array of JPanels representing the cells of the game table.
	 */
	private JPanel[][] cells;

	/**
	 * 2D array representing the main cells of the game table.
	 */
	private int[][] mainCells;

	/**
	 * Number of players in the game.
	 */
	private int numberOfPlayers;

	/**
	 * JLabel representing the discard pile in the game table.
	 */
	private JLabel discardPileLabel;

	/**
	 * JButton representing the draw pile in the game table.
	 */
	private JButton drawPileButton;

	/**
	 * JLabel representing the card count in the draw pile.
	 */
	private JLabel cardCountInDrawPile;

	/**
	 * Font object for custom fonts in the game table.
	 */
	private final Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

	/**
	 * 
	 * Boolean indicating whether the draw pile button has been clicked.
	 */
	private boolean drawPileButtonClicked = false;

	/**
	 * JTextArea representing the area for displaying game status.
	 */
	private JTextArea gameStatusArea;

	/**
	 * Delay time for bot actions in milliseconds.
	 */
	private int botDelay = 3000;

	/**
	 * Constructs a new GameTable object with the specified number of players and
	 * game session name.
	 * 
	 * @param numberOfPlayers The number of players in the game.
	 * @param gameSessionName The name of the game session.
	 */
	public GameTable(int numberOfPlayers, String gameSessionName) {
		super(WindowConstants.GAME_TABLE_WINDOW + " : " + gameSessionName);
		this.numberOfPlayers = numberOfPlayers;
		gameSession = new GameSession(numberOfPlayers, gameSessionName);
		gameSession.initializeGameSession();
		gameSession.setSessionName(gameSessionName);
		initializeFrame();
		mainCells = GameTableLayoutHelper.getPlayerCells(numberOfPlayers);
		addBotPlayerElements();
		paintUserCell();
		addCenterElements();
		paintUserCell();
	}

	/**
	 * Adds center elements to the game table interface.
	 */
	void addCenterElements() {
		var centerPanel = getCenterPanel();
		centerPanel.setLayout(null);

		gameStatusArea = new JTextArea();
		gameStatusArea.setEditable(false);
		gameStatusArea.setLineWrap(true);
		gameStatusArea.setWrapStyleWord(true);
		gameStatusArea.setOpaque(false);
		gameStatusArea.setForeground(Color.white);
		gameStatusArea.setBackground(new Color(0, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane(gameStatusArea);
		scrollPane.setBounds(10, 70, 295, 255);
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

		gameStatusArea.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> {
					JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
					verticalScrollBar.setValue(verticalScrollBar.getMaximum());
				});
			}

			public void removeUpdate(DocumentEvent e) {
			}

			public void changedUpdate(DocumentEvent e) {
			}
		});

		centerPanel.add(scrollPane);

		JSeparator verticalLine = new JSeparator(SwingConstants.VERTICAL);
		verticalLine.setBackground(Color.black);
		verticalLine.setBounds(375, 0, 2, centerPanel.getHeight());
		centerPanel.add(verticalLine);

		drawPileButton = new JButton();
		drawPileButton.setBounds(425, 90, 130, 150);
		drawPileButton.setPreferredSize(new Dimension(130, 150));
		drawPileButton.setPreferredSize(new Dimension(130, 150));

		drawPileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!drawPileButtonClicked) {
					var drawnCard = gameSession.drawCard();
					gameSession.getPlayers().get(0).addCard(drawnCard);
					paintUserCell();

					if (gameSession.getDrawPileCardCount() == 0) {
						gameSession.reshuffleDiscardPile();
					}

					updateCardCountInDrawPile(gameSession.getDrawPileCardCount());
					updateDrawPileImage();
				}
			}
		});
		centerPanel.add(drawPileButton);
		centerPanel.setComponentZOrder(drawPileButton, 0);

		discardPileLabel = new JLabel();

		discardPileLabel.setBounds(565, 90, 103, 150);
		discardPileLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		centerPanel.add(discardPileLabel);

		ImageIcon icon = new ImageIcon(ImagePath.BACK_ICON);
		Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ButtonWithImage leaveGameBtn = new ButtonWithImage(new ImageIcon(image), 50, 50);
		leaveGameBtn.addActionListener(e -> leaveGame());
		leaveGameBtn.setSize(50, 50);
		leaveGameBtn.setBounds(5, 5, 50, 50);
		centerPanel.add(leaveGameBtn);

		cardCountInDrawPile = new JLabel();
		cardCountInDrawPile.setFont(customFont.deriveFont(Font.PLAIN, 22));
		cardCountInDrawPile.setForeground(Color.white);
		cardCountInDrawPile.setBounds(465, 245, 50, 50);
		centerPanel.add(cardCountInDrawPile);

		updateCardCountInDrawPile(gameSession.getDrawPileCardCount());
		updateDrawPileImage();

		addStatusMessage(UnoStatusMessages.getGameStartMessage(gameSession.getSessionName()));
		addStatusMessage(UnoStatusMessages.getPlayerTurnMessage(gameSession.getCurrentPlayer()));
		setCellBorders();
	}

	/**
	 * Handles the selection of a card by a player.
	 * 
	 * @param btn  The button representing the selected card.
	 * @param card The card that was selected.
	 */
	void handleCardSelection(JButton btn, Card card) {
		if (gameSession.getCurrentPlayerIndex() == 0) {
			gameSession.setCurrentPlayerIndex(-1);
			var played = false;

			gameSession.setCurrentPlayerIndex(0);
			played = gameSession.playCard(card);
			gameSession.setCurrentPlayerIndex(-1);
			if (played) {
				gameSession.setCurrentPlayerIndex(1);
				Container parent = btn.getParent();
				if (parent instanceof JPanel) {
					JPanel cardPanel = (JPanel) parent;
					cardPanel.remove(btn);
					cardPanel.revalidate();
					cardPanel.repaint();
				}
				updateDiscardPileImage(card.getImagePath());
				gameSession.setCurrentPlayerIndex(0);
				if (card instanceof WildCard) {
					ColorSelectionPopup colorSelectionPopup = new ColorSelectionPopup(this);
					colorSelectionPopup.setVisible(true);
					model.enums.Color selectedColor = colorSelectionPopup.getSelectedColor();
					((WildCard) card).setColor(selectedColor);
					gameSession.setColorToPlay(selectedColor);
					gameSession.setCurrentPlayerIndex(0);
					addStatusMessage(UnoStatusMessages.getWildCardPlayedMessage(gameSession.getCurrentPlayer(),
							card.getName(), selectedColor));
					handleWildCard(gameSession.getCurrentPlayer(), (WildCard) card);
					if (((WildCard) card).getWildType() == WildType.WILD_DRAW_4) {
						gameSession.setCurrentPlayerIndex(0);
						skipNextPlayer();
					} else {
						gameSession.setCurrentPlayerIndex(0);
					}
				} else if (card instanceof ActionCard) {
					handleActionCard(gameSession.getCurrentPlayer(), (ActionCard) card);
				} else {
					addStatusMessage(
							UnoStatusMessages.getPlayerPlayCardMessage(gameSession.getCurrentPlayer(), card.getName()));
				}
				drawPileButtonClicked = true;
				if (gameSession.getPlayers().get(0).getCardCount() == 1) {
					addStatusMessage(UnoStatusMessages.getPlayerCalledUnoMessage(gameSession.getCurrentPlayer()));
				}

				if (gameSession.getPlayers().get(0).hasWon()) {
					addStatusMessage(UnoStatusMessages.getPlayerWinMessage(gameSession.getPlayers().get(0)));
					var totalScore = getTotalScoreOfLosers(0);

					var currentUser = CurrentUserManager.getInstance().getCurrentUser();
					try {
						var currentUserStatistic = UserStatisticRepository.getUserStatisticById(currentUser.getId());
						var statistics = UserStatisticRepository.getUserStatistics();
						for (var statistic : statistics) {
							if (statistic.getId().equals(currentUserStatistic.getId())) {
								statistic.setTotalScore(currentUserStatistic.getTotalScore() + totalScore);
								statistic.setNumberOfWins(currentUserStatistic.getNumberOfWins() + 1);
								break;
							}
						}
						UserStatisticRepository.updateUserStatistics(statistics);
					} catch (IOException e) {
						Logger.log(e.getMessage(), FileConstants.ERROR_LOGS_FILE_PATH);
					}

					dispose();

					new YouWonWindow();
				} else {
					invokeBotTurn();
				}
			} else {
				gameSession.setCurrentPlayerIndex(0);
				toaster.warn(WarningConstants.YOU_CANNOT_PLAY_THIS_CARD);
			}
		}
	}

	/**
	 * Initiates a bot player's turn.
	 */
	void invokeBotTurn() {
		Player currentPlayer = gameSession.nextPlayer();
		setCellBorders();
		addStatusMessage(UnoStatusMessages.getPlayerTurnMessage(gameSession.getCurrentPlayer()));
		Timer initialTimer = new Timer(botDelay, e -> {
			if (currentPlayer instanceof Bot bot) {
				var obj = gameSession.playBotTurn(bot);
				var playedCardByBot = (Card) obj[0];
				gameSession.playCard(playedCardByBot);
				var drewCard = (boolean) obj[1];
				var playerIndex = gameSession.getCurrentPlayerIndex();

				if (drewCard) {
					var drawCount = (int) obj[2];
					addStatusMessage(UnoStatusMessages.getPlayerDrawCardMessage(currentPlayer, drawCount));
				}
				if (playedCardByBot instanceof WildCard w) {
					handleWildCard(bot, w);
				} else if (playedCardByBot instanceof ActionCard a) {
					handleActionCard(bot, a);
				} else {
					addStatusMessage(
							UnoStatusMessages.getPlayerPlayCardMessage(currentPlayer, playedCardByBot.getName()));
				}
				updateDiscardPileImage(playedCardByBot.getImagePath());
				paintBotCell(mainCells[playerIndex], bot, bot.getUser());

				if (currentPlayer.getCardCount() == 1) {
					addStatusMessage(UnoStatusMessages.getPlayerCalledUnoMessage(currentPlayer));
				}

				if (currentPlayer.hasWon()) {
					addStatusMessage(UnoStatusMessages.getPlayerWinMessage(currentPlayer));

					dispose();

					new YouLostWindow(UnoStatusMessages.getPlayerRoundWinMessage(currentPlayer));
				} else {
					invokeBotTurn();
				}
			} else {
				drawPileButtonClicked = false;
			}
			setCellBorders();
			updateCardCountInDrawPile(gameSession.getDrawPileCardCount());
		});
		initialTimer.setRepeats(false);
		initialTimer.start();
	}

	/**
	 * Calculates the total score of losing players.
	 * 
	 * @param index The index of the current player.
	 * @return The total score of losing players.
	 */
	int getTotalScoreOfLosers(int index) {
		var totalScore = 0;
		for (int x = 0; x < gameSession.getPlayers().size(); x++) {
			if (x != index) {
				var p = gameSession.getPlayers().get(x);
				var cards = p.getHand();
				for (var c : cards) {
					totalScore += c.getScore();
				}
			}
		}
		return totalScore;
	}

	/**
	 * Handles the effect of playing a Wild Card.
	 * 
	 * @param player   The player who played the Wild Card.
	 * @param wildCard The Wild Card that was played.
	 */
	private void handleWildCard(Player player, WildCard wildCard) {
		if (player instanceof Bot bot) {
			model.enums.Color selectedColor = bot.chooseRandomColor();
			wildCard.setColor(selectedColor);
			addStatusMessage(UnoStatusMessages.getWildCardPlayedMessage(player, wildCard.getName(), selectedColor));
		}

		if (wildCard.getWildType() == WildType.WILD_DRAW_4) {
			draw4();
			skipNextPlayer();
		}
	}

	/**
	 * Handles the effect of playing an Action Card.
	 * 
	 * @param player     The player who played the Action Card.
	 * @param actionCard The Action Card that was played.
	 */
	private void handleActionCard(Player player, ActionCard actionCard) {
		switch (actionCard.getAction()) {
		case SKIP:
			skipNextPlayer();
			addStatusMessage(UnoStatusMessages.getSkipCardPlayedMessage(player, actionCard.getName()));
			break;
		case REVERSE:
			gameSession.reverseGameDirection();
			addStatusMessage(UnoStatusMessages.getReverseCardPlayedMessage(player, actionCard.getName()));
			break;
		case DRAW_2:
			addStatusMessage(UnoStatusMessages.getActionCardPlayedMessage(player, actionCard.getName()));
			draw2();
			skipNextPlayer();
		default:
			break;
		}
	}

	/**
	 * Draws a specified number of cards from the draw pile.
	 * 
	 * @param count The number of cards to draw.
	 */
	void drawCards(int count) {
		int currentPlayerIndex = gameSession.getCurrentPlayerIndex();
		int gameDirection = gameSession.getGameDirection();
		int nextPlayerIndex = getNextIndex(currentPlayerIndex, gameDirection, gameSession.getPlayers().size());
		var nextPlayer = gameSession.getPlayers().get(nextPlayerIndex);
		for (int x = 0; x < count; x++) {
			var card = gameSession.drawCard();
			nextPlayer.addCard(card);
		}
		addStatusMessage(UnoStatusMessages.getDrawPenaltyMessage(nextPlayer, count));
		if (CurrentUserManager.getInstance().getCurrentUser().getId() != nextPlayer.getUser().getId())
			paintBotCell(mainCells[nextPlayerIndex], nextPlayer, nextPlayer.getUser());
		else
			paintUserCell();
	}

	/**
	 * Skips the turn of the next player.
	 */
	void skipNextPlayer() {
		int currentPlayerIndex = gameSession.getCurrentPlayerIndex();
		int gameDirection = gameSession.getGameDirection();
		int playerCount = gameSession.getPlayers().size();
		int nextPlayerIndex = getNextIndex(currentPlayerIndex, gameDirection, playerCount);
		gameSession.setCurrentPlayerIndex(nextPlayerIndex);
	}

	/**
	 * Gets the index of the next player in the game session.
	 * 
	 * @param currentIndex The index of the current player.
	 * @param direction    The direction of the game (forward or backward).
	 * @param playerCount  The total number of players in the game.
	 * @return The index of the next player.
	 */
	int getNextIndex(int currentIndex, int direction, int playerCount) {
		int nextIndex = currentIndex + direction;
		if (nextIndex < 0) {
			nextIndex += playerCount;
		}
		return (nextIndex + playerCount) % playerCount;
	}

	/**
	 * Initiates drawing two cards from the draw pile.
	 */
	void draw2() {
		drawCards(2);
	}

	/**
	 * Initiates drawing four cards from the draw pile.
	 */
	void draw4() {
		drawCards(4);
	}

	/**
	 * Adds a status message to the game status area.
	 * 
	 * @param message The status message to add.
	 */
	void addStatusMessage(String message) {
		gameStatusArea.append(message + "\n");

		gameStatusArea.setCaretPosition(gameStatusArea.getDocument().getLength());

		Logger.log(message, FileConstants.GAME_LOGS_FILE_PATH);
	}

	/**
	 * Updates the displayed count of cards in the draw pile.
	 * 
	 * @param count The new count of cards in the draw pile.
	 */
	void updateCardCountInDrawPile(int count) {
		cardCountInDrawPile.setText(Integer.toString(count));
	}

	/**
	 * Updates the image of the draw pile button based on the number of cards in the
	 * draw pile.
	 */
	void updateDrawPileImage() {
		String imagePath;
		var cardCount = gameSession.getDrawPileCardCount();
		imagePath = switch (cardCount) {
		case 4 -> ImagePath.DRAW_PILE_IMAGE_4;
		case 3 -> ImagePath.DRAW_PILE_IMAGE_3;
		case 2 -> ImagePath.DRAW_PILE_IMAGE_2;
		default -> cardCount > 4 ? ImagePath.DRAW_PILE_IMAGE_4 : ImagePath.DEFAULT_CARD_IMAGE_PATH;
		};

		ImageIcon drawPileIcon = new ImageIcon(imagePath);
		Image scaledImage = drawPileIcon.getImage().getScaledInstance(130, 150, Image.SCALE_SMOOTH);
		drawPileIcon = new ImageIcon(scaledImage);
		drawPileButton.setIcon(drawPileIcon);
	}

	/**
	 * Updates the image of the discard pile based on the image path of the played
	 * card.
	 * 
	 * @param imagePath The image path of the played card.
	 */
	void updateDiscardPileImage(String imagePath) {
		ImageIcon discardPileIcon = new ImageIcon(imagePath);
		Image scaledImage = discardPileIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
		discardPileIcon = new ImageIcon(scaledImage);
		discardPileLabel.setIcon(discardPileIcon);
		discardPileLabel.revalidate();
		discardPileLabel.repaint();
	}

	/**
	 * Adds elements representing the current player to the specified cell panel.
	 * 
	 * @param cellPanel The panel representing the cell for the current player.
	 */
	void addCurrentPlayerElements(JPanel cellPanel) {
		try {

			cellPanel.removeAll();
			var currentPlayer = gameSession.getPlayers().get(0);
			JPanel currentPlayerPanel = new JPanel();
			currentPlayerPanel.setOpaque(false);
			JPanel cardPanel = new JPanel();
			cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
			cardPanel.setOpaque(false);

			if (currentPlayer.getCardCount() >= 20) {
				cardPanel.setBorder(new EmptyBorder(15, currentPlayer.getCardCount() * 33, 0, 0));
			} else if (currentPlayer.getCardCount() >= 14) {
				cardPanel.setBorder(new EmptyBorder(15, currentPlayer.getCardCount() * 24, 0, 0));
			} else if (currentPlayer.getCardCount() >= 10) {
				cardPanel.setBorder(new EmptyBorder(15, currentPlayer.getCardCount() * 21, 0, 0));
			} else if (currentPlayer.getCardCount() >= 6) {
				cardPanel.setBorder(new EmptyBorder(15, currentPlayer.getCardCount() * 17, 0, 0));
			}

			cardPanel.setAlignmentX(CENTER_ALIGNMENT);
			var cardWidth = 70;
			var cardHeight = 110;
			boolean isFirstCard = true;
			for (Card card : currentPlayer.getHand()) {
				ImageIcon cardImage = new ImageIcon(card.getImagePath());
				Image img = cardImage.getImage();
				Image resizedImg = img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
				ImageIcon resizedCardImage = new ImageIcon(resizedImg);
				JButton cardButton = new ButtonWithImage(resizedCardImage, cardWidth + 10, cardHeight + 10);
				cardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				cardButton.setOpaque(false);

				int marginToLeft = -(int) (currentPlayer.getCardCount() * 1.4);
				if (isFirstCard) {
					marginToLeft = 0;
					isFirstCard = false;
				}

				cardButton.setBorder(BorderFactory.createEmptyBorder(0, marginToLeft, 0, 0));
				cardButton.addActionListener(e -> handleCardSelection(cardButton, card));
				cardPanel.add(cardButton);
			}
			currentPlayerPanel.add(cardPanel);

			// Add UNO button with image
			ImageIcon unoImage = new ImageIcon(ImagePath.UNO_BUTTON_IMAGE_PATH); // Replace with your image path
			JButton unoButton = new JButton(unoImage);
			unoButton.setOpaque(false);
			unoButton.setContentAreaFilled(false);
			unoButton.setBorderPainted(false);
			if (currentPlayer.getCardCount() == 1) {
				unoButton.setVisible(true);
			} else {
				unoButton.setVisible(false);
			}
			unoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// handleUnoAction();
				}
			});
			cellPanel.add(currentPlayerPanel, BorderLayout.CENTER);
		} catch (Exception e) {
			Logger.log(e.getMessage(), FileConstants.ERROR_LOGS_FILE_PATH);
		}
	}

	/**
	 * Adds elements representing bot players to the game table interface.
	 */
	void addBotPlayerElements() {
		var players = gameSession.getPlayers();
		Dimension[][] initialCellSizes = new Dimension[mainCells.length][];
		for (int i = 0; i < mainCells.length; i++) {
			var cellCoordinates = mainCells[i];
			var cellPanel = getCell(cellCoordinates[0], cellCoordinates[1]);
			initialCellSizes[i] = new Dimension[] { cellPanel.getPreferredSize() };
		}
		for (int x = 1; x < mainCells.length; x++) {
			var cellCoordinates = mainCells[x];
			var player = players.get(x);
			var user = player.getUser();
			paintBotCell(cellCoordinates, player, user);
		}
		for (int i = 0; i < mainCells.length; i++) {
			var cellCoordinates = mainCells[i];
			var cellPanel = getCell(cellCoordinates[0], cellCoordinates[1]);
			cellPanel.setPreferredSize(initialCellSizes[i][0]);
		}
		for (int x = 1; x < mainCells.length; x++) {
			var cellCoordinates = mainCells[x];
			var cellPanel = getCell(cellCoordinates[0], cellCoordinates[1]);
			JPanel botCardPanel = (JPanel) cellPanel.getComponent(0);
			botCardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		}
	}

	/**
	 * Paints the cell representing the current user's player information.
	 */
	void paintUserCell() {
		var playerCell = getCell(mainCells[0][0], mainCells[0][1]);
		addCurrentPlayerElements(playerCell);
	}

	/**
	 * Sets borders for the cells representing players in the game session.
	 */
	void setCellBorders() {
		var currentPlayerIndex = gameSession.getCurrentPlayerIndex();
		var currentPlayerCellCoordinates = mainCells[currentPlayerIndex];
		var currentPlayerCellPanel = getCell(currentPlayerCellCoordinates[0], currentPlayerCellCoordinates[1]);
		currentPlayerCellPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

		for (var cellCoordinates : mainCells) {
			var cellPanel = getCell(cellCoordinates[0], cellCoordinates[1]);
			if (cellCoordinates != currentPlayerCellCoordinates) {
				cellPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			}
		}
	}

	/**
	 * Paints the cell representing bot player information.
	 * 
	 * @param cellCoordinates The coordinates of the cell.
	 * @param player          The bot player to paint.
	 * @param user            The user associated with the bot player.
	 */
	void paintBotCell(int[] cellCoordinates, Player player, User user) {
		var cellPanel = getCell(cellCoordinates[0], cellCoordinates[1]);
		cellPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		cellPanel.removeAll();

		JPanel botCardPanel = new JPanel();
		botCardPanel.setLayout(new BoxLayout(botCardPanel, BoxLayout.Y_AXIS));
		botCardPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		botCardPanel.setOpaque(false);

		JLabel usernameLabel = new JLabel(user.getUsername());
		usernameLabel.setFont(customFont.deriveFont(Font.PLAIN, 20));
		usernameLabel.setForeground(Color.white);
		usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		botCardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
		botCardPanel.add(usernameLabel);
		botCardPanel.add(Box.createRigidArea(new Dimension(0, 25)));

		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Default spacing for the first card
		cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		boolean isFirstCard = true;
		for (int x = 0; x < player.getHand().size(); x++) {
			ImageIcon cardImage = Card.getDefaultCardImage(35, 60);
			JLabel cardLabel = new JLabel(cardImage);

			if (!isFirstCard) {
				var margin = -(int) (player.getHand().size() * 2.5);
				if (player.getHand().size() >= 13) {
					margin = -25;
				}
				cardPanel.add(Box.createRigidArea(new Dimension(margin, 0)));
			} else {
				isFirstCard = false;
			}

			cardPanel.add(cardLabel);
		}

		cardPanel.setOpaque(false);
		botCardPanel.add(cardPanel);

		JLabel lbl = new JLabel(Integer.toString(player.getHand().size()));
		lbl.setFont(customFont.deriveFont(Font.PLAIN, 20));
		lbl.setForeground(Color.white);
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		botCardPanel.add(lbl);

		cellPanel.add(botCardPanel, BorderLayout.CENTER);
		cellPanel.revalidate();
		cellPanel.repaint();
	}

	/**
	 * Initializes the frame of the game table.
	 */
	@Override
	void initializeFrame() {
		int rows = GameTableLayoutHelper.rows;
		int columns = GameTableLayoutHelper.columns;
		var mainPanel = new JPanel(new GridBagLayout());
		toaster = new Toaster(mainPanel);
		cells = new GradientPanel[rows][columns];
		GridBagConstraints[][] gbcArray = GameTableLayoutHelper.generateLayout(numberOfPlayers);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new GradientPanel();
				cells[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				GridBagConstraints gbc = gbcArray[i][j];
				mainPanel.add(cells[i][j], gbc);
			}
		}
		add(mainPanel);
		setVisible(true);
	}

	/**
	 * Gets the center panel of the game table interface.
	 * 
	 * @return The center panel of the game table.
	 */
	public JPanel getCenterPanel() {
		return getCell(1, 1);
	}

	/**
	 * Gets the cell panel at the specified row and column indices.
	 * 
	 * @param row    The row index of the cell.
	 * @param column The column index of the cell.
	 * @return The cell panel at the specified indices.
	 */
	public JPanel getCell(int row, int column) {
		return cells[row][column];
	}

	/**
	 * Leaves the current game session and returns to the main menu.
	 */
	void leaveGame() {
		dispose();

		new MainMenu();
	}
}
