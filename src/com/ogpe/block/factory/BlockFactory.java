package com.ogpe.block.factory;

import com.ogpe.block.implementation.AdditionBlock;
import com.ogpe.block.implementation.ConstantBooleanBlock;
import com.ogpe.block.implementation.ConstantNumberBlock;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.implementation.PrintBlock;
import com.ogpe.block.model.implementation.AdditionBlockModel;
import com.ogpe.block.model.implementation.ConstantBooleanBlockModel;
import com.ogpe.block.model.implementation.ConstantNumberBlockModel;
import com.ogpe.block.model.implementation.ConstantStringBlockModel;
import com.ogpe.block.model.implementation.PrintBlockModel;

public class BlockFactory {

	public ConstantBooleanBlock makeConstantBooleanBlock(double x, double y) {
		ConstantBooleanBlock constantBooleanBlock = new ConstantBooleanBlock(new ConstantBooleanBlockModel());
		constantBooleanBlock.getBlockView().setX(x);
		constantBooleanBlock.getBlockView().setY(y);
		return constantBooleanBlock;
	}

	public ConstantNumberBlock makeConstantNumberBlock(double x, double y) {
		ConstantNumberBlock constantNumberBlock = new ConstantNumberBlock(new ConstantNumberBlockModel());
		constantNumberBlock.getBlockView().setX(x);
		constantNumberBlock.getBlockView().setY(y);
		return constantNumberBlock;
	}

	public ConstantStringBlock makeConstantStringBlock(double x, double y) {
		ConstantStringBlock constantStringBlock = new ConstantStringBlock(new ConstantStringBlockModel());
		constantStringBlock.getBlockView().setX(x);
		constantStringBlock.getBlockView().setY(y);
		return constantStringBlock;
	}

	public AdditionBlock makeAdditionBlock(double x, double y) {
		AdditionBlock additionBlock = new AdditionBlock(new AdditionBlockModel());
		additionBlock.getBlockView().setX(x);
		additionBlock.getBlockView().setY(y);
		return additionBlock;
	}

	public PrintBlock makePrintBlock(double x, double y) {
		PrintBlock printBlock = new PrintBlock(new PrintBlockModel());
		printBlock.getBlockView().setX(x);
		printBlock.getBlockView().setY(y);
		return printBlock;
	}
}
