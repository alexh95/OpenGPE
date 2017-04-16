package com.ogpe.block.implementation;

import com.ogpe.block.behaviour.implementation.ConstantStringBlockBehavior;
import com.ogpe.block.model.implementation.ConstantStringBlockModel;
import com.ogpe.block.view.implementation.ConstantStringBlockView;

public class ConstantStringBlock extends ConstantBlock<String> {

	public ConstantStringBlock(ConstantStringBlockModel constantStringBlockModel, double x, double y) {
		super(constantStringBlockModel, new ConstantStringBlockBehavior(), new ConstantStringBlockView(x, y));
	}
}
