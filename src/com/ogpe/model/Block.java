package com.ogpe.model;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Block {

	private Collection<InputPort<?>> inputPorts;
	private Collection<OutputPort<?>> outputPorts;
	
	public Block() {
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
