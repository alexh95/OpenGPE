package com.ogpe.block.network;

import com.ogpe.block.type.DataType;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class NetworkNode<T> {

	private DataType dataType;

	private NetworkNode<T> networkNode;

	private double x;
	private double y;

	private NetworkNodeHighlight highlight;

	public NetworkNode(DataType dataType) {
		this.dataType = dataType;
		setX(0);
		setY(0);
		setHighlighted(NetworkNodeHighlight.UNSET);
	}

	public abstract T getValue();

	public abstract void drawNode(GraphicsContext graphicsContext);
	
	public void drawWire(GraphicsContext graphicsContext) {
		if (networkNode != null) {
			graphicsContext.setStroke(Color.BLACK);
			graphicsContext.strokeLine(x, y, networkNode.getX(), networkNode.getY());
		}
	}

	public DataType getDataType() {
		return dataType;
	}

	public NetworkNode<T> getNetworkNode() {
		return networkNode;
	}

	public boolean isAssignable(NetworkNode<?> networkNode) {
		return dataType.assignable(networkNode.dataType);
	}

	@SuppressWarnings("unchecked")
	public void setNetworkNode(NetworkNode<?> networkNode) {
		if (isAssignable(networkNode)) {
			this.networkNode = (NetworkNode<T>) networkNode;
		} else {
			throw new RuntimeException("Incompatible DataType");
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public NetworkNodeHighlight getHighlight() {
		return highlight;
	}

	public void setHighlighted(NetworkNodeHighlight highlight) {
		this.highlight = highlight;
	}
}
