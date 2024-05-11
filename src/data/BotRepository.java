package data;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.cards.Card;
import model.player.Bot;
import model.user.User;
import util.constants.FileConstants;

/**
 * Manages bot data retrieval operation. This class provides methods for reading
 * user data from a file.
 */
public class BotRepository {
	/**
	 * Retrieves a list of all bots from the bot data file.
	 *
	 * @return A list of bots.
	 * @throws IOException If an I/O error occurs while reading the bot data file.
	 */
	public static List<Bot> getBots() throws IOException {
		List<Bot> botList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FileConstants.BOT_DATA_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] userData = line.split(FileConstants.BOT_DATA_SEPARATOR);
				User user = new User();
				user.setId(userData[0]);
				user.setUsername(userData[1]);
				user.setEmail(userData[2]);
				user.setPassword(userData[3]);
				botList.add(new Bot(user, new ArrayList<Card>()));
			}
		}
		return botList;
	}
}
