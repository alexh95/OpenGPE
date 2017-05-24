package com.ogpe.block;

@FunctionalInterface
public interface Provider<T> {

	T provide();
	
}
