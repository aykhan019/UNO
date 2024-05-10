package model.cards;

import model.enums.Color;
import model.enums.WildType;

public class WildCard extends Card {
	WildType wildType;

	public WildCard(WildType wildType) {
		super(Color.NONE, -1, 50); // Wild cards have no specific color or value
		this.wildType = wildType;
	}
	
	public WildType getWildType() {
        return wildType;
    }

    @Override
    public boolean isPlayableOn(Card otherCard) {
        // Wild cards can be played on any other card
        return true;
    }
}