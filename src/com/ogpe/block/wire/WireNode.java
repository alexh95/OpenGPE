package com.ogpe.block.wire;

public class WireNode<T> {

	private WireNodeTarget<T> wireNodeTarget;
	
	public WireNode(WireNodeTarget<T> wireNodeTarget) {
		this.wireNodeTarget = wireNodeTarget;
	}
	
	public WireNodeTarget<T> getWireNodeTarget() {
		return wireNodeTarget;
	}
}
