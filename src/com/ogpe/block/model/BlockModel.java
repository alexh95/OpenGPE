package com.ogpe.block.model;

import java.util.ArrayList;
import java.util.List;

import com.ogpe.block.network.NetworkNode;

public abstract class BlockModel {

	private List<NetworkNode<?>> networkNodes;

	public BlockModel() {
		networkNodes = new ArrayList<>();
	}

	protected void addNetworkNode(NetworkNode<?> networkNode) {
		networkNodes.add(networkNode);
	}

	public List<NetworkNode<?>> getNetworkNodes() {
		return networkNodes;
	}
}
