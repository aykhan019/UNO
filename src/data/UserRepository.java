package data;

import java.io.*;
import java.util.*;
import model.User;
import util.constants.FileConstants;

public class UserRepository {
	
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

    public static void updateUsers(List<User> userList) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileConstants.USER_DATA_FILE_PATH))) {
            for (User user : userList) {
                String userDataString = serializeUser(user);
                writer.write(userDataString);
                writer.newLine();
            }
        }
    }

    public static void addUser(User user) throws IOException {
    	var users = getUsers();
    	users.add(user);
    	updateUsers(users);
    }
    
    public static boolean usernameTaken(String username) throws IOException {
    	var users = getUsers();
    	for (var user : users) {
    		if (user.getUsername().equals(username)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean emailExists(String email) throws IOException {
    	var users = getUsers();
    	for (var user : users) {
    		if (user.getEmail().equals(email)) {
    			return true;
    		}
    	}
    	return false;
    }

    private static String serializeUser(User user) {
        return user.getId() + FileConstants.USER_DATA_SEPARATOR 
        	 + user.getUsername() + FileConstants.USER_DATA_SEPARATOR
        	 + user.getEmail() + FileConstants.USER_DATA_SEPARATOR
        	 + user.getPassword();
    }
}
