package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.network.InputNetworkNode;

public abstract class ConstantBlockModel<T> extends BlockModel {

	private T constantValue;

	private InputNetworkNode<T> constantValueInputNetworkNode;

	public ConstantBlockModel(T constantValue) {
		super();
		setConstantValue(constantValue);
		constantValueInputNetworkNode = new InputNetworkNode<>(() -> getConstantValue());
	}

	public T getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(T constantValue) {
		this.constantValue = constantValue;
	}

	public InputNetworkNode<T> getConstantValueInputNetworkNode() {
		return constantValueInputNetworkNode;
	}
}
