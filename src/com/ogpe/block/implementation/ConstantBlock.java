package com.ogpe.block.implementation;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantBlockView;

public abstract class ConstantBlock<T> extends Block<ConstantBlockModel<T>, ConstantBlockBehavior<T>, ConstantBlockView<T>> {

	public ConstantBlock(ConstantBlockModel<T> constantBlockModel, ConstantBlockView<T> constantBlockView) {
		super(constantBlockModel, constantBlockView);
	}
}
