package com.ogpe.model;

public class InputPort<T> extends ValueCacher<T> {
	
	private Provider<T> inputProvider;
	
	public InputPort(Provider<T> inputProvider) {
		this.inputProvider = inputProvider;
		setCachedValueSet(false);
	}
	
	public T request() {
		if (!isCachedValueSet()) {
			setCachedValue(inputProvider.provide());
			setCachedValueSet(true);
		}
		return getCachedValue();
	}
}
