package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.PrintBlockModel;
import com.ogpe.block.view.BlockView;
import com.ogpe.observable.Observable;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class PrintBlockView extends BlockView<PrintBlockModel> {

	public static final double WIDTH = 42;
	public static final double HEIGHT = 22;

	public PrintBlockView(PrintBlockModel printBlockModel) {
		super(printBlockModel, WIDTH, HEIGHT);
		addXObserver(x -> getBlockModel().getPrintValueNetworkNode().setX(x + getEW() / 2));
		addYObserver(y -> getBlockModel().getPrintValueNetworkNode().setY(y + 2.5));
	}

	@Override
	public void drawBlock(GraphicsContext graphicsContext) {
		if (isMoving()) {
			graphicsContext.setFill(Color.YELLOW);
		} else {
			graphicsContext.setFill(Color.YELLOWGREEN);
		}
		double rectX = getX();
		double rectY = getY();
		double rectW = getW();
		double rectH = getH();
		graphicsContext.fillRect(rectX, rectY, rectW, rectH);

		if (isSelected()) {
			graphicsContext.setStroke(Color.RED);
		} else {
			graphicsContext.setStroke(Color.BLACK);
		}

		double borderRectX = getX();
		double borderRectY = getY();
		double borderRectW = getW();
		double borderRectH = getH();
		graphicsContext.strokeRect(borderRectX + 0.5, borderRectY + 0.5, borderRectW, borderRectH);

		double portX1 = getX() + Math.round(getW() / 2) - 5;
		double portY1 = getY();
		double portX2 = getX() + Math.round(getW() / 2);
		double portY2 = getY() + 5;
		double portX3 = getX() + Math.round(getW() / 2) + 5;
		double portY3 = getY();
		graphicsContext.strokePolyline(new double[] { portX1 + 0.5, portX2 + 0.5, portX3 + 0.5 },
				new double[] { portY1 + 0.5, portY2 + 0.5, portY3 + 0.5 }, 3);

		graphicsContext.setFill(Color.BLACK);
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.setTextBaseline(VPos.CENTER);
		String text = "Print";
		double textX = getX() + Math.round(getW() / 2);
		double textY = getY() + Math.round(getH() / 2) + 3;
		graphicsContext.fillText(text, textX, textY);
	}

	@Override
	public Node getEditingPane(Observable<?> redrawObservable) {
		VBox editingPane = new VBox();
		return editingPane;
	}
}
