package model.player;

import model.cards.Card;
import model.enums.Color;
import model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the card game.
 */
public class Player {
    private User user;
    private List<Card> hand;

    /**
     * Constructs a player with the specified user and hand of cards.
     *
     * @param user The user associated with the player.
     * @param hand The initial hand of cards for the player.
     */
    public Player(User user, ArrayList<Card> hand) {
        this.user = user;
        this.hand = hand;
    }

    /**
     * Retrieves the user associated with the player.
     *
     * @return The user associated with the player.
     */
    public User getUser() {
        return user;
    }

    /**
     * Retrieves the current hand of cards held by the player.
     *
     * @return The current hand of cards held by the player.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to add to the player's hand.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes a card from the player's hand.
     *
     * @param card The card to remove from the player's hand.
     */
    public void removeCard(Card card) {
        hand.remove(card);
    }
    
    /**
     * Retrieves the number of cards in the player's hand.
     *
     * @return The number of cards in the player's hand.
     */
    public int getCardCount() {
        return hand.size();
    }

    /**
     * Retrieves a playable card from the player's hand based on the top card of the pile and the color to play.
     *
     * @param topCard     The top card of the pile.
     * @param colorToPlay The color to play (or NONE if color doesn't matter).
     * @return A playable card from the player's hand, or null if no card is playable.
     */
    public Card getPlayableCard(Card topCard, Color colorToPlay) {
        for (Card card : hand) {
            if (card.isPlayableOn(topCard) && card.getColor() == colorToPlay) {
                return card;
            }
        }
        return null;
    }

    /**
     * Checks if the player has won the game (i.e., has no cards left in their hand).
     *
     * @return True if the player has won, otherwise false.
     */
    public boolean hasWon() {
        return hand.isEmpty();
    }
}
