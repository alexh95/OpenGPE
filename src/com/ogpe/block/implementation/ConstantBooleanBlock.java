package com.ogpe.block.implementation;

import com.ogpe.block.behaviour.implementation.ConstantBooleanBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBooleanBlockModel;
import com.ogpe.block.view.implementation.ConstantBooleanBlockView;

public class ConstantBooleanBlock extends ConstantBlock<Boolean> {

	public ConstantBooleanBlock(ConstantBooleanBlockModel constantBooleanBlockModel, double x, double y) {
		super(constantBooleanBlockModel, new ConstantBooleanBlockBehavior(), new ConstantBooleanBlockView(x, y));
	}
}
