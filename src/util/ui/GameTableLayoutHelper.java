package util.ui;

import java.awt.GridBagConstraints;

/**
 * Helper class for generating grid layout constraints for a game table.
 */
public class GameTableLayoutHelper {
	/**
	 * Number of rows in the grid layout.
	 */
	public final static int rows = 4;

	/**
	 * Number of columns in the grid layout.
	 */
	public final static int columns = 5;

	/**
	 * Generates grid layout constraints based on the number of players.
	 * 
	 * @param numberOfPlayers the number of players in the game
	 * @return a 2D array of GridBagConstraints representing the layout constraints
	 */
	public static GridBagConstraints[][] generateLayout(int numberOfPlayers) {
		GridBagConstraints[][] gbcArray = new GridBagConstraints[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;

				gbc.gridwidth = 1;
				gbc.gridheight = 1;

				if (numberOfPlayers == 10) {
					if (i == 1 && j == 1) {
						gbc.gridwidth = 3;
						gbc.gridheight = 2;
					} else if (i == 3 && j == 0) {
						gbc.gridwidth = 5;
						gbc.gridheight = 1;
					}
				}
				if (numberOfPlayers > 3 && numberOfPlayers < 10) {
					if (i == 1 && j == 1) {
						gbc.gridwidth = 3;
						gbc.gridheight = 2;
					}
				}
				if (numberOfPlayers == 3) {
					if (i == 1 && j == 0) {
						gbc.gridwidth = 5;
						gbc.gridheight = 2;
					} else if (i == 3 && j == 0) {
						gbc.gridwidth = 5;
						gbc.gridheight = 1;
					}
				}
				if (numberOfPlayers == 2) {
					if (i == 1 && j == 0) {
						gbc.gridwidth = 5;
						gbc.gridheight = 2;
					}
				}

				if (i == 3 && j == 0) {
					gbc.gridwidth = 5;
					gbc.gridheight = 1;
				}

				gbc.weightx = 1.0 / columns;
				gbc.weighty = 1.0 / rows;
				gbc.gridx = j;
				gbc.gridy = i;

				gbcArray[i][j] = gbc;
			}
		}
		return gbcArray;
	}

	/**
	 * Gets the grid cells occupied by the player panels.
	 * 
	 * @param numberOfPlayers the number of players in the game
	 * @return a 2D array representing the grid cells occupied by player panels
	 */
	public static int[][] getPlayerCells(int numberOfPlayers) {

		switch (numberOfPlayers) {
		case 2:
			return new int[][] { { 3, 0 }, { 0, 2 } };
		case 3:
			return new int[][] { { 3, 0 }, { 0, 1 }, { 0, 3 } };
		case 4:
			return new int[][] { { 3, 0 }, { 1, 0 }, { 0, 2 }, { 1, 4 } };
		case 5:
			return new int[][] { { 3, 0 }, { 1, 0 }, { 0, 1 }, { 0, 3 }, { 1, 4 } };
		case 6:
			return new int[][] { { 3, 0 }, { 1, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 } };
		case 7:
			return new int[][] { { 3, 0 }, { 2, 0 }, { 1, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 }, };
		case 8:
			return new int[][] { { 3, 0 }, { 2, 0 }, { 1, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 }, { 2, 4 } };
		case 9:
			return new int[][] { { 3, 0 }, { 2, 0 }, { 1, 0 }, { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 },
					{ 2, 4 } };
		case 10:
			return new int[][] { { 3, 0 }, { 2, 0 }, { 1, 0 }, { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 },
					{ 1, 4 }, { 2, 4 } };
		default:
			break;
		}
		return null;
	}
}
