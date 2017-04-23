package com.ogpe.block.network;

import com.ogpe.block.type.DataType;
import com.ogpe.requester.Requester;

import javafx.scene.canvas.GraphicsContext;

public class InputNetworkNode<T> extends NetworkNode<T> {

	private Requester<T> valueRequester;

	public InputNetworkNode(DataType dataType, Requester<T> valueRequester) {
		super(dataType, NetworkNodeType.INPUT);
		this.valueRequester = valueRequester;
	}

	@Override
	public T getValue() {
		return valueRequester.request();
	}

	@Override
	public void drawNode(GraphicsContext graphicsContext) {
		graphicsContext.setFill(getHighlight().getColor());

		double nodeX = getX() - 4.5;
		double nodeY = getY() - 2.5;
		double nodeW = 9;
		double nodeH = 4;
		graphicsContext.fillRect(nodeX, nodeY, nodeW, nodeH);
	}
}
