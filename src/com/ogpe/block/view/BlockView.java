package com.ogpe.block.view;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.model.BlockModelContainer;

import javafx.scene.canvas.GraphicsContext;

public abstract class BlockView<T extends BlockModel> extends BlockModelContainer<T> {

	private double x;
	private double y;
	private double w;
	private double h;

	public BlockView(T blockModel) {
		super(blockModel);
	}

	public abstract void drawBlock(GraphicsContext graphicsContext);

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}
}
