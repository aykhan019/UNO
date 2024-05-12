package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
	private ArrayList<Player> players; // 1 current User, n - 1 bots
	private ArrayList<Card> drawPile;
	private ArrayList<Card> discardPile;
	private String sessionName;
	private Card topCard;
	private int currentPlayerIndex = 0;
	private int numberOfPlayers;
	private int gameDirection = 1; // clockwise
	private boolean playDirectionClockwise = true;
	private Color colorToPlay;

	public GameSession(int numberOfPlayers, String sessionName) {
		this.sessionName = sessionName;
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public int getGameDirection() {
		return gameDirection;
	}
	
	public void reverseGameDirection() {
		if (gameDirection == 1) {
			gameDirection = -1;
			playDirectionClockwise = false;
		}
		else {
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

	public void initializeGameSession() {
		initializeDrawPile();
		shuffleDrawPile();
		initializePlayers();
		distributeCards();
		discardPile = new ArrayList<Card>();
	}

	private void initializeDrawPile() {
		drawPile = new ArrayList<Card>();
		for (Color color : Color.values()) {
			if (color != Color.NONE) {
				for (int value = 0; value <= 2; value++) {
					if (value != 0) {
						// Two sets of 1-9
						drawPile.add(new NumberCard(color, value));
						drawPile.add(new NumberCard(color, value));
					} else {
						// One 0 card
						drawPile.add(new NumberCard(color, value));
					}
				}

				// Add action cards
				for (ActionType action : ActionType.values()) {
					// Two action card
					drawPile.add(new ActionCard(color, action));
					drawPile.add(new ActionCard(color, action));
					drawPile.add(new ActionCard(color, action));
					drawPile.add(new ActionCard(color, action));
					drawPile.add(new ActionCard(color, action));
					drawPile.add(new ActionCard(color, action));
					drawPile.add(new ActionCard(color, action));
					drawPile.add(new ActionCard(color, action));
				}
			}
		}

		// Add wild cards
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

		// Add Current user
		User currentUser = CurrentUserManager.getInstance().getCurrentUser();
		Player currentPlayer = new Player(currentUser, new ArrayList<Card>());
		players.add(currentPlayer);

		// Add bots
		var bots = Bot.getBotPlayers(numberOfPlayers - 1);
		players.addAll(bots);
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
		if (numPlayers <= 4) {
			return 7;
		} else if (numPlayers <= 7) {
			return 6;
		} else {
			return 5;
		}
	}

	public void playHumanTurn(Player player) {
		// Implementation of human player's turn will depend on the user interface
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
			if (drawnCard.isPlayableOn(topCard)) {
				topCard = drawnCard;
				bot.removeCard(drawnCard);
				return new Object[] { drawnCard, true };
			} else {
				// Bot cannot play and end its turn;
				return null;
			}
		}
	}

	public Card drawCard() {
		if (drawPile.isEmpty()) {
			reshuffleDiscardPile();
		}
		return drawPile.remove(drawPile.size() - 1);
	}

	public void reshuffleDiscardPile() {
		if (discardPile.size() > 0) {

			Collections.shuffle(discardPile);
			drawPile.addAll(discardPile);
			discardPile.clear();
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
		var index = currentPlayerIndex;
		int size = players.size();
		if (playDirectionClockwise) {
			index = (index + 1) % size;
		} else {
			index = (index - 1 + size) % size;
		}
		currentPlayerIndex = index;
		return players.get(currentPlayerIndex);
	}

	public void declareUno() {
		// Implementation of declaring UNO by a player
	}

	public void penalizeNoUno() {
		// Implementation of penalizing a player for not saying UNO
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
