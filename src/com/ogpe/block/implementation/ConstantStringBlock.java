package com.ogpe.block.implementation;

import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.behaviour.implementation.ConstantStringBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantStringBlockView;

public class ConstantStringBlock extends ConstantBlock<String> {

	public ConstantStringBlock(ConstantBlockModel<String> constantBlockModel, double x, double y) {
		super(constantBlockModel, new ConstantStringBlockView(constantBlockModel, x, y));
	}

	@Override
	public ConstantBlockBehavior<String> makeBlockBehavior() {
		return new ConstantStringBlockBehavior(getBlockModel());
	}
}
