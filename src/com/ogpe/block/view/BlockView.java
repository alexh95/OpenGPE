package com.ogpe.block.view;

import com.ogpe.block.behaviour.Provider;
import com.ogpe.block.model.BlockModel;

import javafx.scene.canvas.GraphicsContext;

public abstract class BlockView<T extends BlockModel> {

	private Provider<T> blockModelProvider;

	private double x;
	private double y;
	private double w;
	private double h;

	public BlockView(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public abstract void drawBlock(GraphicsContext graphicsContext);

	public Provider<T> getBlockModelProvider() {
		return blockModelProvider;
	}

	public void setBlockModelProvider(Provider<T> blockModelProvider) {
		this.blockModelProvider = blockModelProvider;
	}

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
