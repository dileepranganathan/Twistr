package com.twitsent.pig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class TweetPreProcessor {
	private HashMap<String, String> slangDB;
	private HashSet<String> stopWordsDB;

	public TweetPreProcessor() {
		slangDB = new HashMap<String, String>();
		stopWordsDB = new HashSet<String>();
		loadSlangDB();
		loadStopWordsDB();
	}

	public void loadStopWordsDB() {
		String fileUrl = "/stopwords.txt";
		BufferedReader reader = null;
		String line = "";
		try {
			reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileUrl)));
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line != null && line.length() > 0) {
					stopWordsDB.add(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadSlangDB() {
		String fileUrl = "/Slanglist.csv";
		BufferedReader reader = null;
		String delimiter = ";";
		String line = "";
		try {
			reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileUrl)));
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line != null && line.length() > 0) {
					String[] temp = line.split(delimiter);
					slangDB.put(temp[0].toLowerCase(), temp[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String convert(String tweet) {
		if (tweet == null || tweet.length() == 0) {
			return "";
		}
		StringTokenizer tweetTokenizer = new StringTokenizer(tweet);
		StringBuilder expandedTweet = new StringBuilder("");
		while (tweetTokenizer.hasMoreTokens()) {
			String token = tweetTokenizer.nextToken();
			if (token == null || token.length() == 0) {
				continue;
			}
			String expand = slangDB.get(token);
			if (expand == null || expand.length() == 0) {
				expand = token;
			}
			expandedTweet.append(expand);
			expandedTweet.append(" ");
		}
		return expandedTweet.toString();
	}

}
