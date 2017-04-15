package com.ogpe.block.behaviour;

import java.util.ArrayList;
import java.util.Collection;

import com.ogpe.block.model.BlockModel;
import com.ogpe.requester.Requester;

public abstract class BlockBehavior<M extends BlockModel> {

	private Requester<M> blockModelRequester;

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

	public Requester<M> getBlockModelRequester() {
		return blockModelRequester;
	}

	public void setBlockModelRequester(Requester<M> blockModelRequester) {
		this.blockModelRequester = blockModelRequester;
	}
}
