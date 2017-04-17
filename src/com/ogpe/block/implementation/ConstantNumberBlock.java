package com.ogpe.block.implementation;

import java.math.BigDecimal;

import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.behaviour.implementation.ConstantNumberBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantNumberBlockView;

public class ConstantNumberBlock extends ConstantBlock<BigDecimal> {

	public ConstantNumberBlock(ConstantBlockModel<BigDecimal> constantBlockModel, double x, double y) {
		super(constantBlockModel, new ConstantNumberBlockView(constantBlockModel, x, y));
	}

	@Override
	public ConstantBlockBehavior<BigDecimal> makeBlockBehavior() {
		return new ConstantNumberBlockBehavior(getBlockModel());
	}
}
