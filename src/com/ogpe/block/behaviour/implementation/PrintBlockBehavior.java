package com.ogpe.block.behaviour.implementation;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.behaviour.InputPort;
import com.ogpe.block.model.implementation.PrintBlockModel;

public class PrintBlockBehavior extends BlockBehavior<PrintBlockModel> {

	private InputPort<String> printValueInputPort;

	public PrintBlockBehavior() {
		super();
		printValueInputPort = new InputPort<>(
				() -> getBlockModelRequester().request().getPrintValueRequester().request().toString());
		addInputPort(printValueInputPort);
	}

	public void execute() {
		String printValue = printValueInputPort.request();
		System.out.println(printValue);
	}
}
