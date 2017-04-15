package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;

public class ConstantBlockModel extends BlockModel {

	private Integer constantValue;

	public ConstantBlockModel(Integer constantValue) {
		this.constantValue = constantValue;
	}

	public Integer getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(Integer constantValue) {
		this.constantValue = constantValue;
	}

}
