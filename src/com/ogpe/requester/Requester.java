package com.ogpe.requester;

@FunctionalInterface
public interface Requester<T> {
	T request();
}
