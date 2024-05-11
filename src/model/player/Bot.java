package model.player;

import model.cards.Card;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.BotRepository;

public class Bot extends Player {

	public Bot(User user, ArrayList<Card> hand) {
		super(user, hand);
	}

	@Override
	public Card getPlayableCard(Card topCard) {
		for (Card card : getHand()) {
			if (card.isPlayableOn(topCard)) {
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
}
