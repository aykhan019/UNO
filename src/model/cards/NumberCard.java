package model.cards;

import model.enums.Color;

import util.helpers.StringUtils;

public class NumberCard extends Card {
    public NumberCard(Color color, int value) {
        super(color, value, value, "images/cards/" + StringUtils.capitalize(color.toString()) + "_" + value + ".jpg");
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Number card value must be between 0 and 9");
        }
    }
    
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
