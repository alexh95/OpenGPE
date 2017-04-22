package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.network.NetworkNode;
import com.ogpe.block.network.OutputNetworkNode;
import com.ogpe.block.type.DataType;

public class PrintBlockModel extends BlockModel {

	private OutputNetworkNode<Object> printValueOutputNetworkNode;

	public PrintBlockModel() {
		super();
		printValueOutputNetworkNode = new OutputNetworkNode<>(DataType.ANY);
		addNetworkNode(printValueOutputNetworkNode);
	}

	public Object getPrintValue() {
		return printValueOutputNetworkNode.getValue();
	}

	public OutputNetworkNode<Object> getPrintValueNetworkNode() {
		return printValueOutputNetworkNode;
	}

	@SuppressWarnings("unchecked")
	public void setPrintValueNetworkNode(NetworkNode<?> printValueNetworkNode) {
		printValueOutputNetworkNode.setNetworkNode((NetworkNode<Object>) printValueNetworkNode);
	}
}
