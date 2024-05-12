package model.cards;

import model.enums.ActionType;

import model.enums.Color;
import util.helpers.StringUtils;

public class ActionCard extends Card {
	ActionType action;

	public ActionCard(Color color, ActionType action) {
		super(color, -1, 20, "images/cards/" + StringUtils.capitalize(color.toString()) + "_"
				+ StringUtils.capitalize(action.toString()) + ".jpg");
		this.action = action;
	}

	public ActionType getAction() {
		return action;
	}

	@Override
	public boolean isPlayableOn(Card otherCard) {
		if (otherCard instanceof ActionCard other) {
			if (other.getAction() == this.getAction() || this.getColor() == other.getColor())
				return true;
			return false;
		} else if (otherCard instanceof NumberCard || otherCard instanceof WildCard) {
			return this.getColor() == otherCard.getColor();
		}
		return true;
	}
}
