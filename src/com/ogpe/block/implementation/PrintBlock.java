package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.model.implementation.PrintBlockModel;
import com.ogpe.block.view.implementation.PrintBlockView;
import com.ogpe.observable.Observable;

public class PrintBlock extends Block<PrintBlockModel, PrintBlockView> {

	public PrintBlock(PrintBlockModel printBlockModel) {
		super(printBlockModel, new PrintBlockView(printBlockModel));
	}

	@Override
	public void run(Observable<String> consoleOutputObservable) {
		String printValue = getBlockModel().getPrintValue().toString();
		consoleOutputObservable.updateObservers(printValue);
	}
}
