package com.ogpe.block.implementation;

import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantBooleanBlockView;

public class ConstantBooleanBlock extends ConstantBlock<Boolean> {

	public ConstantBooleanBlock(ConstantBlockModel<Boolean> constantBlockModel) {
		super(constantBlockModel, new ConstantBooleanBlockView(constantBlockModel));
	}
}
