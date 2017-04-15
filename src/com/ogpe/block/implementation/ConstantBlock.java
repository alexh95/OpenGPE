package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantBlockView;

public class ConstantBlock extends Block<ConstantBlockModel, ConstantBlockBehavior, ConstantBlockView> {

	public ConstantBlock(ConstantBlockModel constantBlockModel, double x, double y) {
		super(constantBlockModel, new ConstantBlockBehavior(), new ConstantBlockView(x, y));
	}
}
