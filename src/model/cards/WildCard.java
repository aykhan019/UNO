package model.cards;

import model.enums.Color;
import model.enums.WildType;
import util.helpers.StringUtils;

public class WildCard extends Card {
	WildType wildType;

	public WildCard(WildType wildType) {
		super(Color.NONE, -1, 50, "images/card/" + StringUtils.capitalize(wildType.toString()) + ".jpg");
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
