package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.implementation.PrintBlockBehavior;
import com.ogpe.block.model.implementation.PrintBlockModel;
import com.ogpe.block.view.implementation.PrintBlockView;

public class PrintBlock extends Block<PrintBlockModel> {

	public PrintBlock(PrintBlockModel printBlockModel, double x, double y) {
		super(new PrintBlockBehavior(printBlockModel), new PrintBlockView(printBlockModel, x, y));
	}

}
