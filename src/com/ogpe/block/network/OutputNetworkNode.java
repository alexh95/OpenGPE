package com.ogpe.block.network;

import com.ogpe.block.type.DataType;

import javafx.scene.canvas.GraphicsContext;

public class OutputNetworkNode<T> extends NetworkNode<T> {

	public OutputNetworkNode(DataType dataType) {
		super(dataType);
	}

	@Override
	public T getValue() {
		return getNetworkNode().getValue();
	}

	@Override
	public void drawNode(GraphicsContext graphicsContext) {
		graphicsContext.setFill(getHighlight().getColor());

		double nodeX1 = getX() - 3.5;
		double nodeY1 = getY() - 1.5;
		double nodeX2 = getX() + 3.5;
		double nodeY2 = getY() - 1.5;
		double nodeX3 = getX();
		double nodeY3 = getY() + 2.5;
		graphicsContext.fillPolygon(new double[] { nodeX1, nodeX2, nodeX3 }, new double[] { nodeY1, nodeY2, nodeY3 },
				3);
	}
}
