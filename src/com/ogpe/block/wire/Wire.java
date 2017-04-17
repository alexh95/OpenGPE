package com.ogpe.block.wire;

import com.ogpe.block.wire.model.WireModel;
import com.ogpe.block.wire.view.WireView;

public class Wire {

	private WireModel wireModel;
	private WireView wireView;
	
	public Wire(WireModel wireModel) {
		this.wireModel = wireModel;
		wireView = new WireView(this.wireModel);
	}
	
	public WireModel getWireModel() {
		return wireModel;
	}
	
	public WireView getWireView() {
		return wireView;
	}
}
