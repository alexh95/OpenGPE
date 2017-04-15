package com.ogpe.block.behaviour;

import java.util.ArrayList;
import java.util.Collection;

import com.ogpe.block.model.BlockModel;

public abstract class BlockBehavior<M extends BlockModel> {

	private Provider<M> blockModelProvider;

	private Collection<InputPort<?>> inputPorts;
	private Collection<OutputPort<?>> outputPorts;

	public BlockBehavior() {
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

	public Provider<M> getBlockModelProvider() {
		return blockModelProvider;
	}

	public void setBlockModelProvider(Provider<M> blockModelProvider) {
		this.blockModelProvider = blockModelProvider;
	}
}
