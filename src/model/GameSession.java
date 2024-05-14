package model;

import java.util.ArrayList;
import java.util.Collections;

import model.cards.ActionCard;
import model.cards.Card;
import model.cards.NumberCard;
import model.cards.WildCard;
import model.enums.ActionType;
import model.enums.Color;
import model.enums.WildType;
import model.player.Bot;
import model.player.Player;
import model.user.User;
import util.session.CurrentUserManager;

/**
 * Represents a session of the Uno game.
 */
public class GameSession {
	/**
	 * List of players participating in the game.
	 */
	private ArrayList<Player> players;

	/**
	 * List of cards in the draw pile.
	 */
	private ArrayList<Card> drawPile;

	/**
	 * List of cards in the discard pile.
	 */
	private ArrayList<Card> discardPile;

	/**
	 * Name of the game session.
	 */
	private String sessionName;

	/**
	 * The card currently on the top of the discard pile.
	 */
	private Card topCard;

	/**
	 * Index of the current player in the player list.
	 */
	private int currentPlayerIndex = 0;

	/**
	 * Number of players in the game.
	 */
	private int numberOfPlayers;

	/**
	 * Direction of the game play (-1 for anti-clockwise, 1 for clockwise).
	 */
	private int gameDirection = 1;

	/**
	 * Indicates whether the game play direction is clockwise.
	 */
	private boolean playDirectionClockwise = true;

	/**
	 * The color chosen to be played after a wild card is played.
	 */
	private Color colorToPlay;

	/**
	 * Creates a new game session with the specified number of players and session
	 * name.
	 * 
	 * @param numberOfPlayers The number of players in the game.
	 * @param sessionName     The name of the game session.
	 */
	public GameSession(int numberOfPlayers, String sessionName) {
		this.sessionName = sessionName;
		this.numberOfPlayers = numberOfPlayers;
	}

	/**
	 * Initializes the game session by setting up draw pile, players, and
	 * distributing cards.
	 */
	public void initializeGameSession() {
		initializeDrawPile();
		shuffleDrawPile();
		initializePlayers();
		distributeCards();
		discardPile = new ArrayList<>();
	}

	/**
	 * Initializes the draw pile by adding number cards, action cards, and wild
	 * cards.
	 */
	private void initializeDrawPile() {
		drawPile = new ArrayList<>();
		for (Color color : Color.values()) {
			if (color != Color.NONE) {
				for (int value = 0; value <= 9; value++) {
					addNumberCardsToDrawPile(color, value);
				}
				addActionCardsToDrawPile(color);
			}
		}
		addWildCardsToDrawPile();
	}

	/**
	 * Adds number cards of the specified color and value to the draw pile.
	 * 
	 * @param color The color of the number cards.
	 * @param value The value of the number cards.
	 */
	private void addNumberCardsToDrawPile(Color color, int value) {
		if (value != 0) {
			drawPile.add(new NumberCard(color, value));
			drawPile.add(new NumberCard(color, value));
		} else {
			drawPile.add(new NumberCard(color, value));
		}
	}

	/**
	 * Adds action cards of the specified color to the draw pile.
	 * 
	 * @param color The color of the action cards.
	 */
	private void addActionCardsToDrawPile(Color color) {
		for (ActionType action : ActionType.values()) {
			drawPile.add(new ActionCard(color, action));
			drawPile.add(new ActionCard(color, action));
		}
	}

	/**
	 * Adds wild cards to the draw pile.
	 */
	private void addWildCardsToDrawPile() {
		for (WildType wildType : WildType.values()) {
			for (int i = 0; i < 4; i++) {
				drawPile.add(new WildCard(wildType));
			}
		}
	}

	/**
	 * Shuffles the draw pile.
	 */
	private void shuffleDrawPile() {
		Collections.shuffle(drawPile);
	}

	/**
	 * Initializes the players in the game session, including the current user and
	 * bots.
	 */
	private void initializePlayers() {
		players = new ArrayList<>();
		User currentUser = CurrentUserManager.getInstance().getCurrentUser();
		Player currentPlayer = new Player(currentUser, new ArrayList<>());
		players.add(currentPlayer);
		players.addAll(Bot.getBotPlayers(numberOfPlayers - 1));
	}

	/**
	 * Distributes cards from the draw pile to the players.
	 */
	private void distributeCards() {
		int numPlayers = players.size();
		int numCardsPerPlayer = calculateNumCardsPerPlayer(numPlayers);
		for (Player player : players) {
			for (int i = 0; i < numCardsPerPlayer; i++) {
				Card card = drawCard();
				player.addCard(card);
			}
		}
	}

	/**
	 * Calculates the number of cards each player should receive based on the number
	 * of players.
	 * 
	 * @param numPlayers The total number of players in the game.
	 * @return The number of cards each player should receive.
	 */
	private int calculateNumCardsPerPlayer(int numPlayers) {
		return switch (numPlayers) {
		case 1, 2, 3, 4 -> 7;
		case 5, 6, 7 -> 6;
		default -> 5;
		};
	}

	/**
	 * Simulates a bot's turn in the game.
	 * 
	 * @param bot The bot whose turn is being played.
	 * @return An array containing information about the bot's turn.
	 */
	public Object[] playBotTurn(Bot bot) {
		Card playableCard = bot.getPlayableCard(topCard, colorToPlay);
		if (playableCard != null) {
			topCard = playableCard;
			bot.removeCard(playableCard);
			return new Object[] { playableCard, false };
		} else {
			Card drawnCard = drawCard();
			bot.addCard(drawnCard);
			var drawCount = 1;

			while (!drawnCard.isPlayableOn(topCard)) {
				drawnCard = drawCard();
				bot.addCard(drawnCard);
				drawCount++;
			}

			topCard = drawnCard;
			bot.removeCard(drawnCard);
			return new Object[] { drawnCard, true, drawCount };
		}
	}

	/**
	 * Gets the direction of the game play.
	 * 
	 * @return The direction of the game play (-1 for anti-clockwise, 1 for
	 *         clockwise).
	 */
	public int getGameDirection() {
		return gameDirection;
	}

	/**
	 * Reverses the direction of the game play.
	 */
	public void reverseGameDirection() {
		if (gameDirection == 1) {
			gameDirection = -1;
			playDirectionClockwise = false;
		} else {
			gameDirection = 1;
			playDirectionClockwise = true;
		}
	}

	/**
	 * Sets the color to be played after a wild card is played.
	 * 
	 * @param color The color chosen to be played.
	 */
	public void setColorToPlay(Color color) {
		colorToPlay = color;
	}

	/**
	 * Gets the color currently chosen to be played.
	 * 
	 * @return The color chosen to be played.
	 */
	public Color getColorToPlay() {
		return colorToPlay;
	}

	/**
	 * Gets the index of the current player.
	 * 
	 * @return The index of the current player.
	 */
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	/**
	 * Gets the current player.
	 * 
	 * @return The current player.
	 */
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	/**
	 * Sets the index of the current player.
	 * 
	 * @param index The index of the current player.
	 */
	public void setCurrentPlayerIndex(int index) {
		currentPlayerIndex = index;
	}

	/**
	 * Gets the number of cards in the draw pile.
	 * 
	 * @return The number of cards in the draw pile.
	 */
	public int getDrawPileCardCount() {
		return drawPile.size();
	}

	/**
	 * Sets the name of the game session.
	 * 
	 * @param sessionName The name of the game session.
	 */
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	/**
	 * Gets the name of the game session.
	 * 
	 * @return The name of the game session.
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * Draws a card from the draw pile.
	 * 
	 * @return The card drawn from the draw pile.
	 */
	public Card drawCard() {
		if (drawPile.isEmpty() || drawPile.size() == 1) {
			reshuffleDiscardPile();
		}
		return drawPile.remove(drawPile.size() - 1);
	}

	/**
	 * Reshuffles the discard pile into the draw pile.
	 */
	public void reshuffleDiscardPile() {
		if (!discardPile.isEmpty()) {
			Collections.shuffle(discardPile);
			drawPile.addAll(discardPile);
		}
	}

	/**
	 * Plays a card from the current player's hand.
	 * 
	 * @param card The card to be played.
	 * @return True if the card is successfully played, false otherwise.
	 */
	public boolean playCard(Card card) {
		if (card.isPlayableOn(topCard)) {
			topCard = card;
			Player currentPlayer = players.get(currentPlayerIndex);
			currentPlayer.removeCard(card);
			discardPile.add(card);
			colorToPlay = topCard.getColor();
			return true;
		}
		return false;
	}

	/**
	 * Moves to the next player in the game, based on the game direction.
	 * 
	 * @return The next player in the game.
	 */
	public Player nextPlayer() {
		int index = currentPlayerIndex;
		int size = players.size();
		if (playDirectionClockwise) {
			index = (index + 1) % size;
		} else {
			index = (index - 1 + size) % size;
		}
		currentPlayerIndex = index;
		return players.get(currentPlayerIndex);
	}

	/**
	 * Checks if the current player has Uno (one card left).
	 * 
	 * @return True if the current player has Uno, false otherwise.
	 */
	public boolean isUno() {
		Player currentPlayer = getCurrentPlayer();
		return currentPlayer.getCardCount() == 1;
	}

	/**
	 * Penalizes the current player by drawing two cards.
	 */
	public void penalizeCurrentPlayer() {
		Player currentPlayer = getCurrentPlayer();
		for (int i = 0; i < 2; i++) {
			currentPlayer.addCard(drawCard());
		}
	}

	/**
	 * Skips the next player's turn.
	 */
	public void skipNextPlayer() {
		nextPlayer();
	}

	/**
	 * Checks if the game has ended (i.e., any player has no cards left).
	 * 
	 * @return True if the game has ended, false otherwise.
	 */
	public boolean isGameEnd() {
		for (Player player : players) {
			if (player.getCardCount() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the winner of the game.
	 * 
	 * @return The player who has won the game, or null if no winner yet.
	 */
	public Player getWinner() {
		for (Player player : players) {
			if (player.getCardCount() == 0) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Gets the list of players participating in the game.
	 * 
	 * @return The list of players participating in the game.
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
}
