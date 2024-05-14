package model.user;

import java.util.UUID;

/**
 * Represents statistics related to a user's gaming activity.
 */
public class UserStatistic {
	/**
	 * The unique identifier of the user statistic.
	 */
	private String id;

	/**
	 * The identifier of the user associated with this statistic.
	 */
	private String userId;

	/**
	 * The number of games played by the user.
	 */
	private int numberOfGamesPlayed;

	/**
	 * The number of games won by the user.
	 */
	private int numberOfWins;

	/**
	 * The total score accumulated by the user.
	 */
	private int totalScore;

	/**
	 * Constructs a UserStatistic object with specified parameters.
	 * 
	 * @param id                  The unique identifier of the user statistic.
	 * @param userId              The identifier of the user associated with this
	 *                            statistic.
	 * @param numberOfGamesPlayed The number of games played by the user.
	 * @param numberOfWins        The number of games won by the user.
	 * @param totalScore          The total score accumulated by the user.
	 */
	public UserStatistic(String id, String userId, int numberOfGamesPlayed, int numberOfWins, int totalScore) {
		setId(id);
		setUserId(userId);
		setNumberOfGamesPlayed(numberOfGamesPlayed);
		setNumberOfWins(numberOfWins);
		setTotalScore(totalScore);
	}

	/**
	 * Constructs a UserStatistic object with a randomly generated ID and the
	 * specified user ID, initializing other statistics to zero.
	 * 
	 * @param userId The identifier of the user associated with this statistic.
	 */
	public UserStatistic(String userId) {
		id = UUID.randomUUID().toString();
		setUserId(userId);
		setNumberOfGamesPlayed(0);
		setNumberOfWins(0);
		setTotalScore(0);
	}

	/**
	 * Constructs a UserStatistic object with a randomly generated ID and
	 * initializes other statistics to zero.
	 */
	public UserStatistic() {
		id = UUID.randomUUID().toString();
		setUserId(userId);
		setNumberOfGamesPlayed(0);
		setNumberOfWins(0);
		setTotalScore(0);
	}

	/**
	 * Gets the ID of the user statistic.
	 * 
	 * @return The ID of the user statistic.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the user statistic.
	 * 
	 * @param id The ID of the user statistic.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the user ID associated with this statistic.
	 * 
	 * @return The user ID associated with this statistic.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user ID associated with this statistic.
	 * 
	 * @param userId The user ID associated with this statistic.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the number of games played by the user.
	 * 
	 * @return The number of games played by the user.
	 */
	public int getNumberOfGamesPlayed() {
		return numberOfGamesPlayed;
	}

	/**
	 * Sets the number of games played by the user.
	 * 
	 * @param numberOfGamesPlayed The number of games played by the user.
	 */
	public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
		this.numberOfGamesPlayed = numberOfGamesPlayed;
	}

	/**
	 * Gets the total score accumulated by the user.
	 * 
	 * @return The total score accumulated by the user.
	 */
	public int getTotalScore() {
		return totalScore;
	}

	/**
	 * Sets the total score accumulated by the user.
	 * 
	 * @param totalScore The total score accumulated by the user.
	 */
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * Sets the number of wins by the user.
	 * 
	 * @param numberOfWins The number of wins by the user.
	 */
	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	/**
	 * Gets the number of wins by the user.
	 * 
	 * @return The number of wins by the user.
	 */
	public int getNumberOfWins() {
		return numberOfWins;
	}

	/**
	 * Calculates the number of losses by subtracting the number of wins from the
	 * total number of games played.
	 * 
	 * @return The number of losses by the user.
	 */
	public int getNumberOfLosses() {
		return numberOfGamesPlayed - numberOfWins;
	}

	/**
	 * Calculates the average score per game played by the user.
	 * 
	 * @return The average score per game played by the user.
	 */
	public double getAverageScore() {
		return numberOfGamesPlayed == 0 ? 0 : (double) totalScore / numberOfGamesPlayed;
	}

	/**
	 * Calculates the win-loss ratio of the user.
	 * 
	 * @return The win-loss ratio of the user.
	 */
	public double getWinLossRatio() {
		if (numberOfGamesPlayed == 0) {
			return 0;
		}
		return (double) numberOfWins / (numberOfGamesPlayed - numberOfWins);
	}
}
