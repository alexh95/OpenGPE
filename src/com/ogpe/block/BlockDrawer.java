package com.ogpe.block;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface BlockDrawer {

	void drawBlock(Block block, GraphicsContext context);
	
}
