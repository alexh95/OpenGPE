package com.ogpe.block.wire;

import com.ogpe.requester.Requester;

public class WireNodeTarget<T> {

	private WireNodeTargetView wireNodeTargetView;
	private WireNodeTargetType wireNodeTargetType;

	private Requester<T> requester;

	public WireNodeTarget(WireNodeTargetType wireNodeTargetType) {
		this.wireNodeTargetType = wireNodeTargetType;
		requester = null;
	}

	public WireNode<T> makeWireNode() {
		return new WireNode<>(this);
	}

	public void initWireNodeTargetView(double x, double y) {
		wireNodeTargetView = new WireNodeTargetView(x, y);
	}

	public WireNodeTargetView getWireNodeTargetView() {
		return wireNodeTargetView;
	}

	public WireNodeTargetType getWireNodeTargetType() {
		return wireNodeTargetType;
	}
	
	public void setRequester(Requester<T> requester) {
		this.requester = requester;
	}

	public Requester<T> getRequester() {
		return requester;
	}
}
