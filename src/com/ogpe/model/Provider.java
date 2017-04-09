package com.ogpe.model;

@FunctionalInterface
public interface Provider<T> {

	T provide();
}
