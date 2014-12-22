package com.twitsent.pig;

import java.io.IOException;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

public class SentimentAnalyzer  {
	private static StanfordCoreNLP pipeline;
	public SentimentAnalyzer(){
		Properties props = new Properties();
		props.setProperty("annotators",
				"tokenize, ssplit, parse, sentiment");
		 pipeline = new StanfordCoreNLP(props);
	}
	
	public String exec(String line)  {
		// TODO Auto-generated method stub
		try {
		if (line == null) {
			return null;
		}
		
			// String line = (String) arg0.get(0);
			int mainSentiment = 0;
			if (line != null && line.length() > 0) {
				int longest = 0;
				Annotation annotation = pipeline.process(line);
				for (CoreMap sentence : annotation
						.get(CoreAnnotations.SentencesAnnotation.class)) {
					Tree tree = sentence
							.get(SentimentCoreAnnotations.AnnotatedTree.class);
					int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
					String partText = sentence.toString();
					if (partText.length() > longest) {
						mainSentiment = sentiment;
						longest = partText.length();
					}

				}
			}
		
			return String.valueOf(mainSentiment);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	
	

}