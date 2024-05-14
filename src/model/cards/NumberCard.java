package model.cards;

import model.enums.Color;

import util.helpers.StringUtils;

/**
 * Represents a Number Card in the Uno game.
 * Number Cards have a color and a numerical value.
 */
public class NumberCard extends Card {

    /**
     * Constructs a Number Card with the specified color and value.
     *
     * @param color The color of the card.
     * @param value The numerical value of the card.
     * @throws IllegalArgumentException if the value is not between 0 and 9.
     */
    public NumberCard(Color color, int value) {
        super(color, value, value, "images/cards/" + StringUtils.capitalize(color.toString()) + "_" + value + ".jpg");
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Number card value must be between 0 and 9");
        }
    }
    
    /**
     * Checks if this Number Card is playable on another card.
     * Number Cards can be played on cards of the same color or the same value.
     *
     * @param otherCard The card to check if this Number Card is playable on.
     * @return True if this Number Card is playable on the other card, false otherwise.
     */
    @Override
    public boolean isPlayableOn(Card otherCard) {
        if (otherCard instanceof NumberCard) {
            NumberCard otherNumberCard = (NumberCard) otherCard;
            return this.getColor() == otherNumberCard.getColor() || this.getValue() == otherNumberCard.getValue();
        } else if (otherCard instanceof ActionCard || otherCard instanceof WildCard) {
            return this.getColor() == otherCard.getColor();
        }
        return true;
    }
}
