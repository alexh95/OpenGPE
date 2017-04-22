package com.ogpe.block.model.implementation;

import java.math.BigDecimal;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.network.InputNetworkNode;
import com.ogpe.block.network.NetworkNode;
import com.ogpe.block.network.OutputNetworkNode;
import com.ogpe.block.type.DataType;

public class AdditionBlockModel extends BlockModel {

	private OutputNetworkNode<BigDecimal> firstOperandOutputNetworkNode;
	private OutputNetworkNode<BigDecimal> secondOperandOutputNetworkNode;

	private InputNetworkNode<BigDecimal> resultInputNetworkNode;

	public AdditionBlockModel() {
		super();
		firstOperandOutputNetworkNode = new OutputNetworkNode<>(DataType.NUMBER);
		secondOperandOutputNetworkNode = new OutputNetworkNode<>(DataType.NUMBER);
		resultInputNetworkNode = new InputNetworkNode<>(DataType.NUMBER, () -> getResult());
		addNetworkNode(firstOperandOutputNetworkNode);
		addNetworkNode(secondOperandOutputNetworkNode);
		addNetworkNode(resultInputNetworkNode);
	}

	private BigDecimal getResult() {
		BigDecimal firstOperand = firstOperandOutputNetworkNode.getValue();
		BigDecimal secondOperand = secondOperandOutputNetworkNode.getValue();
		return firstOperand.add(secondOperand);
	}

	public OutputNetworkNode<BigDecimal> getFirstOperandNetworkNode() {
		return firstOperandOutputNetworkNode;
	}

	public void setFirstOperandNetworkNode(NetworkNode<BigDecimal> firstOperandNetworkNode) {
		firstOperandOutputNetworkNode.setNetworkNode(firstOperandNetworkNode);
	}

	public OutputNetworkNode<BigDecimal> getSecondOperandNetworkNode() {
		return secondOperandOutputNetworkNode;
	}

	public void setSecondOperandNetworkNode(NetworkNode<BigDecimal> secondOperandNetworkNode) {
		secondOperandOutputNetworkNode.setNetworkNode(secondOperandNetworkNode);
	}

	public InputNetworkNode<BigDecimal> getResultInputNetworkNode() {
		return resultInputNetworkNode;
	}
}
