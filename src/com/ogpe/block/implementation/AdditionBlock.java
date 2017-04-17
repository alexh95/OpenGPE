package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.implementation.AdditionBlockBehavior;
import com.ogpe.block.model.implementation.AdditionBlockModel;
import com.ogpe.block.view.implementation.AdditionBlockView;

public class AdditionBlock extends Block<AdditionBlockModel, AdditionBlockBehavior, AdditionBlockView> {

	public AdditionBlock(AdditionBlockModel additionBlockModel, double x, double y) {
		super(additionBlockModel, new AdditionBlockView(additionBlockModel, x, y));
	}

	@Override
	public AdditionBlockBehavior makeBlockBehavior() {
		return new AdditionBlockBehavior(getBlockModel());
	}
}
