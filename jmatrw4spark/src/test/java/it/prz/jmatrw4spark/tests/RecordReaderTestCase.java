package it.prz.jmatrw4spark.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
import org.apache.hadoop.util.ReflectionUtils;

import it.prz.jmatrw4spark.JMATFileInputFormat;
import junit.framework.TestCase;

public class RecordReaderTestCase extends TestCase {

	public void testInit() throws URISyntaxException, IOException, InterruptedException {
		Configuration conf = new Configuration(false);
		conf.set("fs.default.name", "file:///");
		
		//Get the input stream.
		InputStream fis = TestCase.class.getResourceAsStream("/basicexamples/example01_array.mat");
		assertNotNull(fis);
		
		//Create the path.
		URL urlFile =  TestCase.class.getResource("/basicexamples/example01_array.mat");
		assertNotNull(urlFile);
		URI uriFile = urlFile.toURI();
		assertNotNull(uriFile);
		Path filePath = new Path(uriFile);
		
		//Create the file split.
		FileSplit fileSplit = new FileSplit(filePath, 0, 2, null);
		
		//Create the InputFormat
		InputFormat inputFormat = ReflectionUtils.newInstance(JMATFileInputFormat.class, conf);
		TaskAttemptContext context = new TaskAttemptContextImpl(conf, new TaskAttemptID());
		RecordReader reader = inputFormat.createRecordReader(fileSplit, context);
		
		reader.initialize(fileSplit, context);
	}//EndTest.
	
}//EndClass.
