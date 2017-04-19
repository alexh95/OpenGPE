package com.ogpe.block.model.implementation;

import java.math.BigInteger;

public class ConstantNumberBlockModel extends ConstantBlockModel<BigInteger> {

	public ConstantNumberBlockModel() {
		super(BigInteger.valueOf(0));
	}
}
