package com.ogpe.block.behaviour.implementation;

import java.math.BigDecimal;

import com.ogpe.block.model.implementation.ConstantBlockModel;

public class ConstantNumberBlockBehavior extends ConstantBlockBehavior<BigDecimal> {

	public ConstantNumberBlockBehavior(ConstantBlockModel<BigDecimal> constantBlockModel) {
		super(constantBlockModel);
	}
}
