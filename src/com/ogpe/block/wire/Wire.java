package com.ogpe.block.wire;

public class Wire<T> {

	private WireModel<T> wireModel;
	private WireView<T> wireView;

	public Wire() {
		wireModel = new WireModel<>();
		wireView = new WireView<T>(wireModel);
	}

	public WireModel<T> getWireModel() {
		return wireModel;
	}

	public WireView<T> getWireView() {
		return wireView;
	}
}
