package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Writer {
	public static void playedWordsWrite(String question, String answer) 
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Words-vocab.txt"), true));
			writer.write(question+"("+answer+")");
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void listWords(MessageReceivedEvent event) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Words-vocab.txt")); 
			StringBuilder builder = new StringBuilder();
			String line = br.readLine();
			if (line != null) {
				while (line != null) {
					//For some reason this is printing an empty line
					//I can't seem to find where its coming from
					if (!line.equals("")) {
						builder.append(line.replace("\n", "").replace("\r", "")).append("; ");
					}
					
					line = br.readLine();
				} 
			}
			else {
				event.getTextChannel().sendMessage("No words have been played.").queue();
			}
			br.close();
			event.getTextChannel().sendMessage("```Words played this game: \n"+builder.toString()+"```").queue();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//clear the words from the file. 
	public static void clearWords() {
		try {
			PrintWriter pw = new PrintWriter("Words-vocab.txt");
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
