package it.jmatfileio.matdatatypes;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class MLDataType {

	public static final MLDataType miINT8 = new MLDataType(1, 1);
	public static final MLDataType miINT32 = new MLDataType(5, 4);
	public static final MLDataType miUINT32 = new MLDataType(6, 4);
	public static final MLDataType miMATRIX = new MLDataType(14, 0); 
	
	private static final Map<Integer, MLDataType> dataTypes = new HashMap<Integer, MLDataType>(); 
	public static MLDataType dataTypeFromValue(int index) { return dataTypes.get(index); }
	
	static {
		dataTypes.put(miINT8.value, miINT8);
		dataTypes.put(miUINT32.value, miUINT32);
		dataTypes.put(miMATRIX.value, miMATRIX);
	}
	
	public final int value;
	public final int bytes;
	
	private MLDataType(int value, int bytes) {
		this.value = value;
		this.bytes = bytes;
	}//EndConstructor.
		
}//EndClass.
