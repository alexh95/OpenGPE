package com.ogpe.model;

public class OutputPort<T> {

	private Provider<T> outputProvider;
	
	private T outputValue;
	private boolean outputValueSet;

	public OutputPort(Provider<T> valueProvider) {
		outputProvider = () -> {
			if (!outputValueSet) {
				outputValue = valueProvider.provide();
				outputValueSet = true;
			}
			return outputValue;
		};
	}

	public Provider<T> getOutputProvider() {
		return outputProvider;
	}
}
