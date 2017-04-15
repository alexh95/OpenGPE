package com.ogpe.block.behaviour;

public class OutputPort<T> extends ValueCacher<T> {

	private Provider<T> outputProvider;

	public OutputPort(Provider<T> valueProvider) {
		outputProvider = () -> {
			if (!isCachedValueSet()) {
				setCachedValue(valueProvider.provide());
				setCachedValueSet(true);
			}
			return getCachedValue();
		};
	}

	public Provider<T> getOutputProvider() {
		return outputProvider;
	}
}
