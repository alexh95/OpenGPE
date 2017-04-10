package com.ogpe.fx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class CanvasPane extends Pane {

	private Canvas canvas;
	private Drawer drawer = graphicsContext -> {
	};

	public CanvasPane() {
		canvas = new Canvas();
		canvas.widthProperty().addListener(observable -> redraw());
		canvas.heightProperty().addListener(observable -> redraw());
		getChildren().add(canvas);
	}

	public void setDrawer(Drawer drawer) {
		this.drawer = drawer;
		redraw();
	}

	public void redraw() {
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		drawer.draw(graphicsContext);
	}

	@Override
	protected void layoutChildren() {
		canvas.setLayoutX(snappedLeftInset());
		canvas.setLayoutY(snappedTopInset());
		canvas.setWidth(snapSize(getWidth()) - canvas.getLayoutX() - snappedRightInset());
		canvas.setHeight(snapSize(getHeight()) - canvas.getLayoutY() - snappedBottomInset());
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
