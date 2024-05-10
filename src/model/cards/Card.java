package model.cards;

import model.enums.Color;

public abstract class Card {
    private Color color;
    private int value;
    private int score;

    public Card(Color color, int value, int score) {
        this.color = color;
        this.value = value;
        this.score = score;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getScore() {
        return score;
    }

    // Abstract method to check if a card is playable on another card
    public abstract boolean isPlayableOn(Card otherCard);
}
