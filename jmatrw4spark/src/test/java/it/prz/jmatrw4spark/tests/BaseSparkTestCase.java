package it.prz.jmatrw4spark.tests;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.ReflectionUtils;

import it.prz.jmatrw4spark.JMATFileInputFormat;
import junit.framework.TestCase;

public class BaseSparkTestCase extends TestCase {

	protected Configuration conf = new Configuration(false);
	protected InputFormat<Long, Double> inputFormat = null;
	
	@Override
	protected void setUp() throws Exception {
		conf.set("fs.default.name", "file:///");
		conf.set(FileInputFormat.INPUT_DIR, "D:/data/Progetti/JMatFileIOWorkspace/jmatrw-mvn-parent-project/jmatrw4spark/src/test/resources/basicexamples");
		
		inputFormat = ReflectionUtils.newInstance(JMATFileInputFormat.class, conf);
	}//EndMethod.
	
	public void testBase() {
		assertNotNull(conf);
		assertNotNull(inputFormat);
	}//EndTest.
	
}//EndClass.
