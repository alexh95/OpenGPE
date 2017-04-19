package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.BlockView;

public abstract class ConstantBlockView<T> extends BlockView<ConstantBlockModel<T>> {

	public ConstantBlockView(double x, double y, double w, double h) {
		super(x, y, w, h);
	}
}
