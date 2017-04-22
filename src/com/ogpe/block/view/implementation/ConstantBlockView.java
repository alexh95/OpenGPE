package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.BlockView;

public abstract class ConstantBlockView<T> extends BlockView<ConstantBlockModel<T>> {

	public ConstantBlockView(ConstantBlockModel<T> constantBlockModel, double w, double h) {
		super(constantBlockModel, w, h);
		addXObserver(x -> getBlockModel().getConstantValueInputNetworkNode().setX(x + getEW() / 2));
		addYObserver(y -> getBlockModel().getConstantValueInputNetworkNode().setY(y + getEH() - 2.5));
	}
}
