package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Start {
	public static Boolean startGame() {
		return true;
	}
	
	public static String getKana() {
		List<String> kana = new ArrayList<String>(Arrays.asList("あ", "い", "う", "え", "お",
				"か", "き","く","け","こ",
				"さ", "し","す","せ","そ",
				"た", "ち","つ","て","と",
				"な", "に","ぬ","ね","の",
				"は", "ひ","ふ","へ","ほ",
				"ま", "み","む","め","も",
				"ら", "り","る","れ","ろ",
				"や", "ゆ","よ","わ",
				"が", "ぎ","ぐ","げ","ご",
				"ざ", "じ","ず","ぜ","ぞ",
				"だ", "ぢ","づ","で","ど",
				"ば", "び","ぶ","べ","ぼ",
				"ぱ", "ぴ","ぷ","ぺ","ぽ"));
		Random rand = new Random();
		int n = rand.nextInt(68);
		
		return kana.get(n);
	}
	
	public static Boolean endGame() {
		return false;
	}
}
