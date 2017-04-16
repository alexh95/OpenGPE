package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;

public abstract class ConstantBlockModel<T> extends BlockModel {

	private T constantValue;

	public ConstantBlockModel(T constantValue) {
		this.constantValue = constantValue;
	}

	public T getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(T constantValue) {
		this.constantValue = constantValue;
	}

}
