package main;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpInfo {
	public static void helpCall (MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("```杏珠's Commands:\n"
				+ "\n"
				+ "Generic: \n"
				+ "?help - Pull up help menu.\n"
				+ "?info - Pull up info on 杏珠\n"
				+ "\n"
				+ "Dictionary: \n"
				+ "![word] - Pull upto 3 definitions from jisho for given word.\n"
				+ "!![word] - Pull all results from jisho for given word.\n"
				+ "p![word] - Pull the pronunciation for given word.\n"
				+ "\n"
				+ "Vocab Game: \n"
				+ "k!vocab-quiz - See all the levels currently available.\n"
				+ "k![level]<word count/interval> - Start a vocab game with designated values. Interval is entered in seconds.\n"
				+ "\n"
				+ "Shiritori:\n"
				+ "s!start - Start a game of shiritori. \n"
				+ "s!end - End current game of shiritori, also clears played words.\n"
				+ "s![word] - Enter word for shiritori. \n"
				+ "s!rules - Get shiritori rules.\n"
				+ "s!reroll - Reroll current kana.\n"
				+ "s!kana - See current kana in play.\n"
				+ "s!clear - Clear currently played words.\n"
				+ "s!played - See currently played words.\n"
				+ "s!export - Export currently played words in shiritori to .txt (Will export even if file is empty)```").queue();
	}
	
	public static void infoCall (MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("`杏珠（あんず）is a bot written by Majin.\n"
				+ "The bot was written in Java using the Java Discord API (JDA).`").queue();
	}

	public static void showRules(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("```Shiritori Rules: \n"
				+ "\n"
				+ "・First あんず will choose the first kana. From there, taking turns, players will make a new word "
				+ "using the last character of the previously played word. \n"
				+ "・Japanese has no words that start with ん, meaning you will lose if you enter a word that ends with it. \n"
				+ "・The only words you can use are Nouns. This includes する verbs and na-adjectives \n"
				+ "・If the word ends in a small hiragana (ぁ、ゃ、etc), you can use it as is. The bot will automatically convert the"
				+ " kana to its normal form for you to use so you do not even have to be aware of this.\n"
				+ "\n"
				+ "・まずは杏珠が最初の仮名を決めて投稿する。以降の人は順番に、前の人が言った単語の最後の文字から始まる単語を言っていく\n"
				+ "・日本語には「ん」で始まる単語がほぼ皆無に等しいため、通常は「ん」で終わる単語を言ってしまうと負けになる。\n"
				+ "・挙げる単語は名詞に限る。しかし、スル動詞と形容動詞も使える。\n"
				+ "・最後の文字が拗音の場合（ぁ、ゃ、等）、そのまま使っていい：いしゃ　→　やり```").queue();
	}
	
	public static void vocabInfo(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("Welcome to the Vocab Quiz game. Please select a level. \n"
				+ "Currently offered levels: " + "```JLPT N1 \n" + "JLPT N2 \n" + "JLPT N3 \n"
				+ "JLPT N4 \n" + "JLPT N5 \n"
						+ "Hard \n"+"Expert \n"
								+ "Jouyou (guess any acceptable reading of kanji)\n"
								+ "MajinAnkiDeck \n" + "Prefectures and Regions (enter just Prefectures) \n"+"Yojijukugo (extremely difficult) \n"
										+ "Kanken1 \n"
										+ "Kanken2 \n"
										+ "Kanken3 \n"
										+ "Kanken4 \n"
										+ "Kanken5 \n"
										+ "Kanken6-10``` \n"
										+ "`Enter in this format: k![level]<word count/interval>\n"
				+ "Sample: k!JLPT N1<15/4> (Interval is entered in seconds)\n"
				+ "The interval is the time between words.`").queue();
	}
}
