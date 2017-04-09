package com.ogpe.model;

public class SumBlock extends Block {
	
	private InputPort<Integer> sumInputPort1;
	private InputPort<Integer> sumInputPort2;

	private OutputPort<Integer> sumOutputPort;
	
	public SumBlock(Provider<Integer> sumValueProvider1, Provider<Integer> sumValueProvider2) {
		super();
		sumInputPort1 = new InputPort<>(sumValueProvider1);
		addInputPort(sumInputPort1);
		sumInputPort2 = new InputPort<>(sumValueProvider2);
		addInputPort(sumInputPort1);
		
		sumOutputPort = new OutputPort<>(() -> sumInputPort1.request() + sumInputPort2.request());
		addOutputPort(sumOutputPort);
	}

	public Provider<Integer> getOutputProvider() {
		return sumOutputPort.getOutputProvider();
	}
}
