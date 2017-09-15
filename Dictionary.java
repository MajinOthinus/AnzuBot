package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Dictionary {
	public static void dictionaryCommand(MessageReceivedEvent event) {
		String output;
		String word;
    
		String url = "http://jisho.org/api/v1/search/words?keyword=";
		List<String> allMatches = new ArrayList<String>();


		try {
			//Converting entered message to be added to url and then searched.
			word = event.getMessage().getContent().replace("!", "");
			url = url + "\""+word+"\"";
		
			//Output contains the raw text from jisho
			output = Url.getUrlContents(url);

			//Patterns for gathering the info from output
			String reading = getReading(output, event);
			
			Pattern def = Pattern.compile("\"english_definitions\":\\[\"(.*?)\"\\]");
			Matcher matcher2 = def.matcher(output);
		
			Pattern part_speech = Pattern.compile("\"parts_of_speech\":\\[\"(.*?)\"\\]");
			Matcher part_matcher = part_speech.matcher(output);
			
			Pattern kanji = Pattern.compile("\"word\":\"(.*?)\"");
			Matcher kanji_match = kanji.matcher(output);
			kanji_match.find();
		
			//Gather all of the instances of the English definitions pattern
			while (matcher2.find()) {
				allMatches.add(matcher2.group(1));
				if (allMatches.size() == 24) {
					break;
				}
			}	
			if (reading != output && part_matcher.find()) {	
			
				//Print
				event.getTextChannel().sendMessage("Kanji: "+kanji_match.group(1) + "\n"
						+"Reading: "+reading+"\n"
						+"Part of Speech: "+part_matcher.group(1).replace(",", "; ").replace("\"", "")
						+"\n"+"\n"+"Definitions:").queue();
					
				//Command to print all definitions.
				int loops = Math.min(3, allMatches.size());
				if (event.getMessage().getContent().startsWith("!!")) 
						loops = allMatches.size();

				for (int i = 0; i < loops; i++) {
					event.getTextChannel().sendMessage("```"+(i + 1) + ".) " + allMatches.get(i).replace("\"", "").replace(",", "; ")
							.replace("[", " ").replace("]", "").replace("english_definitions:", "")+"```").queue();
				}
			}
			else {
				event.getTextChannel().sendMessage("Word not found").queue();
			}
		} catch (Exception e) {
			event.getTextChannel().sendMessage("Something went wrong. Contact Majin.").queue();
		}
	} 
	
	public static void getPronunciation(MessageReceivedEvent event) {
		String kanji = event.getMessage().getContent().replaceAll("p!", "");
		String kana = "";
		String jisho = "http://jisho.org/api/v1/search/words?keyword="+kanji;
		
		//rawText contains the raw text from the jisho api link.
		String rawText = Url.getUrlContents(jisho);

		//Patterns for gathering the reading from the raw text
		kana = getReading(rawText, event);
		
		//If we find a match
		if (kana != rawText) {
			String pronounceURL = "https://assets.languagepod101.com/dictionary/japanese/audiomp3.php?kanji="
					+ kanji + "&kana=" + kana;
			event.getTextChannel().sendMessage("Pronunciation of "+kanji+": "+"\n"+pronounceURL).queue();
		}
		else {
			event.getTextChannel().sendMessage("No match was found, or something went wrong.").queue();
		}
	}
	
	public static String getReading(String output, MessageReceivedEvent event) {
		Pattern reading = Pattern.compile("\"reading\":\"(.*?)\"");
		Matcher matcher = reading.matcher(output);
		if (matcher.find()) {
			output = matcher.group(1);
		}
		else {
			event.getTextChannel().sendMessage("┐('～`;)┌").queue();
		}
		return output;
	}
}
