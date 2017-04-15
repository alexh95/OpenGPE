package com.ogpe.block.behaviour;

import com.ogpe.requester.Requester;

public class OutputPort<T> extends ValueCacher<T> {

	private Requester<T> outputRequester;

	public OutputPort(Requester<T> valueRequester) {
		outputRequester = () -> {
			if (!isCachedValueSet()) {
				setCachedValue(valueRequester.request());
				setCachedValueSet(true);
			}
			return getCachedValue();
		};
	}

	public Requester<T> getOutputRequester() {
		return outputRequester;
	}
}
