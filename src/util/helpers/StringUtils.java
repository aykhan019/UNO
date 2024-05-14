package util.helpers;

/**
 * Helper class for string manipulation operations.
 */
public class StringUtils {
	/**
	 * Capitalizes the first letter of a string.
	 * 
	 * @param str the string to capitalize
	 * @return the capitalized string
	 */
	public static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
