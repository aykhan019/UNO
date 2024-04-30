package uno;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import data.UserRepository;
import data.UserStatisticRepository;
import model.UserStatistic;
import view.LoginPageView;

public class Main {
	public static void main(String[] args) {
		new LoginPageView();
		
		System.out.println("Program ended!");
	}
}


/*
 * // RANDOM USER STATISTIC DATA GENERATOR try { var users = UserRepository.getUsers();
 * 
 * var lst = new ArrayList<UserStatistic>(); for (var user:users) { var id =
 * UUID.randomUUID().toString(); var userId = user.getId(); int
 * numberOfGamesPlayed = (int) ((0.2 + Math.random() * 0.7) * 100); int
 * numberOfWins = (int) ((0.22 + Math.random() * 0.7) * numberOfGamesPlayed);
 * int totalScore = (int) ((0.22 + Math.random() * 0.7) * numberOfWins * 100);
 * var user_stat = new UserStatistic(id, userId, numberOfGamesPlayed,
 * numberOfWins, totalScore); lst.add(user_stat); }
 * 
 * UserStatisticRepository.updateUserStatistics(lst);
 * 
 * 
 * 
 * } catch (IOException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 */