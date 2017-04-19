package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.wire.WireNodeTarget;
import com.ogpe.block.wire.WireNodeTargetType;
import com.ogpe.requester.Requester;

public class PrintBlockModel extends BlockModel {

	private WireNodeTarget<? extends Object> printValueWireNodeTarget;

	public PrintBlockModel() {
		super();
		printValueWireNodeTarget = new WireNodeTarget<>(WireNodeTargetType.DESTINATION);
		addWireNodeTarget(printValueWireNodeTarget);
	}

	public WireNodeTarget<? extends Object> getPrintValueWireNodeTarget() {
		return printValueWireNodeTarget;
	}

	public Requester<? extends Object> getPrintValueRequester() {
		return printValueWireNodeTarget.getRequester();
	}
}
