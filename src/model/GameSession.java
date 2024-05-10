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
	private String sessionName;
	private ArrayList<Player> players; // 1 current User, n - 1 bots
	private Map<String, ArrayList<Card>> playerCards;
	private ArrayList<Card> drawPile;
	private ArrayList<Card> discardPile;
	private int currentPlayerIndex = 0;
	private int gameDirection = 1; // clockwise
	private Card topCard;

	private boolean playDirectionClockwise;

	public GameSession(String sessionName) {
		this.sessionName = sessionName;
		initializeDrawPile();
		shuffleDrawPile();
	}

	public void initializeDrawPile() {
		drawPile = new ArrayList<Card>();
		for (Color color : Color.values()) {
			if (color != Color.NONE) {
				// Add number cards
				for (int value = 0; value <= 9; value++) {
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

	public void shuffleDrawPile() {
		Collections.shuffle(drawPile);
	}

	public void initializePlayers(int numBots) {
	    players = new ArrayList<>();
	    
	    // Add Current user
	    User currentUser = CurrentUserManager.getInstance().getCurrentUser();
	    Player currentPlayer = new Player(currentUser, new ArrayList<Card>());
	    players.add(currentPlayer);

	    // Add bots
	    for (int i = 0; i < numBots; i++) {
	        Bot bot = new Bot(); // You can assign IDs to bots dynamically
	        players.add(bot);
	    }
	}
	
	public void distributeCards() {
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
	
	

	
	public void startGame() {
		boolean gameEnded = false;
		while (!gameEnded) {
			Player currentPlayer = nextPlayer();
			if (currentPlayer instanceof Bot) {
				playBotTurn((Bot) currentPlayer);
			} else {
				// Assuming this should be handled by the User interface
				// playHumanTurn(currentPlayer);
			}
			if (currentPlayer.hasWon()) {
				gameEnded = true;
			}
			nextPlayer();
		}
	}

	private void playHumanTurn(Player player) {
		// Implementation of human player's turn will depend on the user interface
	}

	private void playBotTurn(Bot bot) {
		Card playableCard = bot.getPlayableCard(topCard);
		if (playableCard != null) {
			topCard = playableCard;
			bot.removeCard(playableCard);
		} else {
			Card drawnCard = drawCard(); 
			bot.addCard(drawnCard);
			if (drawnCard.isPlayableOn(topCard)) {
				topCard = drawnCard;
				bot.removeCard(drawnCard);
			} else {
				// Bot cannot play and end its turn;
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
		Collections.shuffle(discardPile);
		drawPile.addAll(discardPile);
		discardPile.clear();
	}

	public void playCard(Card card) {
		// Implementation of playing a card by a player
	}

	public Player nextPlayer() {
		var index = currentPlayerIndex;
		int size = players.size();
		if (playDirectionClockwise) {
			index = (index + 1) % size;
		} else {
			index = (index - 1 + size) % size;
		}
		return players.get(index);
	}

	public void declareUno() {
		// Implementation of declaring UNO by a player
	}

	public void penalizeNoUno() {
		// Implementation of penalizing a player for not saying UNO
	}
}
