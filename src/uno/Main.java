package uno;

import model.User;
import util.session.CurrentUserManager;
import view.LoginPageView;
import view.MainMenu;

public class Main {
	public static void main(String[] args) {
		CurrentUserManager.getInstance().loadUserData();

        if (CurrentUserManager.getInstance().isLoggedIn()) {
            User currentUser = CurrentUserManager.getInstance().getCurrentUser();
            System.out.println("Welcome back, " + currentUser.getUsername() + "!");
            new MainMenu();
        } else {
            System.out.println("No user logged in.");
    		new LoginPageView();
        }
        
		System.out.println("Program ended!");
		
        // Save user data when the application exits
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			CurrentUserManager.getInstance().saveUserData(CurrentUserManager.getInstance().getCurrentUser());
	    }));
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