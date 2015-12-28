package it.prz.jmatrw4spark;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class JMATFileInputFormat extends FileInputFormat<LongWritable, DoubleWritable> {

	@Override
	public RecordReader<LongWritable, DoubleWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		return new JMATFileRecordReader();
	}//EndMethod.

}//EndClass.
