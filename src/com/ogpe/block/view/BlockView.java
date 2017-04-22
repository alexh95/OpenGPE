package com.ogpe.block.view;

import com.ogpe.block.model.BlockModel;
import com.ogpe.observable.Observable;
import com.ogpe.observable.Observer;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

public abstract class BlockView<M extends BlockModel> {

	private M blockModel;

	private Observable<Double> xObservable;
	private Observable<Double> yObservable;

	private double x;
	private double y;
	private double w;
	private double h;

	private boolean selected;
	private boolean moving;

	public BlockView(M blockModel, double w, double h) {
		this.blockModel = blockModel;
		xObservable = new Observable<>();
		yObservable = new Observable<>();
		setX(0);
		setY(0);
		setW(w);
		setH(h);
		setSelected(false);
		setMoving(false);
	}

	public abstract void drawBlock(GraphicsContext graphicsContext);

	public void clearEditPanel(Group panel) {
		panel.getChildren().clear();
	}

	public void populateEditPanel(Observable<?> observable, Group panel) {
		Node editingPanel = getEditingPane(observable);
		clearEditPanel(panel);
		panel.getChildren().add(editingPanel);
	};

	protected abstract Node getEditingPane(Observable<?> observable);

	public boolean isInside(double x, double y) {
		return this.x <= x && x <= this.x + this.w && this.y <= y && y <= this.y + this.h;
	}

	public boolean intersects(double x, double y, double w, double h) {
		return this.x <= x + w && this.x + this.w >= x && this.y <= y + h && this.y + this.h >= y;
	}

	protected M getBlockModel() {
		return blockModel;
	}

	public void addXObserver(Observer<Double> observer) {
		xObservable.addObserver(observer);
	}

	public void addYObserver(Observer<Double> observer) {
		yObservable.addObserver(observer);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		xObservable.updateObservers(x);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		yObservable.updateObservers(y);
	}

	public double getW() {
		return w;
	}
	
	public double getEW() {
		return w + 1;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}
	
	public double getEH() {
		return h + 1;
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
