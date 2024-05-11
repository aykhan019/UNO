package util.ui;

import java.awt.GridBagConstraints;

public class GameTableLayoutHelper {

	public final static int rows = 4;
	public final static int columns = 5;

	public static GridBagConstraints[][] generateLayout(int numberOfPlayers) {
		GridBagConstraints[][] gbcArray = new GridBagConstraints[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;

				gbc.gridwidth = 1;
				gbc.gridheight = 1;

				if (numberOfPlayers == 10) { // 10 PLAYERS
					if (i == 1 && j == 1) {
						gbc.gridwidth = 3; // colspan
						gbc.gridheight = 2; // rowspan
					} else if (i == 3 && j == 0) {
						gbc.gridwidth = 5; // colspan
						gbc.gridheight = 1; // rowspan
					}
				}
				if (numberOfPlayers > 3 && numberOfPlayers < 10) { // 4,5,6,7,8, 9 PLAYERS
					if (i == 1 && j == 1) {
						gbc.gridwidth = 3; // colspan
						gbc.gridheight = 2; // rowspan
					}
				}
				if (numberOfPlayers == 3) { // 3 PLAYERS
					if (i == 1 && j == 0) {
						gbc.gridwidth = 5; // colspan
						gbc.gridheight = 2; // rowspan
					} else if (i == 3 && j == 0) {
						gbc.gridwidth = 5; // colspan
						gbc.gridheight = 1; // rowspan
					}
				}
				if (numberOfPlayers == 2) { // 2 PLAYERS
					if (i == 1 && j == 0) {
						gbc.gridwidth = 5; // colspan
						gbc.gridheight = 2; // rowspan
					}
				}

				// Player Panel
				if (i == 3 && j == 0) {
					gbc.gridwidth = 5; // colspan
					gbc.gridheight = 1; // rowspan
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
