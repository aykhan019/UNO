package model.cards;

import java.awt.Image;

import javax.swing.ImageIcon;

import model.enums.Color;
import util.constants.ImagePath;

/**
 * Represents a card in the Uno game.
 */
public abstract class Card {

	private Color color; // The color of the card
	private int value; // The value of the card (for Number Cards)
	private int score; // The score of the card
	private String imagePath; // The path to the image associated with the card

	/**
	 * Constructs a Card with the specified color, value, score, and image path.
	 *
	 * @param color     The color of the card.
	 * @param value     The value of the card (for Number Cards). Use -1 for Action
	 *                  Cards and Wild Cards.
	 * @param score     The score of the card.
	 * @param imagePath The path to the image associated with the card.
	 */
	public Card(Color color, int value, int score, String imagePath) {
		this.color = color;
		this.value = value;
		this.score = score;
		this.imagePath = imagePath;
	}

	/**
	 * Sets the color of the card.
	 *
	 * @param color The color to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Gets the color of the card.
	 *
	 * @return The color of the card.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Gets the value of the card.
	 *
	 * @return The value of the card.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Gets the score of the card.
	 *
	 * @return The score of the card.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Gets the path to the image associated with the card.
	 *
	 * @return The path to the image associated with the card.
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Gets the name of the card.
	 *
	 * @return The name of the card.
	 */
	public String getName() {
		if (this instanceof NumberCard) {
			return color.toString() + " " + value;
		} else if (this instanceof ActionCard) {
			return color.toString() + " " + ((ActionCard) this).getAction().toString();
		} else if (this instanceof WildCard) {
			return ((WildCard) this).getWildType().toString();
		} else {
			return "Unknown Card";
		}
	}

	/**
	 * Checks if this card is playable on another card.
	 *
	 * @param otherCard The card to check if this card is playable on.
	 * @return True if this card is playable on the other card, false otherwise.
	 */
	public abstract boolean isPlayableOn(Card otherCard);

	/**
	 * Gets the default image of a card with the specified width and height.
	 *
	 * @param width  The width of the image.
	 * @param height The height of the image.
	 * @return The default image of a card.
	 */
	public static ImageIcon getDefaultCardImage(int width, int height) {
		ImageIcon icon = new ImageIcon(ImagePath.DEFAULT_CARD_IMAGE_PATH);

		Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		return new ImageIcon(scaledImage);
	}
}
