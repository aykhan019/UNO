package util.ui;

import java.awt.GridBagConstraints;

public class GameTableLayoutGenerator {

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
}

//package util.ui;
//
//import java.awt.GridBagConstraints;
//
//public class GameTableLayoutGenerator {
//
//	private int rows = 4;
//	private int columns = 5;
//
//	public static GridBagConstraints[][] generateLayout(int numberOfPlayers) {
//		GridBagConstraints[][] gbcArray = null;
//
//		switch (numberOfPlayers) {
//		case 2:
//			break;
//		case 3:
//			break;
//		case 4:
//			break;
//		case 5:
//			break;
//		case 6:
//			break;
//		case 7:
//			break;
//		case 8:
//			break;
//		case 9:
//			break;
//		case 10:
//			break;
//		default:
//			// Value is invalid
//			break;
//		}
//		gbcArray = getGbcArray10Players();
//
//		return gbcArray;
//	}
//
//	private static GridBagConstraints[][] getGbcArray10Players() {
//		GridBagConstraints[][] gbcArray = new GridBagConstraints[rows][columns];
//
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < columns; j++) {
//				GridBagConstraints gbc = new GridBagConstraints();
//				gbc.fill = GridBagConstraints.BOTH;
//
//				// Default grid constraints
//				gbc.gridwidth = 1;
//				gbc.gridheight = 1;
//
//				if (numberOfPlayers == 10) {
//					// Adjust grid constraints based on cell position for 10 players
//					if (i == 1 && j == 1) {
//						gbc.gridwidth = 3; // colspan
//						gbc.gridheight = 2; // rowspan
//					} else if (i == 3 && j == 0) {
//						gbc.gridwidth = 5; // colspan
//						gbc.gridheight = 1; // rowspan
//					}
//				}
//
//				// Set grid constraints to fill the available space
//				gbc.weightx = 1.0 / columns;
//				gbc.weighty = 1.0 / rows;
//				gbc.gridx = j;
//				gbc.gridy = i;
//
//				gbcArray[i][j] = gbc;
//			}
//		}
//	}
//}
