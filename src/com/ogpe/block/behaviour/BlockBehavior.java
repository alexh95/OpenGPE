package com.ogpe.block.behaviour;

import java.util.ArrayList;
import java.util.Collection;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.model.BlockModelContainer;

public abstract class BlockBehavior<T extends BlockModel> extends BlockModelContainer<T> {

	private Collection<InputPort<?>> inputPorts;
	private Collection<OutputPort<?>> outputPorts;

	public BlockBehavior(T blockModel) {
		super(blockModel);
		inputPorts = new ArrayList<>();
		outputPorts = new ArrayList<>();
	}

	protected void addInputPort(InputPort<?> inputPort) {
		inputPorts.add(inputPort);
	}

	protected void addOutputPort(OutputPort<?> outputPort) {
		outputPorts.add(outputPort);
	}

	public void resetPortCaches() {
		inputPorts.forEach(valueCacher -> valueCacher.setCachedValueSet(false));
		inputPorts.forEach(valueCacher -> valueCacher.setCachedValueSet(false));
	}
}
