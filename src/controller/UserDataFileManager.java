package controller;

import java.io.*;
import java.util.*;
import model.User;
import util.constants.FileConstants;

public class UserDataFileManager {
    public static List<User> readUserData() throws IOException {
        List<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileConstants.USER_DATA_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                userList.add(user);
            }
        }
        return userList;
    }

    public static void writeUserData(List<User> userList) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileConstants.USER_DATA_FILE_PATH))) {
            for (User user : userList) {
                String userDataString = serializeUser(user);
                writer.write(userDataString);
                writer.newLine();
            }
        }
    }

    private static User parseUser(String userDataString) {
        String[] parts = userDataString.split(FileConstants.USER_DATA_SEPARATOR);
        String username = parts[0];
        String email = parts[1];
        String password = parts[2];
        
        return new User(username, email, password);
    }

    private static String serializeUser(User user) {
        return user.getUsername() + FileConstants.USER_DATA_SEPARATOR + user.getEmail() + FileConstants.USER_DATA_SEPARATOR + user.getPassword();
    }
}
