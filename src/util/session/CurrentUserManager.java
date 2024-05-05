package util.session;

import model.User;
import java.io.*;

public class CurrentUserManager {
	private static final String USER_DATA_FILE = "session/userData.ser";
	private static CurrentUserManager instance;
	private User currentUser;

	private CurrentUserManager() {
		// Private constructor to prevent instantiation from outside
	}

	public static CurrentUserManager getInstance() {
		if (instance == null) {
			instance = new CurrentUserManager();
		}
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		saveUserData(currentUser);
	}

	public boolean isLoggedIn() {
		return currentUser != null;
	}

	public void saveUserData(User user) {
		File file = new File(USER_DATA_FILE);
		if (!file.exists()) {
			return;
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadUserData() {
		File file = new File(USER_DATA_FILE);
		if (!file.exists() || file.length() == 0) {
			return;
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			currentUser = (User) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
