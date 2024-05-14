package model.cards;

import model.enums.ActionType;
import model.enums.Color;
import util.helpers.StringUtils;

/**
 * Represents an Action Card in the Uno game.
 * Action Cards have a specific action associated with them, such as Skip, Reverse, or Draw Two.
 */
public class ActionCard extends Card {

    private ActionType action; // The type of action associated with the card

    /**
     * Constructs an Action Card with the specified color and action.
     *
     * @param color  The color of the card.
     * @param action The type of action associated with the card.
     */
    public ActionCard(Color color, ActionType action) {
        super(color, -1, 20, "images/cards/" + StringUtils.capitalize(color.toString()) + "_"
                + StringUtils.capitalize(action.toString()) + ".jpg");
        this.action = action;
    }

    /**
     * Gets the type of action associated with the card.
     *
     * @return The ActionType of the card.
     */
    public ActionType getAction() {
        return action;
    }

    /**
     * Checks if this Action Card is playable on another card.
     * Action Cards can be played on cards of the same action or color, or on Number Cards or Wild Cards of the same color.
     *
     * @param otherCard The card to check if this Action Card is playable on.
     * @return True if this Action Card is playable on the other card, false otherwise.
     */
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
