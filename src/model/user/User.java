package model.user;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a user in the system.
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	/**
	 * The unique identifier for the user.
	 */
	private String id;

	/**
	 * The username chosen by the user.
	 */
	private String username;

	/**
	 * The email address of the user.
	 */
	private String email;

	/**
	 * The password chosen by the user.
	 */
	private String password;

	/**
	 * Constructs a new user with default values.
	 */
	public User() {
	}

	/**
	 * Constructs a new user with the specified username, email, and password.
	 *
	 * @param username The username of the user.
	 * @param email    The email address of the user.
	 * @param password The password of the user.
	 */
	public User(String username, String email, String password) {
		id = UUID.randomUUID().toString();
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Constructs a new user with the specified ID, username, email, and password.
	 *
	 * @param id       The ID of the user.
	 * @param username The username of the user.
	 * @param email    The email address of the user.
	 * @param password The password of the user.
	 */
	public User(String id, String username, String email, String password) {
		setId(id);
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Retrieves the ID of the user.
	 *
	 * @return The ID of the user.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the user.
	 *
	 * @param id The ID of the user.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves the username of the user.
	 *
	 * @return The username of the user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the user.
	 *
	 * @param name The username of the user.
	 */
	public void setUsername(String name) {
		this.username = name;
	}

	/**
	 * Retrieves the email address of the user.
	 *
	 * @return The email address of the user.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the user.
	 *
	 * @param email The email address of the user.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retrieves the password of the user.
	 *
	 * @return The password of the user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password The password of the user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns a string representation of the user.
	 *
	 * @return A string representation of the user.
	 */
	@Override
	public String toString() {
		return "User{" + "id='" + id + '\'' + ", username='" + username + '\'' + ", email='" + email + '\''
				+ ", password='" + password + '\'' + '}';
	}
}
