package com.ogpe.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.factory.BlockFactory;
import com.ogpe.block.implementation.AdditionBlock;
import com.ogpe.block.implementation.ConstantNumberBlock;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.implementation.PrintBlock;

public class NetworkTest {

	@Test
	public void testConstantStringPrint() {
		String printValue = "test print value";
		
		BlockFactory blockFactory = new BlockFactory();
		ConstantStringBlock constantStringBlock = blockFactory.makeConstantStringBlock(0, 0);
		PrintBlock printBlock = blockFactory.makePrintBlock(0, 0);
		
		constantStringBlock.getBlockModel().setConstantValue(printValue);
		printBlock.getBlockModel().setPrintValueNetworkNode(constantStringBlock.getBlockModel().getConstantValueInputNetworkNode());
		Object returnedValue = printBlock.getBlockModel().getPrintValue();
		
		Assert.assertEquals(printValue, returnedValue);
	}
	
	@Test
	public void testAddition() {
		BigDecimal firstOperand = BigDecimal.valueOf(3.4);
		BigDecimal secondOperand = BigDecimal.valueOf(-1.2);
		BigDecimal additionResult = firstOperand.add(secondOperand);

		BlockFactory blockFactory = new BlockFactory();
		
		ConstantNumberBlock firstOperandConstantNumberBlock = blockFactory.makeConstantNumberBlock(0, 0);
		firstOperandConstantNumberBlock.getBlockModel().setConstantValue(firstOperand);
		
		ConstantNumberBlock secondOperandConstantNumberBlock = blockFactory.makeConstantNumberBlock(0, 0);
		secondOperandConstantNumberBlock.getBlockModel().setConstantValue(secondOperand);
		
		AdditionBlock additionBlock = blockFactory.makeAdditionBlock(0, 0);
		additionBlock.getBlockModel().setFirstOperandNetworkNode(firstOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		additionBlock.getBlockModel().setSecondOperandNetworkNode(secondOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		
		PrintBlock printBlock = blockFactory.makePrintBlock(0, 0);
		printBlock.getBlockModel().setPrintValueNetworkNode(additionBlock.getBlockModel().getResultInputNetworkNode());
		BigDecimal returnedValue = (BigDecimal) printBlock.getBlockModel().getPrintValue();
		
		Assert.assertEquals(additionResult, returnedValue);
	}
	
	@Test
	public void test() {
		BigDecimal firstOperand = BigDecimal.valueOf(3.4);
		BigDecimal secondOperand = BigDecimal.valueOf(-1.2);

		BlockFactory blockFactory = new BlockFactory();
		
		ConstantNumberBlock firstOperandConstantNumberBlock = blockFactory.makeConstantNumberBlock(0, 0);
		firstOperandConstantNumberBlock.getBlockModel().setConstantValue(firstOperand);
		
		ConstantNumberBlock secondOperandConstantNumberBlock = blockFactory.makeConstantNumberBlock(0, 0);
		secondOperandConstantNumberBlock.getBlockModel().setConstantValue(secondOperand);
		
		AdditionBlock additionBlock = blockFactory.makeAdditionBlock(0, 0);
		additionBlock.getBlockModel().setFirstOperandNetworkNode(firstOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		additionBlock.getBlockModel().setSecondOperandNetworkNode(secondOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		
		PrintBlock printBlock = blockFactory.makePrintBlock(0, 0);
		printBlock.getBlockModel().setPrintValueNetworkNode(additionBlock.getBlockModel().getResultInputNetworkNode());
		
		printBlock.run();
	}
}
