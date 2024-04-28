package model;

public class User {
	private String username;
	private String email;
	private String password;
	
	public User(String username, String email, String password) {
		setUsername(username);
		setEmail(email);
		setPassword(password);
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
}
