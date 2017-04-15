package com.ogpe.block.behaviour.implementation;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.behaviour.OutputPort;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.requester.Requester;

public class ConstantBlockBehavior extends BlockBehavior<ConstantBlockModel> {

	private OutputPort<Integer> constantValueOutputPort;

	public ConstantBlockBehavior() {
		super();
		constantValueOutputPort = new OutputPort<>(() -> getBlockModelRequester().request().getConstantValue());
		addOutputPort(constantValueOutputPort);
	}

	public Requester<Integer> getOutputRequester() {
		return constantValueOutputPort.getOutputRequester();
	}
}
