package it.jmatrw.tests;

import java.io.IOException;
import java.io.InputStream;

import it.jmatrw.JMATData;
import it.jmatrw.io.JMatControl;
import junit.framework.TestCase;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class TC01ReadArrayTestCase extends TestCase {

	/*
	 * example01_array.mat contains: x = [1, 3, 2]
	 * @throws IOException
	 */
		
	public void testVersion() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/basicexamples/example01_array.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMatControl controller = new JMatControl(fis);
		JMATData matdata = controller.read();
		
		//Check file version.
		assertTrue(matdata.header.contains("MATLAB"));
		assertTrue(matdata.version == 1);
	}//EndTest.
	
}//EndTestCase.
