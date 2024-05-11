package model.cards;

import model.enums.Color;
import util.helpers.StringUtils;

public class NumberCard extends Card {
    public NumberCard(Color color, int value) {
        super(color, value, value, "images/card/" + StringUtils.capitalize(color.toString()) + "_" + value + ".jpg");
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Number card value must be between 0 and 9");
        }
    }
    
    @Override
    public boolean isPlayableOn(Card otherCard) {
    	  if (otherCard instanceof NumberCard) {
              // A number card is playable on another number card if they have the same color or value
              NumberCard otherNumberCard = (NumberCard) otherCard;
              return this.getColor() == otherNumberCard.getColor() || this.getValue() == otherNumberCard.getValue();
          } else if (otherCard instanceof ActionCard || otherCard instanceof WildCard) {
				// Number cards can be played on action cards or wild cards
				return true;
          }	
          return false;
    }
}
