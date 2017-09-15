package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class KanjiGame {
	public static void vocabGame (MessageReceivedEvent event, String level, int total, int interval) {
		int increment = 0;
		
		
		//Pause the program for 10000 milliseconds, or 10 seconds, before starting.
		event.getTextChannel().sendMessage("`"+level+" vocab quiz starting in 5 seconds. \nThis game will be "+total+" words long"
				+ " with an interval of "+interval+" seconds.`").queue();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Not sure what to do with this while loop condition
		while (increment < total) {
			//Store random line from text file and store in text variable.
			String text = Reader.fileReader(level);
			//Get the question 
			String word = getQuestion(text);
			//Get the answer
			String ans = getAnswer(text, event);
			//Write word to txt file to print out later at end of game. 
			Writer.playedWordsWrite(word, ans);
			//Create image of word and send to discord
			imageCreator(word, event);

			//Wait before printing correct answer
			try {
				Thread.sleep(interval*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			//Print the answer
			event.getTextChannel().sendMessage("Correct answer: "+ans).queue();

			//Wait a little bit more
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			//Increment
			increment++; 
		}
		
		//Show words played and clear word file
		Writer.listWords(event);
		Writer.clearWords();
	}
	
	public static void imageCreator(String text, MessageReceivedEvent event) {
        /*
           Because font metrics is based on a graphics context, we need to create
           a small, temporary image so we can ascertain the width and height
           of the final image
         */
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Meiryo", Font.PLAIN, 65);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,width,height);
        g2d.setPaint(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File("word.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Print picture
        Message message = new MessageBuilder().append("Word:").build();
		event.getTextChannel().sendFile(new File("word.png"), message).queue();
	}
	
	//getQuestion gets the specific word from the question bracket
	public static String getQuestion(String text) {
		Pattern question = Pattern.compile("\\{ \"question\": \"(.*?)\"");
		Matcher questionMatch = question.matcher(text);
		questionMatch.find();
		String word = questionMatch.group(1);
		
		return word;
	}
	//getAnswer gets the specific reading from the answers bracket
	public static String getAnswer(String text, MessageReceivedEvent event) {
		Pattern answer = Pattern.compile("answers\":\\[ \"(.*?)\"");
		Matcher answerMatch = answer.matcher(text);
		answerMatch.find();
		String ans = "";
		try {
			ans = answerMatch.group(1);
		} catch (Exception e) {
			event.getTextChannel().sendMessage("Something went wrong. Restart the game.").queue();
		}
		
		return ans;
	}
}
