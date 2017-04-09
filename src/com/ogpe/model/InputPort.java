package com.ogpe.model;

public class InputPort<T> {
	
	private Provider<T> inputProvider;
	
	private T inputValue;
	private boolean inputValueSet;
	
	public InputPort(Provider<T> inputProvider) {
		this.inputProvider = inputProvider;
		inputValueSet = false;
	}
	
	public T request() {
		if (!inputValueSet) {
			inputValue = inputProvider.provide();
			inputValueSet = true;
		}
		return inputValue;
	}
}
