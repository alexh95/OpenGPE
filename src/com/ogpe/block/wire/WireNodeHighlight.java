package com.ogpe.block.wire;

import javafx.scene.paint.Color;

public enum WireNodeHighlight {
	
	UNSET(Color.YELLOW),
	SET(Color.BLACK),
	WIRING(Color.GREEN),
	HOVERING(Color.BLUE),
	HOVERING_WIRING_VALID(Color.BLUE),
	HOVERING_WIRING_INVALID(Color.RED);
	
	private final Color color;

	private WireNodeHighlight(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	};
}
