package com.ogpe.observable;

@FunctionalInterface
public interface Observer<T> {
	void update(T value);
}
