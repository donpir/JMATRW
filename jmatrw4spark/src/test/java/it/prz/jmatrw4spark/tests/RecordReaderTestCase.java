package it.prz.jmatrw4spark.tests;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;

import junit.framework.TestCase;

public class RecordReaderTestCase extends BaseSparkTestCase {

	public void testRecordReader() throws URISyntaxException, IOException, InterruptedException {
		//Create the path.
		URL urlFile =  TestCase.class.getResource("/basicexamples/vecRow01_x3.mat");
		assertNotNull(urlFile);
		URI uriFile = urlFile.toURI();
		assertNotNull(uriFile);
		Path filePath = new Path(uriFile);
		
		FileSplit fileSplit = new FileSplit(filePath, 184, 24, null);
		
		//Create the reader.
		TaskAttemptContext context = new TaskAttemptContextImpl(conf, new TaskAttemptID());
		RecordReader<Long, Double> reader = inputFormat.createRecordReader(fileSplit, context);
		
		reader.initialize(fileSplit, context);
		
		//key-0.
		assertTrue(reader.getCurrentKey() == null);
		assertTrue(reader.nextKeyValue() == true);
		assertTrue(reader.getCurrentKey() == 0);
		assertTrue(reader.getCurrentValue() == 1.0);
		
		//key-1.
		assertTrue(reader.nextKeyValue() == true);
		assertTrue(reader.getCurrentKey().longValue() == 1);
		assertTrue(reader.getCurrentValue() == 3.0);
		
		//key-2.
		assertTrue(reader.nextKeyValue() == true);
		assertTrue(reader.getCurrentKey().longValue() == 2);
		assertTrue(reader.getCurrentValue() == 2.0);
	}//EndTest.
	
}//EndClass.
