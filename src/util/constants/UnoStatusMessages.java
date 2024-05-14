package util.constants;

import model.enums.Color;
import model.player.Player;

/**
 * The UnoStatusMessages class provides static methods to generate various
 * messages related to the status of Uno gameplay, such as card plays and player
 * actions.
 */
public class UnoStatusMessages {

	/**
	 * Gets the status message for a player's turn.
	 * 
	 * @param player The player whose turn it is.
	 * @return The status message.
	 */
	public static String getPlayerTurnMessage(Player player) {
		return player.getUser().getUsername() + "'s turn.";
	}

	/**
	 * Gets the status message when a player is deciding.
	 * 
	 * @param player The player who is deciding.
	 * @return The status message.
	 */
	public static String getPlayerDecidingMessage(Player player) {
		return player.getUser().getUsername() + " is deciding...";
	}

	/**
	 * Gets the status message when a player decides to pass.
	 * 
	 * @param player The player who is passing.
	 * @return The status message.
	 */
	public static String getPlayerPassMessage(Player player) {
		return player.getUser().getUsername() + " has passed.";
	}

	/**
	 * Gets the status message when a player draws a card.
	 * 
	 * @param player The player who is drawing a card.
	 * @return The status message.
	 */
	public static String getPlayerDrawCardMessage(Player player, int drawCount) {
		return player.getUser().getUsername() + " has drawn " + drawCount + " card(s).";
	}

	/**
	 * Gets the status message when a player plays a card.
	 * 
	 * @param player The player who is playing a card.
	 * @param card   The card being played.
	 * @return The status message.
	 */
	public static String getPlayerPlayCardMessage(Player player, String card) {
		return player.getUser().getUsername() + " played a " + card + ".";
	}

	/**
	 * Gets the status message when the game starts.
	 * 
	 * @return The status message.
	 */
	public static String getGameStartMessage(String gameSessionName) {
		return "The game " + gameSessionName + " has started.";
	}

	/**
	 * Gets the status message when a player wins the game.
	 * 
	 * @param player The player who won.
	 * @return The status message.
	 */
	public static String getPlayerWinMessage(Player player) {
		return player.getUser().getUsername() + " has won!";
	}

	/**
	 * Gets the status message when the deck is empty and needs shuffling.
	 * 
	 * @return The status message.
	 */
	public static String getEmptyDeckShuffleMessage() {
		return "The deck is empty. Shuffling...";
	}

	/**
	 * Gets the status message when the game ends in a draw.
	 * 
	 * @return The status message.
	 */
	public static String getGameDrawMessage() {
		return "The game has ended in a draw.";
	}

	/**
	 * Gets the status message when a player is caught bluffing and draws 4 cards as
	 * a penalty.
	 * 
	 * @param player The player who was caught bluffing.
	 * @return The status message.
	 */
	public static String getBluffPenaltyMessage(Player player) {
		return player.getUser().getUsername() + " was caught bluffing and draws 4 cards.";
	}

	/**
	 * Gets the status message when a player successfully calls Uno.
	 * 
	 * @param player The player who successfully called Uno.
	 * @return The status message.
	 */
	public static String getUnoCallMessage(Player player) {
		return player.getUser().getUsername() + " successfully called Uno!";
	}

	/**
	 * Gets the status message when a Wild card is played and a color is chosen.
	 * 
	 * @param player The player who played the Wild card.
	 * @param color  The color chosen after playing the Wild card.
	 * @return The status message.
	 */
	public static String getWildCardColorMessage(Player player, String color) {
		return "A Wild card was played. " + player.getUser().getUsername() + " chose " + color + ".";
	}

	/**
	 * Gets the status message when a Draw Four card is played and the next player
	 * draws 4 cards.
	 * 
	 * @param player The player who played the Draw Four card.
	 * @return The status message.
	 */
	public static String getDrawFourPenaltyMessage(Player player) {
		return "A Draw Four card was played. " + player.getUser().getUsername() + " drew 4 cards.";
	}

	/**
	 * Returns the message when a player draws cards as a penalty.
	 * 
	 * @param player    The player who draws cards.
	 * @param cardCount The number of cards drawn.
	 * @return The message indicating the draw penalty.
	 */
	public static String getDrawPenaltyMessage(Player player, int cardCount) {
		return "A Draw " + cardCount + " card was played. " + player.getUser().getUsername() + " drew " + cardCount
				+ " cards.";
	}

	/**
	 * Gets the status message when a player joins the game.
	 * 
	 * @param player The player who joined the game.
	 * @return The status message.
	 */
	public static String getPlayerJoinMessage(Player player) {
		return player.getUser().getUsername() + " has joined the game.";
	}

	/**
	 * Gets the status message when the game lobby is full and waiting for players
	 * to start.
	 * 
	 * @return The status message.
	 */
	public static String getGameLobbyFullMessage() {
		return "Game is full. Waiting for players to start.";
	}

	/**
	 * Gets the status message when a player is down to their last card.
	 * 
	 * @param player The player with only one card left.
	 * @return The status message.
	 */
	public static String getPlayerUnoMessage(Player player) {
		return player.getUser().getUsername() + " has 1 card left!";
	}

	/**
	 * Gets the status message when a player has only one card left and calls Uno.
	 * 
	 * @param player The player who successfully called Uno.
	 * @return The status message.
	 */
	public static String getPlayerCalledUnoMessage(Player player) {
		return player.getUser().getUsername() + " is Uno!";
	}

	/**
	 * Gets the status message when a player wins a round.
	 * 
	 * @param player The player who won the round.
	 * @return The status message.
	 */
	public static String getPlayerRoundWinMessage(Player player) {
		return player.getUser().getUsername() + " has won the round!";
	}

	/**
	 * Gets the status message when a player is out of cards and wins the game.
	 * 
	 * @param player The player who won the game.
	 * @return The status message.
	 */
	public static String getPlayerGameWinMessage(Player player) {
		return player.getUser().getUsername() + " is out of cards and wins the game!";
	}

	/**
	 * Gets the status message when a player's turn is skipped due to a Skip card.
	 * 
	 * @param player The player who was skipped.
	 * @return The status message.
	 */
	public static String getPlayerSkippedMessage(Player player) {
		return player.getUser().getUsername() + "'s turn was skipped.";
	}

	/**
	 * Gets the status message when a player's turn is reversed due to a Reverse
	 * card.
	 * 
	 * @param player The player whose turn direction is reversed.
	 * @return The status message.
	 */
	public static String getPlayerTurnReversedMessage(Player player) {
		return player.getUser().getUsername() + "'s turn direction is reversed.";
	}

	/**
	 * Gets the status message when a player plays a Skip card.
	 * 
	 * @param player The player who played the Skip card.
	 * @return The status message.
	 */
	public static String getPlayerSkipCardPlayedMessage(Player player) {
		return player.getUser().getUsername() + " played a Skip card.";
	}

	/**
	 * Gets the status message when a player plays a Reverse card.
	 * 
	 * @param player The player who played the Reverse card.
	 * @return The status message.
	 */
	public static String getPlayerReverseCardPlayedMessage(Player player) {
		return player.getUser().getUsername() + " played a Reverse card.";
	}

	/**
	 * Returns the message when a Wild Card is played.
	 * 
	 * @param player        The player who played the card.
	 * @param cardName      The name of the Wild Card.
	 * @param selectedColor The color chosen by the player.
	 * @return The message indicating the Wild Card played.
	 */
	public static String getWildCardPlayedMessage(Player player, String cardName, Color selectedColor) {
		return String.format("%s played a Wild Card (%s)\nand chose %s color.", player.getUser().getUsername(),
				cardName, selectedColor.toString());
	}

	/**
	 * Returns the message when a Skip Card is played.
	 * 
	 * @param player   The player who played the card.
	 * @param cardName The name of the Skip Card.
	 * @return The message indicating the Skip Card played.
	 */
	public static String getSkipCardPlayedMessage(Player player, String cardName) {
		return String.format("%s played a Skip Card (%s).", player.getUser().getUsername(), cardName);
	}

	/**
	 * Returns the message when a Reverse Card is played.
	 * 
	 * @param player   The player who played the card.
	 * @param cardName The name of the Reverse Card.
	 * @return The message indicating the Reverse Card played.
	 */
	public static String getReverseCardPlayedMessage(Player player, String cardName) {
		return String.format("%s played a Reverse Card (%s).", player.getUser().getUsername(), cardName);
	}

	/**
	 * Returns the message when a Draw Two Card is played.
	 * 
	 * @param player   The player who played the card.
	 * @param cardName The name of the Draw Two Card.
	 * @return The message indicating the Draw Two Card played.
	 */
	public static String getDrawTwoCardPlayedMessage(Player player, String cardName) {
		return String.format("%s played a Draw Two Card (%s).", player.getUser().getUsername(), cardName);
	}

	/**
	 * Returns the message when a Draw Four Card is played.
	 * 
	 * @param player   The player who played the card.
	 * @param cardName The name of the Draw Four Card.
	 * @return The message indicating the Draw Four Card played.
	 */
	public static String getDrawFourCardPlayedMessage(Player player, String cardName) {
		return String.format("%s played a Draw Four Card (%s).", player.getUser().getUsername(), cardName);
	}

	/**
	 * Returns the message when an Action Card other than Wild, Skip, Reverse, Draw
	 * Two, or Draw Four is played.
	 * 
	 * @param player   The player who played the card.
	 * @param cardName The name of the Action Card.
	 * @return The message indicating the Action Card played.
	 */
	public static String getActionCardPlayedMessage(Player player, String cardName) {
		return String.format("%s played an Action Card (%s).", player.getUser().getUsername(), cardName);
	}

	/**
	 * Returns the message when a player is skipped due to having no playable cards.
	 * 
	 * @param player The player who was skipped.
	 * @return The message indicating the skipped turn.
	 */
	public static String getSkippedTurnMessage(Player player) {
		return player.getUser().getUsername() + " skipped due to no playable cards.";
	}
}
