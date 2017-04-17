package com.ogpe.block.wire;

import com.ogpe.block.model.BlockModel;

public class WireNodeTarget {

	private BlockModel blockModel;

	private double x;
	private double y;

	private boolean highlight;

	public WireNodeTarget(double x, double y) {
		this.x = x;
		this.y = y;

		highlight = false;
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

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
}
