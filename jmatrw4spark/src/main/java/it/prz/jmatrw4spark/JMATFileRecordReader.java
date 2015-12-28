package it.prz.jmatrw4spark;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


public class JMATFileRecordReader<LongWritable, Text> extends RecordReader<LongWritable, Text> {

	public void initialize(InputSplit baseSplit, TaskAttemptContext ctx) throws IOException, InterruptedException {
		Configuration cfg = ctx.getConfiguration();
		
		FileSplit fileSplit = (FileSplit) baseSplit;
		Path filePath = fileSplit.getPath();

		FileSystem fs = filePath.getFileSystem(cfg);
		FSDataInputStream is = fs.open(fileSplit.getPath());
		
		
		
	}//EndMethod.

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LongWritable getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}//EndClass.
