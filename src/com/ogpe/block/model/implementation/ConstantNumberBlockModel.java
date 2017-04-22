package com.ogpe.block.model.implementation;

import java.math.BigDecimal;

import com.ogpe.block.type.DataType;

public class ConstantNumberBlockModel extends ConstantBlockModel<BigDecimal> {

	public ConstantNumberBlockModel() {
		super(DataType.NUMBER, BigDecimal.valueOf(0));
	}
}
