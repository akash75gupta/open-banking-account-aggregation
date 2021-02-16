package io.akash.openbanking.accountaggregator.accountdiscovery.helper.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

/** 
 * This class is used for performing any file related I/O operations.
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-01-02
 * 
 **/

@Component
public class FileUtil {
	public static String fetchTestDataForClass(String fileName, String className) {
		String path = "src/test/resources/" + className.toLowerCase() + "\\" + fileName;
		String data = readFile(path);
		return data;
	}

	private static String readFile(String path) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "File not found at the given location "+path,e);
		}
		
		StringBuilder sb = new StringBuilder();
		String line;

		try {
			line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
		} catch (IOException e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "I/O error while reading from file at "+path, e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error closing the file at "+path, e);
			}
		}

		return sb.toString();
	}
}
