package com.ogpe.blockx.wire;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface WireNodeDrawer {

	void drawWireNode(WireNode wireNode, GraphicsContext context);
	
}
