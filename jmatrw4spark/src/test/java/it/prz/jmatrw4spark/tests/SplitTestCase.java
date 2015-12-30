package it.prz.jmatrw4spark.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.task.JobContextImpl;
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
import org.apache.hadoop.util.ReflectionUtils;

import it.prz.jmatrw4spark.JMATFileInputFormat;

public class SplitTestCase extends BaseSparkTestCase {

	public void testInit() throws URISyntaxException, IOException, InterruptedException {
		JobContext jobCtx = new JobContextImpl(conf, new JobID());
		List<InputSplit> lstSplits = inputFormat.getSplits(jobCtx);
		assertNotNull(lstSplits);
		assertTrue(lstSplits.size() > 0);
		
		FileSplit fileSplit = (FileSplit) lstSplits.get(0);
		assertNotNull(fileSplit);
		
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
		assertTrue(reader.getCurrentKey() == 1);
		assertTrue(reader.getCurrentValue() == 3.0);
		
		//key-2.
		assertTrue(reader.nextKeyValue() == true);
		assertTrue(reader.getCurrentKey() == 2);
		assertTrue(reader.getCurrentValue() == 2.0);
	}//EndTest.
	
}//EndTestCase.
