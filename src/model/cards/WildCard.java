package model.cards;

import model.enums.Color;
import model.enums.WildType;
import util.helpers.StringUtils;

/**
 * Represents a Wild Card in the Uno game.
 * Wild Cards have no specific color but have a special effect associated with them.
 */
public class WildCard extends Card {
    
    private WildType wildType; // The type of special effect associated with the card

    /**
     * Constructs a Wild Card with the specified special effect.
     *
     * @param wildType The type of special effect associated with the card.
     */
    public WildCard(WildType wildType) {
        super(Color.NONE, -1, 50, "images/cards/" + StringUtils.capitalize(wildType.toString()) + ".jpg");
        this.wildType = wildType;
    }

    /**
     * Gets the type of special effect associated with the card.
     *
     * @return The WildType of the card.
     */
    public WildType getWildType() {
        return wildType;
    }

    /**
     * Checks if this Wild Card is playable on another card.
     * Wild Cards can be played on any other card.
     *
     * @param otherCard The card to check if this Wild Card is playable on.
     * @return True if this Wild Card is playable on the other card, false otherwise.
     */
    @Override
    public boolean isPlayableOn(Card otherCard) {
        // Wild cards can be played on any other card
        return true;
    }
}
