package com.ogpe.block.model.implementation;

import com.ogpe.block.type.DataType;

public class ConstantBooleanBlockModel extends ConstantBlockModel<Boolean> {

	public ConstantBooleanBlockModel() {
		super(DataType.BOOLEAN, false);
	}
}
