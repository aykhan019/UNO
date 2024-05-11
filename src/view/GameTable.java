package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import model.GameSession;
import model.cards.Card;
import model.cards.WildCard;
import model.player.Bot;
import model.player.Player;
import model.user.User;
import util.constants.ErrorConstants;
import util.constants.FontConstants;
import util.constants.ImagePath;
import util.constants.WindowConstants;
import util.ui.GameTableLayoutHelper;
import util.ui.UIUtils;
import util.ui.toaster.Toaster;
import view.CustomComponents.ButtonWithImage;
import view.CustomComponents.GradientPanel;
import view.popups.ColorSelectionPopup;

public class GameTable extends BaseFrame {

	private Toaster toaster;
	private GameSession gameSession;
	private JPanel[][] cells;
	private int[][] mainCells;
	private int numberOfPlayers;
	private JLabel discardPileLabel;
	private JButton drawPileButton;
	private JLabel cardCountInDrawPile;
	private final Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);
	private final Font textAreaFont = UIUtils.loadCustomFont(FontConstants.NeuropoliticalFontPath);
	private boolean drawPileButtonClicked = false;
	JTextArea gameStatusArea;

	public GameTable(int numberOfPlayers, String gameSessionName) {
		super(WindowConstants.GAME_TABLE_WINDOW);
		this.numberOfPlayers = numberOfPlayers;
		gameSession = new GameSession(numberOfPlayers, gameSessionName);
		gameSession.initializeGameSession();
		initializeFrame();
		mainCells = GameTableLayoutHelper.getPlayerCells(numberOfPlayers);
		addBotPlayerElements();
		paintUserCell();
		addCenterElements();

		addStatusMessage("Hiii");
		addStatusMessage("Hiii");
		addStatusMessage("Hiii");
		addStatusMessage("Hiii");
		addStatusMessage("Hiii");
		addStatusMessage("Hiii");
		addStatusMessage("Hiii");
		addStatusMessage("Hiii");
	}

	void addCenterElements() {
		var centerPanel = getCenterPanel();
		centerPanel.setLayout(null);

		// Create JTextArea for game status
		gameStatusArea = new JTextArea();
		gameStatusArea.setFont(textAreaFont.deriveFont(Font.PLAIN, 20));
		gameStatusArea.setEditable(false); 
		gameStatusArea.setLineWrap(true);
		gameStatusArea.setWrapStyleWord(true); 
		gameStatusArea.setOpaque(false);
		gameStatusArea.setBackground(new Color(0, 0, 0, 0)); 
		gameStatusArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Wrap JTextArea in a JScrollPane
		JScrollPane scrollPane = new JScrollPane(gameStatusArea);
		scrollPane.setBounds(10, 60, 300, 150); 
		scrollPane.setBorder(null); 
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getViewport().setBackground(Color.red);
		scrollPane.revalidate();
		scrollPane.repaint();
		// Create a custom Document listener to auto-scroll JTextArea
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

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		centerPanel.add(scrollPane);

		drawPileButton = new JButton();
		drawPileButton.setBounds(400, 90, 130, 150);
		centerPanel.add(drawPileButton);
		drawPileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!drawPileButtonClicked) {
					drawPileButtonClicked = true;

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

		discardPileLabel = new JLabel();
		discardPileLabel.setBounds(540, 90, 103, 150);
		discardPileLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		centerPanel.add(discardPileLabel);

		ImageIcon icon = new ImageIcon(ImagePath.BACK_ICON);
		Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ButtonWithImage leaveGameBtn = new ButtonWithImage(new ImageIcon(image), 50, 50);
		leaveGameBtn.addActionListener(e -> goBack());
		leaveGameBtn.setSize(50, 50);
		leaveGameBtn.setBounds(5, 5, 50, 50);
		centerPanel.add(leaveGameBtn);

		cardCountInDrawPile = new JLabel();
		cardCountInDrawPile.setFont(customFont.deriveFont(Font.PLAIN, 22));
		cardCountInDrawPile.setForeground(Color.white);
		cardCountInDrawPile.setBounds(440, 245, 50, 50);
		centerPanel.add(cardCountInDrawPile);

		updateCardCountInDrawPile(gameSession.getDrawPileCardCount());
		updateDrawPileImage();
	}

	void addStatusMessage(String message) {
		gameStatusArea.append(message + "\n");

		gameStatusArea.setCaretPosition(gameStatusArea.getDocument().getLength());
	}

	void updateCardCountInDrawPile(int count) {
		cardCountInDrawPile.setText(Integer.toString(count));
	}

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

	void updateDiscardPileImage(String imagePath) {
		ImageIcon discardPileIcon = new ImageIcon(imagePath);
		Image scaledImage = discardPileIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
		discardPileIcon = new ImageIcon(scaledImage);
		discardPileLabel.setIcon(discardPileIcon);
		discardPileLabel.revalidate();
		discardPileLabel.repaint();
	}

	void addCurrentPlayerElements(JPanel cellPanel) {
		cellPanel.removeAll();
		var currentPlayer = gameSession.getPlayers().get(0);
		JPanel currentPlayerPanel = new JPanel();
		currentPlayerPanel.setOpaque(false);
		JPanel cardPanel = new JPanel();
		cardPanel.setOpaque(false);
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
		cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
		var cardWidth = 70;
		var cardHeight = 110;
		for (Card card : currentPlayer.getHand()) {
			ImageIcon cardImage = new ImageIcon(card.getImagePath());
			Image img = cardImage.getImage();
			Image resizedImg = img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
			ImageIcon resizedCardImage = new ImageIcon(resizedImg);
			JButton cardButton = new ButtonWithImage(resizedCardImage, cardWidth + 10, cardHeight + 10);
			cardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			cardButton.setOpaque(false);
			cardButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			cardButton.addActionListener(e -> handleCardSelection(cardButton, card));
			cardPanel.add(cardButton);
		}
		currentPlayerPanel.add(cardPanel);
		cellPanel.add(currentPlayerPanel, BorderLayout.CENTER);
	}

	void handleCardSelection(JButton btn, Card card) {
		var played = false;
		if (card instanceof WildCard) {
			ColorSelectionPopup colorSelectionPopup = new ColorSelectionPopup(this);
			colorSelectionPopup.setVisible(true);
			model.enums.Color selectedColor = colorSelectionPopup.getSelectedColor();
			((WildCard) card).setColor(selectedColor);
		}
		played = gameSession.playCard(card);
		if (played) {
			Container parent = btn.getParent();
			if (parent instanceof JPanel) {
				JPanel cardPanel = (JPanel) parent;
				cardPanel.remove(btn);
				cardPanel.revalidate();
				cardPanel.repaint();
			}
			updateDiscardPileImage(card.getImagePath());
			drawPileButtonClicked = true;
			Timer timer = new Timer(1000, e -> invokeBotTurn());
			timer.setRepeats(false);
			timer.start();
		} else {
			toaster.warn(ErrorConstants.UNKNOWN_ERROR);
		}
	}

	void invokeBotTurn() {
		Player currentPlayer = gameSession.nextPlayer();
		if (currentPlayer instanceof Bot bot) {
			var playedCardByBot = gameSession.playBotTurn(bot);
			if (playedCardByBot != null) {
				var playerIndex = gameSession.getCurrentPlayerIndex();
				updateDiscardPileImage(playedCardByBot.getImagePath());
				paintBotCell(mainCells[playerIndex], bot, bot.getUser());
			}
			if (currentPlayer.hasWon()) {
				// Handle game end
			} else {
				Timer timer = new Timer(1000, e -> invokeBotTurn());
				timer.setRepeats(false);
				timer.start();
			}
		} else {
			drawPileButtonClicked = false;
		}
		updateCardCountInDrawPile(gameSession.getDrawPileCardCount());
	}

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

	void paintUserCell() {
		var playerCell = getCell(mainCells[0][0], mainCells[0][1]);
		addCurrentPlayerElements(playerCell);
	}

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
		cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		for (Card card : player.getHand()) {
			ImageIcon cardImage = Card.getDefaultCardImage(30, 60);
			JLabel cardLabel = new JLabel(cardImage);
			cardPanel.add(cardLabel);
		}
		cardPanel.setOpaque(false);
		botCardPanel.add(cardPanel);
		cellPanel.add(botCardPanel, BorderLayout.CENTER);
		cellPanel.revalidate();
		cellPanel.repaint();
	}

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

	public JPanel getCenterPanel() {
		return getCell(1, 1);
	}

	public JPanel getCell(int row, int column) {
		return cells[row][column];
	}

	void goBack() {
	}
}
