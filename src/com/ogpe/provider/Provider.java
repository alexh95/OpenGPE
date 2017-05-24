package com.ogpe.provider;

@FunctionalInterface
public interface Provider<T> {

	T provide();
	
}
