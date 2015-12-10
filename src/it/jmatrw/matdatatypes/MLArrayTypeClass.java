package it.jmatrw.matdatatypes;

import java.util.HashMap;
import java.util.Map;

public class MLArrayTypeClass {

	public static final MLArrayTypeClass mxDOUBLE_CLASS = new MLArrayTypeClass(6, 8); //8 bytes.
	
	private static final Map<Integer, MLArrayTypeClass> dataTypes = new HashMap<Integer, MLArrayTypeClass>(); 
	public static MLArrayTypeClass dataTypeFromValue(int index) { return dataTypes.get(index); }
	
	static {
		dataTypes.put(mxDOUBLE_CLASS.value, mxDOUBLE_CLASS);
	}
	
	public final int value;
	public final int bytes;
	
	private MLArrayTypeClass(int value, int bytes) {
		this.value = value;
		this.bytes = bytes;
	}//EndConstructor.
	
}//EndClass.
