package com.twitsent.pig;

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

public class TwitSentClassifierUDF extends EvalFunc<DataBag> {
	
	private BagFactory bagFactory = BagFactory.getInstance();
    private TupleFactory tupleFactory = TupleFactory.getInstance();
    
    public static String urlRegex = "http+://[\\S]+|https+://[\\S]+";
	public static Pattern urlPattern = Pattern.compile(urlRegex);

	public static String mentionRegex = "^@\\w+|\\s@\\w+";
	public static Pattern mentionPattern = Pattern.compile(mentionRegex);

	@Override
	public DataBag exec(Tuple arg0) throws IOException {
		// TODO Auto-generated method stub
		if (arg0 == null || arg0.size() == 0 || arg0.get(0) == null) {
            return null;
        }
		try {
			DataBag output = bagFactory.newDefaultBag();
			//Get the unparsed (RAW) tweet from PIG
			//TODO - Change the index according to the dataset
			String tupleString= (String) arg0.get(0);
			String[] entities = tupleString.split(",");
			String rawTweet = entities[0];
			String pos = entities[1];
			//SWN3 swn = new SWN3("SentiWordNet_3.0.0_20130122.txt");
			double score = 0;
			for(String tweetWord:rawTweet.split("\\s")){
			 //score += swn.extract(tweetWord,pos);
			}
			String label = String.valueOf(score);
			Tuple tuple = tupleFactory.newTuple(rawTweet);
			tuple.append(label);
			output.add(tuple);
			return output;
		} catch(Exception ex) {
			throw new IOException("TwistSent:: Unknown Pre-Processing error in TwitSentUDF", ex);      
		}
	}

	@Override
	public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
		Schema s = new Schema();
        s.add(new Schema.FieldSchema(null, DataType.CHARARRAY));
        return Arrays.asList(new FuncSpec(this.getClass().getName(), s));
	}
}
