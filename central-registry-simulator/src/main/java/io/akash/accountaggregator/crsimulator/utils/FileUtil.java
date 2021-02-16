package io.akash.accountaggregator.crsimulator.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

	public static String fetchTestDataForClass(String fileName, String className) throws Exception {
		String path = "src/test/resources/" + className.toLowerCase() + "\\" + fileName;

		String data = readFile(path);

		return data;
	}

	private static String readFile(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}
