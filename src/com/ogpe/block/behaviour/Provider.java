package com.ogpe.block.behaviour;

@FunctionalInterface
public interface Provider<T> {
	T provide();
}
