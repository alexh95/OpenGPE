package com.ogpe.block.network;

import com.ogpe.block.type.DataType;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ThroughputNetworkNode<T> extends NetworkNode<T> {

	public ThroughputNetworkNode(DataType dataType) {
		super(dataType);
	}

	@Override
	public T getValue() {
		return getNetworkNode().getValue();
	}

	@Override
	public void drawNode(GraphicsContext graphicsContext) {
		graphicsContext.setFill(getHighlight().getColor());

		graphicsContext.setFill(Color.RED);
		double nodeX = getX() - 2;
		double nodeY = getY() - 2;
		double nodeW = 2;
		double nodeH = 2;
		graphicsContext.fillOval(nodeX, nodeY, nodeW, nodeH);
	}
}
