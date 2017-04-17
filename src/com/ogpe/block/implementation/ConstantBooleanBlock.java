package com.ogpe.block.implementation;

import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.behaviour.implementation.ConstantBooleanBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantBooleanBlockView;

public class ConstantBooleanBlock extends ConstantBlock<Boolean> {

	public ConstantBooleanBlock(ConstantBlockModel<Boolean> constantBlockModel, double x, double y) {
		super(constantBlockModel, new ConstantBooleanBlockView(constantBlockModel, x, y));
	}

	@Override
	public ConstantBlockBehavior<Boolean> makeBlockBehavior() {
		return new ConstantBooleanBlockBehavior(getBlockModel());
	}
}
