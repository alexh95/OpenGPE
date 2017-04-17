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

	private boolean selected;
	private boolean moving;

	public BlockView(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		setSelected(false);
		setMoving(false);
	}

	public abstract void drawBlock(GraphicsContext graphicsContext);

	public boolean isInside(double x, double y) {
		return this.x <= x && x <= this.x + this.w && this.y <= y && y <= this.y + this.h;
	}

	public boolean intersects(double x, double y, double w, double h) {
		return this.x <= x + w && this.x + this.w >= x && this.y <= y + h && this.y + this.h >= y;
	}

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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
}
