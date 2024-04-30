package data;

import java.io.*;
import java.util.*;
import model.UserStatistic;
import util.constants.FileConstants;

public class UserStatisticRepository {

    public static List<UserStatistic> getUserStatistics() throws IOException {
        List<UserStatistic> userStatisticsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileConstants.USER_STATISTIC_DATA_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userStatData = line.split(FileConstants.USER_STATISTIC_DATA_SEPARATOR);
                UserStatistic userStatistic = new UserStatistic(
                    userStatData[0], 
                    userStatData[1], 
                    Integer.parseInt(userStatData[2]), 
                    Integer.parseInt(userStatData[3]), 
                    Integer.parseInt(userStatData[4])
                );
                userStatisticsList.add(userStatistic);
            }
        }
        return userStatisticsList;
    }

    public static void updateUserStatistics(List<UserStatistic> userStatisticsList) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileConstants.USER_STATISTIC_DATA_FILE_PATH))) {
            for (UserStatistic userStatistic : userStatisticsList) {
                String userStatDataString = serializeUserStatistic(userStatistic);
                writer.write(userStatDataString);
                writer.newLine();
            }
        }
    }

    public static void addUserStatistic(UserStatistic userStatistic) throws IOException {
        var userStats = getUserStatistics();
        userStats.add(userStatistic);
        updateUserStatistics(userStats);
    }

    private static String serializeUserStatistic(UserStatistic userStatistic) {
        return userStatistic.getId() + FileConstants.USER_STATISTIC_DATA_SEPARATOR 
             + userStatistic.getUserId() + FileConstants.USER_STATISTIC_DATA_SEPARATOR
             + userStatistic.getNumberOfGamesPlayed() + FileConstants.USER_STATISTIC_DATA_SEPARATOR
             + userStatistic.getNumberOfWins() + FileConstants.USER_STATISTIC_DATA_SEPARATOR
             + userStatistic.getTotalScore();
    }
}
