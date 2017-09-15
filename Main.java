package main;

import java.io.File;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
	//Some variables that need to be used in multiple blocks
	Boolean game = false;
	String level = "";
	int points;
	int interval;
	String gameLength, inter;
	String firstKana = null;
	String currentKana = "";

	public static void main(String[] args)
			throws LoginException, RateLimitedException, InterruptedException
	{
		//Bot's token
		String token = "MzQ1MDM1NTA1NjM4OTY1MjUx.DG1fUg.FalpRqA-ilKQqR-TVBOsQOeWVQ0";
		
		//Connect to discord application
		JDA jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
		jda.addEventListener(new Main());
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		event.getGuild().getTextChannelById("218816436141817856").sendMessage("Welcome "
				+ event.getUser().getAsMention() + " to the server!\n"
						+ "For bot commands, type ?help").queue();
	}
	
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		event.getGuild().getTextChannelById("218816436141817856").sendMessage(event.getUser().getAsMention() 
				+ " has left the server.").queue();
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		event.getJDA().getGuildById("218795281876123649").getTextChannelById("356639657485664257")
		.sendMessage("杏珠 has started up successfully").queue();
	}
	
	
	@Override
	public void onReconnect(ReconnectedEvent event) {
		event.getJDA().getGuildById("218795281876123649").getTextChannelById("356639657485664257")
		.sendMessage("杏珠 was disconnected (internet!?) but has reconnected.").queue();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		//Vocab Game code starts here. Dictionary code is below.
		if (event.getMessage().getContent().equals("k!vocab-quiz")) {
			//Display the current possible games
			HelpInfo.vocabInfo(event);
		}
		try {
			if (event.getMessage().getContent().startsWith("k!JLPT N1")) {
				//Get game data
				getGameData(event);
				//Call the game
				KanjiGame.vocabGame(event, level, points, interval);
			}
			
			//Repeat for other levels
			if (event.getMessage().getContent().startsWith("k!JLPT N2")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!JLPT N3")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!JLPT N4")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!JLPT N5")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!MajinAnkiDeck")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Prefectures")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Yojijukugo")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Expert")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Hard")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Jouyou")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Kanken1")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Kanken2")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Kanken3")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Kanken4")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Kanken5")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
			if (event.getMessage().getContent().startsWith("k!Kanken6-10")) {
				getGameData(event);
				KanjiGame.vocabGame(event, level, points, interval);
			}
		} catch (NumberFormatException e) {
			event.getTextChannel().sendMessage("もう一度入力してください。").queue();
		}
			
			
		//------------------------------------------------------------
		//------------------------------------------------------------
			
		
		//Dictionary command
		if (event.getMessage().getContent().startsWith("!")) {
			Dictionary.dictionaryCommand(event);
		}
		
		//Help and Info commands
		if (event.getMessage().getContent().equals("?help")) {
			HelpInfo.helpCall(event);
		}
		
		if (event.getMessage().getContent().equals("?info")) {
			HelpInfo.infoCall(event);
		}
		
		if (event.getMessage().getContent().startsWith("p!")) {
			Dictionary.getPronunciation(event);
		}
		
		//TODO: Get this to work
		//Shiritori
		if (event.getMessage().getContent().startsWith("s!")) {
			//Start the game
				if (event.getMessage().getContent().equals("s!start")) {
					//Get first kana randomly
					currentKana = Start.getKana();
					event.getTextChannel().sendMessage("Shiritori game started. If at anytime you would like to export the "
							+ "currently played words, use the `s!export` command.\n"
							+ "The first kana is: "+currentKana).queue();
					game = true;
				}
				else if (event.getMessage().getContent().equals("s!end")) {
					if (game == true) {
						game = false;
						event.getTextChannel().sendMessage("Shiritori game ended. Thanks for playing!").queue();
						Store.clearWords();
					}
					else {
						event.getTextChannel().sendMessage("A Shiritori game is not started. Use `s!start` to start one.").queue();
					}
				}
				else if (event.getMessage().getContent().equals("s!export")) {
					//Send Words.txt file to discord
					Message message = new MessageBuilder().append("Words played: ").build();
					event.getTextChannel().sendFile(new File("Words.txt"), message).queue();
				}
				else if (event.getMessage().getContent().equals("s!reroll")) {
					if (game == true) {
						//Grab a random kana
						currentKana = Start.getKana();
						event.getTextChannel().sendMessage("Kana rerolled to: "+currentKana).queue();
					}
					else {
						event.getTextChannel().sendMessage("A Shiritori game is not started. Use `s!start` to start one.").queue();
					}
				}
				else if (event.getMessage().getContent().equals("s!kana")){
					event.getTextChannel().sendMessage("Current kana: "+currentKana).queue();
				}
				else if (event.getMessage().getContent().equals("s!clear")) {
					Store.clearWords();
					event.getTextChannel().sendMessage("Played words cleared.").queue();
				}
				else if (event.getMessage().getContent().equals("s!played")) {
					if (game == true) {
						Store.playedWordsSoFar(event);
					}
					else {
						event.getTextChannel().sendMessage("A Shiritori game is not started. Use `s!start` to start one.").queue();
					}
				}
				else if (event.getMessage().getContent().equals("s!rules")) {
					HelpInfo.showRules(event);
				}
				else {
					//get ready for if statements out the ass
					if (game == true) {
						String word = event.getMessage().getContent().replaceAll("s!", "");
						String jisho = "http://jisho.org/api/v1/search/words?keyword=" + word;

						//rawText contains the raw text from the jisho api link.
						String rawText = Url.getUrlContents(jisho);
						String reading = Dictionary.getReading(rawText, event);
						
						String fixedReading = reading.replace("ゃ", "や").replaceAll("ゅ", "ゆ").replaceAll("ょ", "よ");
						
						if (currentKana.equals(reading.substring(0, 1))) {
							Boolean playedBefore = Store.playedWordsRead(word);
							Boolean typeCheck = Store.typeCheck(rawText);
							
							if (reading.substring(reading.length()-1).equals("ん")) {
								event.getTextChannel().sendMessage("Words cannot end in ん.\n"
										+ "For shiritori rules type `s!rules`").queue();
							}
							else {
								if (typeCheck == true) {
									if (playedBefore == false) {
										currentKana = fixedReading.substring(reading.length() - 1);
										event.getTextChannel()
										.sendMessage("Word played: " + reading + "\n" + "The next kana is: " + currentKana).queue();
										try {
											Store.playedWordsWrite(word);
										} catch (IOException e) {
											e.printStackTrace();
										}
									} else {
										event.getTextChannel().sendMessage("That word has been played before. Please enter another.")
										.queue();
										}
								}
								else {
									event.getTextChannel().sendMessage("That is not a noun or na-adj, please enter another word.\n"
											+ "For shiritori rules type `s!rules`").queue();
								}
							}
						} else {
							event.getTextChannel().sendMessage("That word does not start with " + currentKana 
									+ ". Please enter another.").queue();
						}
					}
					else {
						event.getTextChannel().sendMessage("A Shiritori game is not started. Use `s!start` to start one.").queue();
					}
				}
			}
		}
		
	//getGameData parses the entered command from the user to get the specific
	//data values for the game.
	public void getGameData(MessageReceivedEvent event) {
		//Get the entered text
		String unparsed = event.getMessage().getContent();
		
		//Get the level being played
		level = unparsed.substring(unparsed.indexOf("!")+1, unparsed.indexOf("<"));
		
		//Get how many words for the game
		gameLength = unparsed.substring(unparsed.indexOf("<")+1, unparsed.indexOf("/"));
		points = Integer.parseInt(gameLength);
		if (points > 30) {
			event.getTextChannel().sendMessage("Surpassed maximum game length. Setting to 30.").queue();
			points = 30;
		}
		
		//Get the interval in seconds
		inter = unparsed.substring(unparsed.indexOf("/")+1, unparsed.indexOf(">"));
		interval = Integer.parseInt(inter);
	}
}
