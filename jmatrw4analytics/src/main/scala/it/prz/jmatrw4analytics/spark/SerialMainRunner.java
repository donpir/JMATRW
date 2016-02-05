package it.prz.jmatrw4analytics.spark;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import it.prz.jmatrw.JMATReader;
import it.prz.jmatrw4analytics.mathexp.MathExpEvaluator;
import it.prz.jmatrw4analytics.mathexp.model.DTAny;
import it.prz.jmatrw4analytics.mathexp.model.DTBool;

public class SerialMainRunner {
	private static JMATReader reader;
	private static Double mean;
	private static Double stddev;
	private static String filePath;
	private static File finput;
	
	public static void main(String[] args) throws IOException {
		String formula = "Peak > mean + 1 * stddev";
		MathExpEvaluator evaluator = new MathExpEvaluator(formula);
		
		scala.collection.mutable.HashMap<String, Object> variables = new scala.collection.mutable.HashMap<String, Object>();
		
		String filePath = "d:/tmp/" + "vecRow512K.mat";
		System.out.println("Running file " + filePath);
		
		long startTime = System.currentTimeMillis();
		
		//Read the matfile.
		finput = new File(filePath);
		if (finput == null) {
			System.out.println("Input file is null");
			return;
		}
		InputStream is = new FileInputStream(finput);
		reader = new JMATReader(is);
		precalculations();
		variables.put("mean", mean);
		variables.put("stddev", stddev);
		
		long count = 0;
		long filtered = 0;
		while (reader.hasNext()) {
			Double curval = reader.next();
			variables.put("Peak", curval);
			DTAny value = evaluator.evaluate( variables );
			if (value instanceof DTBool && ((DTBool) value).value()) {
				filtered++;
			}
			
			count++;
		}
		
		long endTime = System.currentTimeMillis();
		long diffTime = endTime - startTime;
		
		System.out.println( diffTime + " milliseconds");
		System.out.println("Mean: " + mean + " Stddev: " + stddev + " formula:" + (mean + 1 * stddev));
		System.out.println("Num of items in base rdd is " + count + " The num of filtered items are " + filtered);
	}
	
	private static void precalculations() throws IOException {
		mean = calculateMean();
		stddev = calculateStdDev(mean);
	}//EndMethod.
	
	private static double calculateMean() throws IOException {
		double dmean = 0;
		long i=0;
		while (reader.hasNext()) {
			double curval = reader.next();
			dmean += curval;
			i++;
		}
		
		//Resets and create the stream again.
		reader.close();
		InputStream is = new FileInputStream(finput);
		reader = new JMATReader(is);
		
		return (i<=0) ? 0 : dmean / i; 
	}//EndMethod.
	
	private static double calculateStdDev(double dmean) throws IOException {
		double dstd = 0;
		long i=0;
		while (reader.hasNext()) {
			double curval = reader.next();
			dstd += Math.pow(curval - dmean, 2);
			i++;
		}
		
		//Resets and create the stream again.
		reader.close();
		InputStream is = new FileInputStream(finput);
		reader = new JMATReader(is);
		
		if (i != 0) dstd = dstd / i;
		dstd = Math.sqrt(dstd);
		
		return dstd;
	}//EndMethod.
	
}
