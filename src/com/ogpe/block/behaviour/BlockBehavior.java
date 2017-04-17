package com.ogpe.block.behaviour;

import java.util.ArrayList;
import java.util.Collection;

import com.ogpe.block.model.BlockModel;

public abstract class BlockBehavior<M extends BlockModel> {

	private M blockModel;

	private Collection<InputPort<?>> inputPorts;
	private Collection<OutputPort<?>> outputPorts;

	public BlockBehavior(M blockModel) {
		this.blockModel = blockModel;
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

	public M getBlockModel() {
		return blockModel;
	}
}
