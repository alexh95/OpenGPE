package com.ogpe.block.network;

public enum NetworkNodeType {
	INPUT,
	OUTPUT;
	
	public boolean isAssignable(NetworkNodeType that) {
		return !equals(that);
	}
}
