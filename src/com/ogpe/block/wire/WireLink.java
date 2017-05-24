package com.ogpe.block.wire;

public class WireLink {

	private final WireNode src;
	private final WireNode dst;

	public WireLink(WireNode src, WireNode dst) {
		this.src = src;
		this.dst = dst;
	}
	
	public boolean contains(WireNode node) {
		return src == node || dst == node;
	}

	public WireNode getSrc() {
		return src;
	}

	public WireNode getDst() {
		return dst;
	}

}
