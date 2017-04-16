package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.BlockView;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ConstantBlockView extends BlockView<ConstantBlockModel> {
	
	public static final double WIDTH = 32;
	public static final double HEIGHT = 22;
	
	public ConstantBlockView(double x, double y) {
		super(x, y, WIDTH, HEIGHT);
	}

	@Override
	public void drawBlock(GraphicsContext graphicsContext) {
		graphicsContext.setFill(Color.YELLOW);
		graphicsContext.fillRect(getX(), getY(), getW(), getH());
		
		if (isSelected()) {
			graphicsContext.setStroke(Color.RED);
		} else {
			graphicsContext.setStroke(Color.BLACK);
		}
		graphicsContext.strokeRect(getX(), getY(), getW(), getH());
		
		double portX = getX() + (WIDTH - 10) / 2;
		double portY = getY() + HEIGHT - 5;
		double portW = 10;
		double portH = 5;
		graphicsContext.strokeRect(portX, portY, portW, portH);
		
		String text = getBlockModelRequester().request().getConstantValue().toString();
		double textX = getX() + 2;
		double textY = getY() + HEIGHT / 2;
		graphicsContext.strokeText(text, textX, textY);
	}
}
