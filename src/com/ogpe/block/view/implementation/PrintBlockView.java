package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.PrintBlockModel;
import com.ogpe.block.view.BlockView;

import javafx.scene.canvas.GraphicsContext;

public class PrintBlockView extends BlockView<PrintBlockModel> {

	public PrintBlockView(double x, double y) {
		super(x, y, 32, 32);
	}

	@Override
	public void drawBlock(GraphicsContext graphicsContext) {

	}
}
