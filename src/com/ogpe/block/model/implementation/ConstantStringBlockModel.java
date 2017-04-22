package com.ogpe.block.model.implementation;

import com.ogpe.block.type.DataType;

public class ConstantStringBlockModel extends ConstantBlockModel<String> {

	public ConstantStringBlockModel() {
		super(DataType.STRING, "text");
	}
}
