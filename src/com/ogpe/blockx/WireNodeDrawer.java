package com.ogpe.blockx;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface WireNodeDrawer {

	void drawWireNode(WireNode wireNode, GraphicsContext context);
	
}
