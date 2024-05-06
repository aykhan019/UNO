package data;

import java.io.*;
import java.util.*;
import model.UserStatistic;
import util.constants.FileConstants;

/**
 * Manages user statistics data storage and retrieval operations. This class
 * provides methods for reading user statistics data from a file, updating user
 * statistics data, and performing user statistic-related queries.
 */
public class UserStatisticRepository {

	/**
	 * Retrieves a list of all user statistics from the user statistics data file.
	 *
	 * @return A list of user statistics.
	 * @throws IOException If an I/O error occurs while reading the user statistics
	 *                     data file.
	 */
	public static List<UserStatistic> getUserStatistics() throws IOException {
		List<UserStatistic> userStatisticsList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FileConstants.USER_STATISTIC_DATA_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] userStatData = line.split(FileConstants.USER_STATISTIC_DATA_SEPARATOR);
				UserStatistic userStatistic = new UserStatistic(userStatData[0], userStatData[1],
						Integer.parseInt(userStatData[2]), Integer.parseInt(userStatData[3]),
						Integer.parseInt(userStatData[4]));
				userStatisticsList.add(userStatistic);
			}
		}
		return userStatisticsList;
	}

	/**
	 * Updates the user statistics data file with the provided list of user
	 * statistics.
	 *
	 * @param userStatisticsList The list of user statistics to be written to the
	 *                           user statistics data file.
	 * @throws IOException If an I/O error occurs while writing to the user
	 *                     statistics data file.
	 */
	public static void updateUserStatistics(List<UserStatistic> userStatisticsList) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileConstants.USER_STATISTIC_DATA_FILE_PATH))) {
			for (UserStatistic userStatistic : userStatisticsList) {
				String userStatDataString = serializeUserStatistic(userStatistic);
				writer.write(userStatDataString);
				writer.newLine();
			}
		}
	}

	/**
	 * Adds a new user statistic to the user statistics data file.
	 *
	 * @param userStatistic The user statistic to be added.
	 * @throws IOException If an I/O error occurs while adding the user statistic to
	 *                     the user statistics data file.
	 */
	public static void addUserStatistic(UserStatistic userStatistic) throws IOException {
		var userStats = getUserStatistics();
		userStats.add(userStatistic);
		updateUserStatistics(userStats);
	}

	/**
	 * Serializes a user statistic object into a string representation.
	 *
	 * @param userStatistic The user statistic object to serialize.
	 * @return A string representation of the user statistic object.
	 */
	private static String serializeUserStatistic(UserStatistic userStatistic) {
		return userStatistic.getId() + FileConstants.USER_STATISTIC_DATA_SEPARATOR + userStatistic.getUserId()
				+ FileConstants.USER_STATISTIC_DATA_SEPARATOR + userStatistic.getNumberOfGamesPlayed()
				+ FileConstants.USER_STATISTIC_DATA_SEPARATOR + userStatistic.getNumberOfWins()
				+ FileConstants.USER_STATISTIC_DATA_SEPARATOR + userStatistic.getTotalScore();
	}

	/**
	 * Retrieves a user statistic by the user's unique identifier (ID).
	 *
	 * @param userId The ID of the user for whom to retrieve the statistic.
	 * @return The user statistic object corresponding to the given user ID, or null
	 *         if no such statistic exists.
	 * @throws IOException If an I/O error occurs while retrieving the user
	 *                     statistics data.
	 */
	public static UserStatistic getUserStatisticById(String userId) throws IOException {
		var userStatistics = getUserStatistics();
		for (var userStatistic : userStatistics) {
			if (userStatistic.getUserId().equals(userId)) {
				return userStatistic;
			}
		}
		return null;
	}
}
