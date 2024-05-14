package util.helpers;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Helper class for file handling operations.
 */
public class FileHandler {

	/**
	 * Writes data to a file.
	 * 
	 * @param fileName the name of the file to write to
	 * @param data     the data to write
	 * @param append   if true, then bytes will be written to the end of the file
	 *                 rather than the beginning
	 * @throws IOException if an I/O error occurs while writing to the file
	 */
	public static void writeFile(String fileName, String data, boolean append) throws IOException {
		try (FileWriter writer = new FileWriter(fileName, append)) {
			writer.write(data);
			writer.write(System.lineSeparator()); 
		}
	}
}
