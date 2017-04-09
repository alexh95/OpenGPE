package com.ogpe.model;

public class PrintBlock extends Block {

	private InputPort<String> printValueInputPort;
	
	public PrintBlock(Provider<?> printValueProvider) {
		super();
		printValueInputPort = new InputPort<>(() -> printValueProvider.provide().toString());
		addInputPort(printValueInputPort);
	}
	
	public void execute() {
		String printValue = printValueInputPort.request();
		System.out.println(printValue);
	}
}
