package com.ogpe.block.implementation;

import com.ogpe.block.model.implementation.ConstantStringBlockModel;
import com.ogpe.block.view.implementation.ConstantStringBlockView;

public class ConstantStringBlock extends ConstantBlock<String> {

	public ConstantStringBlock(ConstantStringBlockModel constantStringBlockModel) {
		super(constantStringBlockModel, new ConstantStringBlockView(constantStringBlockModel));
	}
}
