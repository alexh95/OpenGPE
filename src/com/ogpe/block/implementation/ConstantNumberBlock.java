package com.ogpe.block.implementation;

import java.math.BigDecimal;

import com.ogpe.block.model.implementation.ConstantNumberBlockModel;
import com.ogpe.block.view.implementation.ConstantNumberBlockView;

public class ConstantNumberBlock extends ConstantBlock<BigDecimal> {

	public ConstantNumberBlock(ConstantNumberBlockModel constantNumberBlockModel) {
		super(constantNumberBlockModel, new ConstantNumberBlockView(constantNumberBlockModel));
	}
}
