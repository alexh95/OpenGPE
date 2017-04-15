package com.ogpe.block.behaviour;

import com.ogpe.requester.Requester;

public class InputPort<T> extends ValueCacher<T> {

	private Requester<T> inputRequester;

	public InputPort(Requester<T> inputRequester) {
		this.inputRequester = inputRequester;
		setCachedValueSet(false);
	}

	public T request() {
		if (!isCachedValueSet()) {
			setCachedValue(inputRequester.request());
			setCachedValueSet(true);
		}
		return getCachedValue();
	}
}
