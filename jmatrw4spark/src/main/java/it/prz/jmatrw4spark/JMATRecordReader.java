package it.prz.jmatrw4spark;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import it.prz.jmatrw.JMATReader;
import it.prz.jmatrw.io.seekable.Seeker;
import it.prz.jmatrw.io.seekable.UnsupportedSeekOperation;
import it.prz.jmatrw.matdatatypes.MLDataType;

public class JMATRecordReader<K, V> extends RecordReader<K, V> {
	
	private JMATReader _matReader = null;
	private long lBlockStart = 0;
	private long lBlockLength = 0;
	private long lBlockEnd = 0;
	private long lBlockCurPos = 0;
	private Long key;
	private Double value;
	
	public void initialize(InputSplit baseSplit, TaskAttemptContext ctx) throws IOException, InterruptedException {
		Configuration cfg = ctx.getConfiguration();
		
		FileSplit fileSplit = (FileSplit) baseSplit;
		Path filePath = fileSplit.getPath();

		FileSystem fs = filePath.getFileSystem(cfg);
		FSDataInputStream dis = fs.open(fileSplit.getPath());
		
		//Initialise the block boundaries.
		lBlockStart = fileSplit.getStart();
		lBlockLength = fileSplit.getLength();
		lBlockEnd = lBlockStart + lBlockLength;
		lBlockCurPos = lBlockStart;
		
		//Initialise the object to read the *.mat file.
		_matReader = new JMATReader(dis);
		
		//move the file pointer to the start location.
		_matReader.seek(lBlockStart, new Seeker() {
			@Override
			public boolean seekTo(long lBytePos, InputStream is) throws IOException {
				if (is instanceof FSDataInputStream == false)
					throw new UnsupportedSeekOperation("Unknown input stream " + is.getClass().getName());
				
				((FSDataInputStream) is).seek(lBytePos);
				
				return true;
			}
		});
	}//EndMethod.

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (lBlockCurPos == lBlockEnd) return false;
		if (_matReader.hasNext() == false) return false;
		
		key = (lBlockCurPos - lBlockStart) / MLDataType.miDOUBLE.bytes;
		value = _matReader.next();
		lBlockCurPos += MLDataType.miDOUBLE.bytes;
		
		return (value != null);
	}//EndMethod.

	@Override
	public K getCurrentKey() throws IOException, InterruptedException {
		return (K) key;
	}//EndMethod.

	@Override
	public V getCurrentValue() throws IOException, InterruptedException {
		return (V) value;
	}//EndMethod.

	@Override
	public float getProgress() throws IOException, InterruptedException {
		if (lBlockLength == 0) return 0;
		
		float fProgress = (lBlockCurPos - lBlockStart) / (float) lBlockLength;
		return Math.min(1.0f, fProgress);
	}//EndMethod.

	@Override
	public void close() throws IOException {
		if (_matReader != null)
			_matReader.close();
	}//EndMethod.
	
}//EndClass.
