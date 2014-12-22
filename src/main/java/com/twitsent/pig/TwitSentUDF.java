package com.twitsent.pig;
import org.apache.pig.builtin.MonitoredUDF;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

@MonitoredUDF
public class TwitSentUDF extends EvalFunc<Tuple> {

	private BagFactory bagFactory = BagFactory.getInstance();
	private TupleFactory tupleFactory = TupleFactory.getInstance();

	public static String urlRegex = "http+://[\\S]+|https+://[\\S]+";
	public static Pattern urlPattern = Pattern.compile(urlRegex);

	public static String longStringRegex = "(.)\\1{2,}";

	public static String mentionRegex = "^@\\w+|\\s@\\w+";
	public static Pattern mentionPattern = Pattern.compile(mentionRegex);

	public static String hashTagRegex = "^#\\w+|\\s#\\w+";
	public static Pattern hashTagattern = Pattern.compile(hashTagRegex);

	public static String nasdaqRegex = "\\$\\b[A-Z,a-z]{1,5}\\b";
	public static Pattern nasdaqPattern = Pattern.compile(nasdaqRegex);

	public static String punctuationRegex = "\\p{Punct}";
	public static Pattern punctuationPattern = Pattern
			.compile(punctuationRegex);

	public TweetPreProcessor preProcessUtil = new TweetPreProcessor();
	public SentimentAnalyzer analyzer = new SentimentAnalyzer();
	@Override
	public Tuple exec(Tuple arg0) throws IOException {
		// TODO Auto-generated method stub
		if (arg0 == null || arg0.size() == 0 || arg0.get(0) == null) {
			return null;
		}
		try {
			

			// Get the unparsed (RAW) tweet from PIG
			// TODO - Change the index according to the dataset
			String rawTweet = (String) arg0.get(0);

			rawTweet = rawTweet.replaceAll(urlRegex, " ");
			rawTweet = rawTweet.replaceAll(mentionRegex, " ");
			rawTweet = rawTweet.replaceAll(nasdaqRegex, " ");
			rawTweet = rawTweet.replaceAll(punctuationRegex, " ");
			rawTweet = rawTweet.toLowerCase();
			rawTweet = rawTweet.replaceAll(longStringRegex, "$1$1");
			rawTweet = rawTweet.replaceAll("[\\s]+", " ");
			rawTweet = preProcessUtil.convert(rawTweet);
			Tuple outputTuple = tupleFactory.newTuple();
			String sentimentScore = analyzer.exec(rawTweet);
			outputTuple.append(rawTweet);
			outputTuple.append(sentimentScore);
			return outputTuple;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
		Schema s = new Schema();
		s.add(new Schema.FieldSchema(null, DataType.CHARARRAY));
		return Arrays.asList(new FuncSpec(this.getClass().getName(), s));
	}
}
