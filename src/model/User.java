package model;

import java.util.UUID;

public class User {
	private String id;
	private String username;
	private String email;
	private String password;
	
	public User() {
		
	}
	
	public User(String username, String email, String password) {
		generateId();
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
	
	public void generateId() {
		id = UUID.randomUUID().toString();
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
         return "User{" +
                 "id='" + id + '\'' +
                 ", username='" + username + '\'' +
                 ", email='" + email + '\'' +
                 ", password='" + password + '\'' +
                 '}';
     }
}
