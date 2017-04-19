package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.wire.WireNodeTarget;
import com.ogpe.block.wire.WireNodeTargetType;
import com.ogpe.requester.Requester;

public abstract class ConstantBlockModel<T> extends BlockModel {

	private T constantValue;
	
	private WireNodeTarget<T> constantValueNodeTarget;

	public ConstantBlockModel(T constantValue) {
		super();
		this.constantValue = constantValue;
		constantValueNodeTarget = new WireNodeTarget<>(WireNodeTargetType.SOURCE);
		constantValueNodeTarget.setRequester(() -> constantValue); 
	}

	public T getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(T constantValue) {
		this.constantValue = constantValue;
	}

	public WireNodeTarget<T> getConstantValueNodeTarget() {
		return constantValueNodeTarget;
	}
	
	public Requester<T> getPrintValueRequester() {
		return constantValueNodeTarget.getRequester();
	}
}
