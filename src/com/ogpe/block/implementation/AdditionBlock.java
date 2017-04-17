package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.implementation.AdditionBlockBehavior;
import com.ogpe.block.model.implementation.AdditionBlockModel;
import com.ogpe.block.view.implementation.AdditionBlockView;

public class AdditionBlock extends Block<AdditionBlockModel, AdditionBlockBehavior, AdditionBlockView> {

	public AdditionBlock(AdditionBlockModel blockModel, double x, double y) {
		super(blockModel, new AdditionBlockBehavior(), new AdditionBlockView(x, y));
	}
}
