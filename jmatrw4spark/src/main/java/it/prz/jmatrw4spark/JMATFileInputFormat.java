/*
 * (C) Copyright 2015 Donato Pirozzi <donatopirozzi@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3 which accompanies this distribution (See the COPYING.LESSER
 * file at the top-level directory of this distribution.), and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Donato Pirozzi
 */

package it.prz.jmatrw4spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import it.prz.jmatrw.JMATInfo;
import it.prz.jmatrw.JMATReader;
import it.prz.jmatrw.matdatatypes.MLDataType;

public class JMATFileInputFormat extends FileInputFormat<Long, Double> {

	private static final double SPLIT_SLOP = 1.1;   // 10% slop
	
	private static final Log LOG = LogFactory.getLog(JMATFileInputFormat.class);
	
	@Override
	public RecordReader<Long, Double> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		return new JMATFileRecordReader();
	}//EndMethod.

	@Override
	public List<InputSplit> getSplits(JobContext job) throws IOException {
		long minSize = Math.max(getFormatMinSplitSize(), getMinSplitSize(job));
		long maxSize = getMaxSplitSize(job);
		
		//It generates the splits.
		List<InputSplit> splits = new ArrayList<InputSplit>();
	    List<FileStatus> files = listStatus(job);
	    
	    for (FileStatus file : files) {
	    	Path filePath = file.getPath();
	    	
	    	//Calculates the content (array of double) length in bytes.
	    	FileSystem fs = filePath.getFileSystem(job.getConfiguration());
	    	FSDataInputStream dis = fs.open(filePath);
	    	JMATReader _matReader = new JMATReader(dis);
	    	JMATInfo _matdata = _matReader.getInfo();
	    	
	    	long length = _matdata.dataNumOfItems * MLDataType.miDOUBLE.bytes; //Content length.
	    	long lContentByteOffset = dis.getPos();
	    	
	    	_matReader.close();
	    	_matReader = null;
	    	dis = null;
	    	
	    	//Zero bytes, empty file split.
	    	if (length <= 0) {
	    		 //Create empty hosts array for zero length files
	            splits.add(makeSplit(filePath, 0, length, new String[0]));
	    	}
	    	
	    	//Split the data.
	    	if (length > 0) {
	    		BlockLocation[] blkLocations;
	            if (file instanceof LocatedFileStatus) {
	              blkLocations = ((LocatedFileStatus) file).getBlockLocations();
	            } else {
	              blkLocations = fs.getFileBlockLocations(file, lContentByteOffset, length);
	            }
	            if (isSplitable(job, filePath)) {
	            	long blockSize = file.getBlockSize();
	            	long splitSize = computeSplitSize(blockSize, minSize, maxSize);
	            	
	            	long bytesRemaining = length;
	            	while (((double) bytesRemaining)/splitSize > SPLIT_SLOP) {
	            		long lBlockByteStart = lContentByteOffset + length-bytesRemaining;
	            		
	                    int blkIndex = getBlockIndex(blkLocations, lBlockByteStart);
	                    splits.add(makeSplit(filePath, lBlockByteStart, splitSize, blkLocations[blkIndex].getHosts()));
	                    bytesRemaining -= splitSize;
	                 }//EndWhile.

	                 if (bytesRemaining != 0) {
	                	long lBlockByteStart = lContentByteOffset + length-bytesRemaining;
	                    int blkIndex = getBlockIndex(blkLocations, lBlockByteStart);
	                    splits.add(makeSplit(filePath, lBlockByteStart, bytesRemaining, blkLocations[blkIndex].getHosts()));
	                 }
	            } else { // not splitable
	                splits.add(makeSplit(filePath, lContentByteOffset, length, blkLocations[0].getHosts()));
	            }
	    	}
	    }//EndFor.
		
	    // Save the number of input files for metrics/loadgen
	    job.getConfiguration().setLong(NUM_INPUT_FILES, files.size());
	    LOG.debug("Total # of splits: " + splits.size());
	    return splits;
	}//EndMethod.
	
	@Override
	protected long computeSplitSize(long blockSize, long minSize, long maxSize) {
		long _size = super.computeSplitSize(blockSize, minSize, maxSize);
		
		//size must be multiple of the mat type size, 
		//to not split a number in the middle.
		if (_size % MLDataType.miDOUBLE.bytes != 0) {
			//Finds the maximum number multiple of mat type size closest to _size.
			long numOfItems = _size / MLDataType.miDOUBLE.bytes;
			_size = numOfItems * MLDataType.miDOUBLE.bytes;
		}
		
		return _size;
	}//EndMethod.

	@Override
	protected long getFormatMinSplitSize() {
		//The split size is the length of a mat file double.
		return MLDataType.miDOUBLE.bytes;
	}//EndMethod.

}//EndClass.
