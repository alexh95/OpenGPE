package com.ogpe.block.implementation;

import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.behaviour.implementation.ConstantStringBlockBehavior;
import com.ogpe.block.model.implementation.ConstantStringBlockModel;
import com.ogpe.block.view.implementation.ConstantStringBlockView;

public class ConstantStringBlock extends ConstantBlock<String> {

	public ConstantStringBlock(double x, double y) {
		super(new ConstantStringBlockModel(), new ConstantStringBlockView(x, y));
	}

	@Override
	public ConstantBlockBehavior<String> makeBlockBehavior() {
		return new ConstantStringBlockBehavior(getBlockModel());
	}
}
