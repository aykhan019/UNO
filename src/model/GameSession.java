package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

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

public class GameSession {
	private Map<String, ArrayList<Card>> playerCards;
	private ArrayList<Player> players;
	private ArrayList<Card> drawPile;
	private ArrayList<Card> discardPile;
	private String sessionName;
	private Card topCard;
	private int currentPlayerIndex = 0;
	private int numberOfPlayers;
	private int gameDirection = 1;
	private boolean playDirectionClockwise = true;
	private Color colorToPlay;

	public GameSession(int numberOfPlayers, String sessionName) {
		this.sessionName = sessionName;
		this.numberOfPlayers = numberOfPlayers;
	}

	public void initializeGameSession() {
		initializeDrawPile();
		shuffleDrawPile();
		initializePlayers();
		distributeCards();
		discardPile = new ArrayList<>();
	}

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

	private void addNumberCardsToDrawPile(Color color, int value) {
		if (value != 0) {
			drawPile.add(new NumberCard(color, value));
			drawPile.add(new NumberCard(color, value));
		} else {
			drawPile.add(new NumberCard(color, value));
		}
	}

	private void addActionCardsToDrawPile(Color color) {
		for (ActionType action : ActionType.values()) {
			drawPile.add(new ActionCard(color, action));
			drawPile.add(new ActionCard(color, action));
		}
	}

	private void addWildCardsToDrawPile() {
		for (WildType wildType : WildType.values()) {
			for (int i = 0; i < 4; i++) {
				drawPile.add(new WildCard(wildType));
			}
		}
	}

	private void shuffleDrawPile() {
		Collections.shuffle(drawPile);
	}

	private void initializePlayers() {
		players = new ArrayList<>();
		User currentUser = CurrentUserManager.getInstance().getCurrentUser();
		Player currentPlayer = new Player(currentUser, new ArrayList<>());
		players.add(currentPlayer);
		players.addAll(Bot.getBotPlayers(numberOfPlayers - 1));
	}

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

	private int calculateNumCardsPerPlayer(int numPlayers) {
		return switch (numPlayers) {
		case 1, 2, 3, 4 -> 7;
		case 5, 6, 7 -> 6;
		default -> 5;
		};
	}

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

	public int getGameDirection() {
		return gameDirection;
	}

	public void reverseGameDirection() {
		if (gameDirection == 1) {
			gameDirection = -1;
			playDirectionClockwise = false;
		} else {
			gameDirection = 1;
			playDirectionClockwise = true;
		}
	}

	public void setColorToPlay(Color color) {
		colorToPlay = color;
	}

	public Color getColorToPlay() {
		return colorToPlay;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public void setCurrentPlayerIndex(int index) {
		currentPlayerIndex = index;
	}

	public int getDrawPileCardCount() {
		return drawPile.size();
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getSessionName() {
		return sessionName;
	}

	public Card drawCard() {
		if (drawPile.isEmpty() || drawPile.size() == 1) {
			reshuffleDiscardPile();
		}
		return drawPile.remove(drawPile.size() - 1);
	}

	public void reshuffleDiscardPile() {
		if (!discardPile.isEmpty()) {
			Collections.shuffle(discardPile);
			drawPile.addAll(discardPile);
		}
	}

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

	public boolean isUno() {
		Player currentPlayer = getCurrentPlayer();
		return currentPlayer.getCardCount() == 1;
	}

	public void penalizeCurrentPlayer() {
		Player currentPlayer = getCurrentPlayer();
		for (int i = 0; i < 2; i++) {
			currentPlayer.addCard(drawCard());
		}
	}

	public void skipNextPlayer() {
		nextPlayer();
	}

	public boolean isGameEnd() {
		for (Player player : players) {
			if (player.getCardCount() == 0) {
				return true;
			}
		}
		return false;
	}

	public Player getWinner() {
		for (Player player : players) {
			if (player.getCardCount() == 0) {
				return player;
			}
		}
		return null;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
