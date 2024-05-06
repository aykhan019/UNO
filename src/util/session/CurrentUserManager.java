package util.session;

import model.User;
import java.io.*;

/**
 * Manages the current user session by saving and loading user data.
 */
public class CurrentUserManager {
    /** The file path to store user data. */
    private static final String USER_DATA_FILE = "session/userData.ser";

    /** The singleton instance of CurrentUserManager. */
    private static CurrentUserManager instance;

    /** The currently logged-in user. */
    private User currentUser;

    /** Private constructor to prevent instantiation from outside. */
    private CurrentUserManager() {
    }

    /**
     * Gets the singleton instance of CurrentUserManager.
     *
     * @return The instance of CurrentUserManager.
     */
    public static CurrentUserManager getInstance() {
        if (instance == null) {
            instance = new CurrentUserManager();
        }
        return instance;
    }

    /**
     * Retrieves the current logged-in user.
     *
     * @return The current logged-in user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current logged-in user and saves their data.
     *
     * @param currentUser The current logged-in user.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        saveUserData(currentUser);
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Saves the user data to a file.
     *
     * @param user The user whose data is to be saved.
     */
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

    /**
     * Loads user data from the file.
     */
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
