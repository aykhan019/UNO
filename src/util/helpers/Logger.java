package util.helpers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class for logging messages to a file.
 */
public class Logger {
	/**
	 * Helper field for formatting dates.
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Logs a message to the specified file path with a timestamp.
	 * 
	 * @param message  the message to log
	 * @param filePath the path of the log file
	 */
	public static void log(String message, String filePath) {
		String timestamp = dateFormat.format(new Date());
		String logMessage = timestamp + " - " + message;

		try {
			FileHandler.writeFile(filePath, logMessage, true);
		} catch (IOException e) {
			System.err.println("Error writing to log file: " + e.getMessage());
		}
	}
}
