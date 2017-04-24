package com.ogpe.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.factory.BlockFactory;
import com.ogpe.block.implementation.AdditionBlock;
import com.ogpe.block.implementation.ConstantNumberBlock;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.implementation.PrintBlock;
import com.ogpe.block.network.ThroughputNetworkNode;
import com.ogpe.block.type.DataType;
import com.ogpe.observable.Observable;

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
		
		ThroughputNetworkNode<BigDecimal> firstOperandNode = new ThroughputNetworkNode<>(DataType.NUMBER);
		firstOperandNode.setNetworkNode(firstOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		
		ConstantNumberBlock secondOperandConstantNumberBlock = blockFactory.makeConstantNumberBlock(0, 0);
		secondOperandConstantNumberBlock.getBlockModel().setConstantValue(secondOperand);
		
		ThroughputNetworkNode<BigDecimal> secondOperandNode = new ThroughputNetworkNode<>(DataType.NUMBER);
		secondOperandNode.setNetworkNode(secondOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		
		AdditionBlock additionBlock = blockFactory.makeAdditionBlock(0, 0);
		additionBlock.getBlockModel().setFirstOperandNetworkNode(firstOperandNode.getNetworkNode());
		additionBlock.getBlockModel().setSecondOperandNetworkNode(secondOperandNode.getNetworkNode());
		
		ThroughputNetworkNode<BigDecimal> resultNode = new ThroughputNetworkNode<>(DataType.NUMBER);
		resultNode.setNetworkNode(additionBlock.getBlockModel().getResultInputNetworkNode());
		
		PrintBlock printBlock = blockFactory.makePrintBlock(0, 0);
		printBlock.getBlockModel().setPrintValueNetworkNode(resultNode.getNetworkNode());
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
		
		ThroughputNetworkNode<BigDecimal> firstOperandNode = new ThroughputNetworkNode<>(DataType.NUMBER);
		firstOperandNode.setNetworkNode(firstOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		
		ConstantNumberBlock secondOperandConstantNumberBlock = blockFactory.makeConstantNumberBlock(0, 0);
		secondOperandConstantNumberBlock.getBlockModel().setConstantValue(secondOperand);
		
		ThroughputNetworkNode<BigDecimal> secondOperandNode = new ThroughputNetworkNode<>(DataType.NUMBER);
		secondOperandNode.setNetworkNode(secondOperandConstantNumberBlock.getBlockModel().getConstantValueInputNetworkNode());
		
		AdditionBlock additionBlock = blockFactory.makeAdditionBlock(0, 0);
		additionBlock.getBlockModel().setFirstOperandNetworkNode(firstOperandNode.getNetworkNode());
		additionBlock.getBlockModel().setSecondOperandNetworkNode(secondOperandNode.getNetworkNode());
		
		ThroughputNetworkNode<BigDecimal> resultNode = new ThroughputNetworkNode<>(DataType.NUMBER);
		resultNode.setNetworkNode(additionBlock.getBlockModel().getResultInputNetworkNode());
		
		PrintBlock printBlock = blockFactory.makePrintBlock(0, 0);
		printBlock.getBlockModel().setPrintValueNetworkNode(resultNode.getNetworkNode());
		
		printBlock.run(new Observable<>());
	}
}
