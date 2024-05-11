package model.cards;

import model.enums.ActionType;
import model.enums.Color;
import util.helpers.StringUtils;

public class ActionCard extends Card {
    ActionType action;

    public ActionCard(Color color, ActionType action) {
        super(color, -1, 20, "images/card/" + StringUtils.capitalize(color.toString()) + "_" + StringUtils.capitalize(action.toString()) + ".jpg");
        this.action = action;
    }

	public ActionType getAction() {
		return action;
	}
    
    @Override
    public boolean isPlayableOn(Card otherCard) {
    	if (otherCard instanceof ActionCard) {
			// Action cards can be played on other action cards if they have the same color
			// or action
			ActionCard otherActionCard = (ActionCard) otherCard;
			return this.getColor() == otherActionCard.getColor() || this.getAction() == otherActionCard.getAction();
		} else if (otherCard instanceof NumberCard || otherCard instanceof WildCard) {
			// Action cards can be played on number cards or wild cards
			return true;
		}
		return false;
    }
}
