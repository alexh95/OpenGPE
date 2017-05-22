package com.ogpe.blockx;

@FunctionalInterface
public interface Provider<T> {

	T provide();
	
}
