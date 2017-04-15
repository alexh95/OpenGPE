package com.ogpe.block.view;

import com.ogpe.block.model.BlockModel;
import com.ogpe.requester.Requester;

import javafx.scene.canvas.GraphicsContext;

public abstract class BlockView<M extends BlockModel> {

	private Requester<M> blockModelRequester;

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

	public Requester<M> getBlockModelRequester() {
		return blockModelRequester;
	}

	public void setBlockModelRequester(Requester<M> blockModelRequester) {
		this.blockModelRequester = blockModelRequester;
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
