package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameSession;
import util.constants.WindowConstants;
import util.ui.GameTableLayoutGenerator;

public class GameTable extends BaseFrame {

	private GameSession gameSession;
	private JPanel[][] cells;
	private int numberOfPlayers;

	public GameTable(int numberOfPlayers) {
		super(WindowConstants.GAME_TABLE_WINDOW);
		gameSession = new GameSession("");
		this.numberOfPlayers = numberOfPlayers;
		initializeFrame();
	}

	@Override
	void initializeFrame() {
		int rows = GameTableLayoutGenerator.rows;
		int columns = GameTableLayoutGenerator.columns;

		setLayout(new GridBagLayout());

		GridBagConstraints[][] gbcArray = GameTableLayoutGenerator.generateLayout(numberOfPlayers);

		cells = new JPanel[rows][columns];

		Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA,
				Color.PINK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.WHITE, Color.BLACK };

		int colorIndex = 0;

		// Create cells with different colors
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new JPanel();
				cells[i][j].setBackground(colors[colorIndex]);
				cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

				// Set grid constraints
				GridBagConstraints gbc = gbcArray[i][j];

				// Add cell to layout
				add(cells[i][j], gbc);

				// Update color index
				colorIndex = (colorIndex + 1) % colors.length;
			}
		}

		getCell(0, 0).setBackground(Color.black);
		getCell(0, 4).setBackground(Color.black);

		setVisible(true);
	}

	public JPanel getCell(int row, int column) {
		return cells[row][column];
	}
}
