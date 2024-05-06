package data;

import java.io.*;
import java.util.*;
import model.User;
import util.constants.FileConstants;

/**
 * Manages user data storage and retrieval operations. This class provides
 * methods for reading user data from a file, updating user data, and performing
 * user-related queries.
 */
public class UserRepository {

	/**
	 * Retrieves a list of all users from the user data file.
	 *
	 * @return A list of users.
	 * @throws IOException If an I/O error occurs while reading the user data file.
	 */
	public static List<User> getUsers() throws IOException {
		List<User> userList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FileConstants.USER_DATA_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] userData = line.split(FileConstants.USER_DATA_SEPARATOR);
				User user = new User();
				user.setId(userData[0]);
				user.setUsername(userData[1]);
				user.setEmail(userData[2]);
				user.setPassword(userData[3]);
				userList.add(user);
			}
		}
		return userList;
	}

	/**
	 * Updates the user data file with the provided list of users.
	 *
	 * @param userList The list of users to be written to the user data file.
	 * @throws IOException If an I/O error occurs while writing to the user data
	 *                     file.
	 */
	public static void updateUsers(List<User> userList) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileConstants.USER_DATA_FILE_PATH))) {
			for (User user : userList) {
				String userDataString = serializeUser(user);
				writer.write(userDataString);
				writer.newLine();
			}
		}
	}

	/**
	 * Adds a new user to the user data file.
	 *
	 * @param user The user to be added.
	 * @throws IOException If an I/O error occurs while adding the user to the user
	 *                     data file.
	 */
	public static void addUser(User user) throws IOException {
		var users = getUsers();
		users.add(user);
		updateUsers(users);
	}

	/**
	 * Checks if a username is already taken by an existing user.
	 *
	 * @param username The username to check.
	 * @return true if the username is taken, false otherwise.
	 * @throws IOException If an I/O error occurs while checking the username.
	 */
	public static boolean usernameTaken(String username) throws IOException {
		var users = getUsers();
		for (var user : users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if an email address already exists in the user data file.
	 *
	 * @param email The email address to check.
	 * @return true if the email address exists, false otherwise.
	 * @throws IOException If an I/O error occurs while checking the email address.
	 */
	public static boolean emailExists(String email) throws IOException {
		var users = getUsers();
		for (var user : users) {
			if (user.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Serializes a user object into a string representation.
	 *
	 * @param user The user object to serialize.
	 * @return A string representation of the user object.
	 */
	private static String serializeUser(User user) {
		return user.getId() + FileConstants.USER_DATA_SEPARATOR + user.getUsername() + FileConstants.USER_DATA_SEPARATOR
				+ user.getEmail() + FileConstants.USER_DATA_SEPARATOR + user.getPassword();
	}

	/**
	 * Retrieves a user by their unique identifier (ID).
	 *
	 * @param userId The ID of the user to retrieve.
	 * @return The user object corresponding to the given ID, or null if no such
	 *         user exists.
	 * @throws IOException If an I/O error occurs while retrieving the user data.
	 */
	public static User getUserById(String userId) throws IOException {
		var users = getUsers();
		for (var user : users) {
			if (user.getId().equals(userId)) {
				return user;
			}
		}
		return null;
	}
}
