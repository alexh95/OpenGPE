package com.ogpe.fx;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface Drawer {
	
	void draw(GraphicsContext context);
	
}
