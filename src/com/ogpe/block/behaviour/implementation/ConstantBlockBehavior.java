package com.ogpe.block.behaviour.implementation;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.behaviour.OutputPort;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.requester.Requester;

public abstract class ConstantBlockBehavior<T> extends BlockBehavior<ConstantBlockModel<T>> {

	private OutputPort<T> constantValueOutputPort;

	public ConstantBlockBehavior(ConstantBlockModel<T> constantBlockModel) {
		super(constantBlockModel);
		constantValueOutputPort = new OutputPort<>(() -> getBlockModel().getConstantValue());
		addOutputPort(constantValueOutputPort);
	}

	public Requester<T> getOutputRequester() {
		return constantValueOutputPort.getOutputRequester();
	}
}
