package com.ogpe.block.behaviour.implementation;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.behaviour.OutputPort;
import com.ogpe.block.behaviour.Provider;
import com.ogpe.block.model.implementation.ConstantBlockModel;

public class ConstantBlockBehavior extends BlockBehavior<ConstantBlockModel> {

	private OutputPort<Integer> constantValueOutputPort;

	public ConstantBlockBehavior() {
		super();
		constantValueOutputPort = new OutputPort<>(() -> getBlockModelProvider().provide().getConstantValue());
		addOutputPort(constantValueOutputPort);
	}

	public Provider<Integer> getOutputProvider() {
		return constantValueOutputPort.getOutputProvider();
	}
}
