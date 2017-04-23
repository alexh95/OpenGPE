package com.ogpe.block.type;

public enum DataType {
	ANY,
	BOOLEAN,
	NUMBER,
	STRING;
	
	public boolean isAssignable(DataType that) {
		return equals(ANY) || that.equals(ANY) || equals(that);
	}
}
