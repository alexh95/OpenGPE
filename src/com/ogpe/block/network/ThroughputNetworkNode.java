package com.ogpe.block.network;

public class ThroughputNetworkNode<T> extends NetworkNode<T> {

	private NetworkNode<T> networkNode;

	public ThroughputNetworkNode() {
		super();
	}
	
	@Override
	public T getValue() {
		return networkNode.getValue();
	}
	
	public NetworkNode<T> getNetworkNode() {
		return networkNode;
	}

	public void setNetworkNode(NetworkNode<T> networkNode) {
		this.networkNode = networkNode;
	}
}
