package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.network.NetworkNode;

public class PrintBlockModel extends BlockModel {

	private NetworkNode<? extends Object> printValueNetworkNode;

	public PrintBlockModel() {
		super();
	}

	public Object getPrintValue() {
		return printValueNetworkNode.getValue();
	}

	public NetworkNode<? extends Object> getPrintValueNetworkNode() {
		return printValueNetworkNode;
	}

	public void setPrintValueNetworkNode(NetworkNode<? extends Object> printValueNetworkNode) {
		this.printValueNetworkNode = printValueNetworkNode;
	}
}
