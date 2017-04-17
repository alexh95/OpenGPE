package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.AdditionBlockModel;
import com.ogpe.block.view.BlockView;
import com.ogpe.observable.Observable;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class AdditionBlockView extends BlockView<AdditionBlockModel> {

	public static final double WIDTH = 25;
	public static final double HEIGHT = 20;
	
	public AdditionBlockView(AdditionBlockModel additionBlockModel, double x, double y) {
		super(additionBlockModel, x, y, WIDTH, HEIGHT);
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

		double inPort1X1 = getX() + Math.round(getW() / 4) - 5;
		double inPort1Y1 = getY();
		double inPort1X2 = getX() + Math.round(getW() / 4);
		double inPort1Y2 = getY() + 5;
		double inPort1X3 = getX() + Math.round(getW() / 4) + 5;
		double inPort1Y3 = getY();
		graphicsContext.strokePolyline(new double[] { inPort1X1 + 0.5, inPort1X2 + 0.5, inPort1X3 + 0.5 },
				new double[] { inPort1Y1 + 0.5, inPort1Y2 + 0.5, inPort1Y3 + 0.5 }, 3);
		
		double inPort2X1 = getX() + Math.round(3 * getW() / 4) - 5;
		double inPort2Y1 = getY();
		double inPort2X2 = getX() + Math.round(3 * getW() / 4);
		double inPort2Y2 = getY() + 5;
		double inPort2X3 = getX() + Math.round(3 * getW() / 4) + 5;
		double inPort2Y3 = getY();
		graphicsContext.strokePolyline(new double[] { inPort2X1 + 0.5, inPort2X2 + 0.5, inPort2X3 + 0.5 },
				new double[] { inPort2Y1 + 0.5, inPort2Y2 + 0.5, inPort2Y3 + 0.5 }, 3);
		
		double outPortX = getX() + Math.round(getW() / 2) - 5;
		double outPortY = getY() + getH() - 5;
		double outPortW = 10;
		double outPortH = 5;
		graphicsContext.strokeRect(outPortX + 0.5, outPortY + 0.5, outPortW, outPortH);

		graphicsContext.setFill(graphicsContext.getStroke());
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.setTextBaseline(VPos.CENTER);
		String text = "+";
		double textX = getX() + Math.round(getW() / 2);
		double textY = getY() + Math.round(getH() / 2) - 3;
		graphicsContext.fillText(text, textX, textY);
	}

	@Override
	protected Node getEditingPane(Observable observable) {
		VBox editingPane = new VBox();
		return editingPane;
	}
}
