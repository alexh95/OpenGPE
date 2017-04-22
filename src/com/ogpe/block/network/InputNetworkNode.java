package com.ogpe.block.network;

import com.ogpe.requester.Requester;

public class InputNetworkNode<T> extends NetworkNode<T> {

	private Requester<T> valueRequester;

	public InputNetworkNode(Requester<T> valueRequester) {
		super();
		this.valueRequester = valueRequester;
	}

	@Override
	public T getValue() {
		return valueRequester.request();
	}
}
