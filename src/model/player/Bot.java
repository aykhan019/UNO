package model.player;

import model.cards.Card;
import model.enums.Color;
import model.user.User;
import util.constants.ErrorConstants;
import util.constants.FileConstants;
import util.helpers.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import data.BotRepository;

/**
 * Represents a bot player in the card game.
 */
public class Bot extends Player {

    /**
     * Constructs a bot player with the specified user and hand of cards.
     *
     * @param user The user associated with the bot.
     * @param hand The initial hand of cards for the bot.
     */
    public Bot(User user, ArrayList<Card> hand) {
        super(user, hand);
    }

    /**
     * Retrieves a playable card from the bot's hand based on the top card of the pile and the color to play.
     *
     * @param topCard     The top card of the pile.
     * @param colorToPlay The color to play (or NONE if color doesn't matter).
     * @return A playable card from the bot's hand, or null if no card is playable.
     */
    @Override
    public Card getPlayableCard(Card topCard, Color colorToPlay) {
        for (Card card : getHand()) {
            if (card.isPlayableOn(topCard) && card.getColor() == colorToPlay) {
                return card;
            }
        }
        return null;
    }

    /**
     * Retrieves a specified number of bot players.
     *
     * @param numberBots The number of bot players to retrieve.
     * @return An ArrayList of bot players.
     */
    public static ArrayList<Bot> getBotPlayers(int numberBots) {
        try {
            List<Bot> bots = BotRepository.getBots();
            Collections.shuffle(bots);
            return new ArrayList<>(bots.subList(0, numberBots));
        } catch (IOException e) {
            Logger.log(ErrorConstants.BOTS_DO_NOT_EXIST, FileConstants.ERROR_LOGS_FILE_PATH);
            return null;
        }
    }

    /**
     * Chooses a random color from the Color enum.
     *
     * @return A randomly chosen Color.
     */
    public Color chooseRandomColor() {
        Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
        int numColors = colors.length;

        Random random = new Random();
        int randomIndex = random.nextInt(numColors);

        return colors[randomIndex];
    }
}
