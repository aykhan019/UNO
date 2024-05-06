package model;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
public class User implements Serializable {
	private String id;
	private String username;
	private String email;
	private String password;

	public User() {
	}

	public User(String username, String email, String password) {
		id = UUID.randomUUID().toString();
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}

	public User(String id, String username, String email, String password) {
		setId(id);
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" + "id='" + id + '\'' + ", username='" + username + '\'' + ", email='" + email + '\''
				+ ", password='" + password + '\'' + '}';
	}
}
