package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.BlockView;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ConstantBlockView extends BlockView<ConstantBlockModel> {

	public ConstantBlockView(ConstantBlockModel constantBlockModel, double x, double y) {
		super(constantBlockModel, x, y, 32, 32);
	}

	@Override
	public void drawBlock(GraphicsContext graphicsContext) {
		graphicsContext.setFill(Color.GREEN);
		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.strokeRect(getX(), getY(), getW(), getH());
	}
}
