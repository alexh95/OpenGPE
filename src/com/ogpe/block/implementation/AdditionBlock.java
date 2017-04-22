package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.model.implementation.AdditionBlockModel;
import com.ogpe.block.view.implementation.AdditionBlockView;

public class AdditionBlock extends Block<AdditionBlockModel, AdditionBlockView> {

	public AdditionBlock(AdditionBlockModel additionBlockModel) {
		super(additionBlockModel, new AdditionBlockView(additionBlockModel));
	}
}
