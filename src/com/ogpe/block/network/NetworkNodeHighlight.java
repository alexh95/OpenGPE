package com.ogpe.block.network;

import javafx.scene.paint.Color;

public enum NetworkNodeHighlight {
	UNSET(Color.YELLOW),
	SET(Color.BLACK),
	HOVER(Color.BLUE),
	WIRING(Color.GREEN),
	HOVER_VALID_WIRING(Color.BLUE),
	HOVER_INVALID_WIRING(Color.RED);
	
	private Color color;

	private NetworkNodeHighlight(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	};
}
