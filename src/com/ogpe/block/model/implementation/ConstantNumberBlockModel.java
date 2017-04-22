package com.ogpe.block.model.implementation;

import java.math.BigDecimal;

public class ConstantNumberBlockModel extends ConstantBlockModel<BigDecimal> {

	public ConstantNumberBlockModel() {
		super(BigDecimal.valueOf(0));
	}
}
