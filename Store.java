package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Store {
	public static void playedWordsWrite(String word) 
			throws IOException
	{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Words.txt"), true));
			writer.write(word);
			writer.newLine();
			writer.close();
	}
	
	//Check if user input is already a played word
	public static Boolean playedWordsRead(String word) {
		File file = new File("Words.txt");
		Boolean result = false;
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.equals(word)) {
					result = true;
				}
			}
			scanner.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean typeCheck(String rawText) {
		Pattern part_speech = Pattern.compile("\"parts_of_speech\":\\[\"(.*?)\"");
		Matcher part_matcher = part_speech.matcher(rawText);
		part_matcher.find();
		String type = part_matcher.group(1);
		
		if (type.equals("Ichidan verb")) {
			return false;
		}
		else if (type.startsWith("Godan verb")) {
			return false;
		}
		else if (type.equals("Adverb")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	//Display all words played so far
	public static void playedWordsSoFar(MessageReceivedEvent event) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("Words.txt")); 
				StringBuilder builder = new StringBuilder();
				String line = br.readLine();
				int count = 0;
				if (line != null) {
					while (line != null) {
						//For some reason this is printing an empty line
						//I can't seem to find where its coming from
						if (!line.equals("")) {
							builder.append(line.replace("\n", "").replace("\r", "")).append("; ");
							count++;
						}
						
						line = br.readLine();
					} 
				}
				else {
					event.getTextChannel().sendMessage("No words have been played.").queue();
				}
				br.close();
				try {
					event.getTextChannel().sendMessage("```"+builder.toString()+"\n"
							+ "Total words played: "+count+"```").queue();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	
	//Clear words.txt
	public static void clearWords() {
		try {
			PrintWriter pw = new PrintWriter("Words.txt");
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
