package main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Reader {
	public static String fileReader(String level) {
		// First, read the lines and store them in the List lines
		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.lines(Paths.get(level+".txt"), Charset.forName("UTF-8")).collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Then get the line count
		int count = lines.size();
		
		//Generate random number within text file's length
		Random rand = new Random();
		int randomInt = rand.nextInt(count);
		
		//Return a random line from the text file
		return lines.get(randomInt);
	}
}
