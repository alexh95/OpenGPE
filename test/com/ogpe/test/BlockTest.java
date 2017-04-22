package com.ogpe.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.factory.BlockFactory;
import com.ogpe.block.implementation.AdditionBlock;
import com.ogpe.block.implementation.ConstantBooleanBlock;
import com.ogpe.block.implementation.ConstantNumberBlock;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.implementation.PrintBlock;
import com.ogpe.block.network.InputNetworkNode;
import com.ogpe.block.type.DataType;

public class BlockTest {

	@Test
	public void testConstantBooleanBlock() {
		Boolean constantBooleanValue = true;

		BlockFactory blockFactory = new BlockFactory();
		ConstantBooleanBlock constantBooleanBlock = blockFactory.makeConstantBooleanBlock(0, 0);
		constantBooleanBlock.getBlockModel().setConstantValue(constantBooleanValue);
		Boolean returnedValue = constantBooleanBlock.getBlockModel().getConstantValueInputNetworkNode().getValue();
		
		Assert.assertEquals(constantBooleanValue, returnedValue);
	}

	@Test
	public void testConstantNumberBlock() {
		BigDecimal constantNumberValue = BigDecimal.valueOf(5);

		BlockFactory blockFactory = new BlockFactory();
		ConstantNumberBlock constantNumberBlock = blockFactory.makeConstantNumberBlock(0, 0);
		constantNumberBlock.getBlockModel().setConstantValue(constantNumberValue);
		BigDecimal returnedValue = constantNumberBlock.getBlockModel().getConstantValueInputNetworkNode().getValue();
		
		Assert.assertEquals(constantNumberValue, returnedValue);
	}

	@Test
	public void testConstantStringBlock() {
		String constantStringValue = "test string";

		BlockFactory blockFactory = new BlockFactory();
		ConstantStringBlock constantStringBlock = blockFactory.makeConstantStringBlock(0, 0);
		constantStringBlock.getBlockModel().setConstantValue(constantStringValue);
		String returnedValue = constantStringBlock.getBlockModel().getConstantValueInputNetworkNode().getValue();
		
		Assert.assertEquals(constantStringValue, returnedValue);
	}

	@Test
	public void testAdditionBlock() {
		BigDecimal firstOperand = BigDecimal.valueOf(3.4);
		BigDecimal secondOperand = BigDecimal.valueOf(-1.2);
		BigDecimal additionResult = firstOperand.add(secondOperand);

		BlockFactory blockFactory = new BlockFactory();
		AdditionBlock additionBlock = blockFactory.makeAdditionBlock(0, 0);
		additionBlock.getBlockModel().setFirstOperandNetworkNode(new InputNetworkNode<>(DataType.NUMBER, () -> firstOperand));
		additionBlock.getBlockModel().setSecondOperandNetworkNode(new InputNetworkNode<>(DataType.NUMBER, () -> secondOperand));
		BigDecimal returnedValue = additionBlock.getBlockModel().getResultInputNetworkNode().getValue();
		
		Assert.assertEquals(additionResult, returnedValue);
	}

	@Test
	public void testPrintBlock() {
		String printValue = "test print value";

		BlockFactory blockFactory = new BlockFactory();
		PrintBlock printBlock = blockFactory.makePrintBlock(0, 0);
		printBlock.getBlockModel().setPrintValueNetworkNode(new InputNetworkNode<>(DataType.NUMBER, () -> printValue));
		Object returnedValue = printBlock.getBlockModel().getPrintValueNetworkNode().getValue();
		
		Assert.assertEquals(printValue, returnedValue);
	}
}
