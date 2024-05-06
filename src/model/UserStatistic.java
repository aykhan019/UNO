package model;

import java.util.UUID;

public class UserStatistic {
	private String id;
	private String userId;
	private int numberOfGamesPlayed;
	private int numberOfWins;
	private int totalScore;

	public UserStatistic(String id, String userId, int numberOfGamesPlayed, int numberOfWins, int totalScore) {
		setId(id);
		setUserId(userId);
		setNumberOfGamesPlayed(numberOfGamesPlayed);
		setNumberOfWins(numberOfWins);
		setTotalScore(totalScore);
	}

	public UserStatistic(String userId) {
		id = UUID.randomUUID().toString();
		setUserId(userId);
		setNumberOfGamesPlayed(0);
		setNumberOfWins(0);
		setTotalScore(0);
	}

	public UserStatistic() {
		id = UUID.randomUUID().toString();
		setUserId(userId);
		setNumberOfGamesPlayed(0);
		setNumberOfWins(0);
		setTotalScore(0);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getNumberOfGamesPlayed() {
		return numberOfGamesPlayed;
	}

	public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
		this.numberOfGamesPlayed = numberOfGamesPlayed;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	public int getNumberOfWins() {
		return numberOfWins;
	}

	public int getNumberOfLosses() {
		return numberOfGamesPlayed - numberOfWins;
	}

	public double getAverageScore() {
		return numberOfGamesPlayed == 0 ? 0 : (double) totalScore / numberOfGamesPlayed;
	}

	public double getWinLossRatio() {
		if (numberOfGamesPlayed == 0) {
			return 0;
		}

		return (double) numberOfWins / (numberOfGamesPlayed - numberOfWins);
	}
}
