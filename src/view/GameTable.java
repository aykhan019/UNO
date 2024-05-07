package view;

import model.GameSession;
import util.constants.WindowConstants;

public class GameTable extends BaseFrame {
	
	private GameSession gameSession;
	
	public GameTable(int numberOfPlayers) {
		super(WindowConstants.GAME_TABLE_WINDOW);
		// TODO Auto-generated constructor stub
		System.out.println(numberOfPlayers);
	}

	@Override
	void initializeFrame() {
		// TODO Auto-generated method stub
	}	
}
