package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.implementation.PrintBlockBehavior;
import com.ogpe.block.model.implementation.PrintBlockModel;
import com.ogpe.block.view.implementation.PrintBlockView;

public class PrintBlock extends Block<PrintBlockModel, PrintBlockBehavior, PrintBlockView> {

	public PrintBlock(PrintBlockModel printBlockModel, double x, double y) {
		super(printBlockModel, new PrintBlockView(printBlockModel, x, y));
	}

	@Override
	public PrintBlockBehavior makeBlockBehavior() {
		return new PrintBlockBehavior(getBlockModel());
	}
}
