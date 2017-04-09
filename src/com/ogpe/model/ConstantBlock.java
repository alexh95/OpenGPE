package com.ogpe.model;

public class ConstantBlock extends Block {

	private OutputPort<Integer> constantValueOutputPort;
	private Integer constantValue;
	
	public ConstantBlock(Integer constantValue) {
		super();
		this.constantValue = constantValue;
		constantValueOutputPort = new OutputPort<>(() -> this.constantValue);
		addOutputPort(constantValueOutputPort);
	}

	public Provider<Integer> getOutputProvider() {
		return constantValueOutputPort.getOutputProvider();
	}
}
