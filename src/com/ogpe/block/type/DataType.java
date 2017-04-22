package com.ogpe.block.type;

public enum DataType {
	ANY,
	BOOLEAN,
	NUMBER,
	STRING;
	
	public boolean assignable(DataType that) {
		return (this == ANY) || (this.equals(that));
	}
}
