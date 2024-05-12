package model.player;

import model.cards.Card;
import model.enums.Color;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import data.BotRepository;

public class Bot extends Player {

	public Bot(User user, ArrayList<Card> hand) {
		super(user, hand);
	}

	@Override
	public Card getPlayableCard(Card topCard, Color colorToPlay) {
		for (Card card : getHand()) {
			if (card.isPlayableOn(topCard) && card.getColor() == colorToPlay) {
				return card;
			}
		}
		return null;
	}

	public static ArrayList<Bot> getBotPlayers(int numberBots) {
		try {
			List<Bot> bots = BotRepository.getBots();
			Collections.shuffle(bots);
			return new ArrayList<>(bots.subList(0, numberBots));
		} catch (IOException e) {
			// TODO
			return null;
		}
	}

	/**
	 * Choose a random color from the Color enum.
	 * 
	 * @return a random Color
	 */
	public Color chooseRandomColor() {
		Color[] colors = Color.values();

		int numColors = colors.length;

		Random random = new Random();
		int randomIndex = random.nextInt(numColors);

		return colors[randomIndex];
	}
}
