package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Url {
	public static String getUrlContents(String theUrl) {
		StringBuilder content = new StringBuilder();
		
		try {
			URL url = new URL(theUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
}
