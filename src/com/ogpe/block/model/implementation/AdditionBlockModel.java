package com.ogpe.block.model.implementation;

import java.math.BigDecimal;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.network.InputNetworkNode;
import com.ogpe.block.network.NetworkNode;

public class AdditionBlockModel extends BlockModel {

	private NetworkNode<BigDecimal> firstOperandNetworkNode;
	private NetworkNode<BigDecimal> secondOperandNetworkNode;

	private InputNetworkNode<BigDecimal> resultInputNetworkNode;

	public AdditionBlockModel() {
		super();
		resultInputNetworkNode = new InputNetworkNode<>(() -> getResult());
	}

	private BigDecimal getResult() {
		BigDecimal firstOperand = firstOperandNetworkNode.getValue();
		BigDecimal secondOperand = secondOperandNetworkNode.getValue();
		return firstOperand.add(secondOperand);
	}

	public NetworkNode<BigDecimal> getFirstOperandNetworkNode() {
		return firstOperandNetworkNode;
	}

	public void setFirstOperandNetworkNode(NetworkNode<BigDecimal> firstOperandNetworkNode) {
		this.firstOperandNetworkNode = firstOperandNetworkNode;
	}

	public NetworkNode<BigDecimal> getSecondOperandNetworkNode() {
		return secondOperandNetworkNode;
	}

	public void setSecondOperandNetworkNode(NetworkNode<BigDecimal> secondOperandNetworkNode) {
		this.secondOperandNetworkNode = secondOperandNetworkNode;
	}

	public InputNetworkNode<BigDecimal> getResultInputNetworkNode() {
		return resultInputNetworkNode;
	}
}
