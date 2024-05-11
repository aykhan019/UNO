package model.cards;

import java.awt.Image;

import javax.swing.ImageIcon;

import model.cards.ActionCard;
import model.cards.NumberCard;
import model.cards.WildCard;
import model.enums.Color;
import util.constants.ImagePath;

public abstract class Card {
	private Color color;
	private int value;
	private int score;
	private String imagePath; // New attribute to store the image path

	public Card(Color color, int value, int score, String imagePath) {
		this.color = color;
		this.value = value;
		this.score = score;
		this.imagePath = imagePath; // Initialize the image path
	}

	public void setColor(Color color) {
		this.color = color;
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

	public String getImagePath() {
		return imagePath; // Method to retrieve the image path
	}

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

	public abstract boolean isPlayableOn(Card otherCard);

	public static ImageIcon getDefaultCardImage(int width, int height) {
		ImageIcon icon = new ImageIcon(ImagePath.DEFAULT_CARD_IMAGE_PATH);

		Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		return new ImageIcon(scaledImage);
	}
}
