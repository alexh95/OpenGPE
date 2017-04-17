package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.observable.Observable;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ConstantBooleanBlockView extends ConstantBlockView<Boolean> {

	public static final double WIDTH = 19;
	public static final double HEIGHT = 20;

	public ConstantBooleanBlockView(ConstantBlockModel<Boolean> constantBlockModel, double x, double y) {
		super(constantBlockModel, x, y, WIDTH, HEIGHT);
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

		double portX = getX() + Math.round(getW() / 2) - 5;
		double portY = getY() + getH() - 5;
		double portW = 10;
		double portH = 5;
		graphicsContext.strokeRect(portX + 0.5, portY + 0.5, portW, portH);

		Boolean value = getBlockModel().getConstantValue();
		String text;
		if (value) {
			graphicsContext.setFill(Color.GREEN);
			text = "T";
		} else {
			graphicsContext.setFill(Color.BLUE);
			text = "F";
		}
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.setTextBaseline(VPos.CENTER);
		double textX = getX() + Math.round(getW() / 2);
		double textY = getY() + Math.round(getH() / 2) - 2;
		graphicsContext.fillText(text, textX, textY);
	}

	@Override
	protected Node getEditingPane(Observable observable) {
		VBox editingPane = new VBox();
		Label booleanValueLabel = new Label("Value:");
		ComboBox<Boolean> booleanValueComboBox = new ComboBox<>();
		Label booleanValueResultLabel = new Label("");
		Button updateButton = new Button("Update");
		editingPane.getChildren().addAll(booleanValueLabel, booleanValueComboBox, booleanValueResultLabel,
				updateButton);

		booleanValueComboBox.getItems().addAll(false, true);
		Boolean currentBooleanValue = getBlockModel().getConstantValue();
		booleanValueComboBox.setValue(currentBooleanValue);

		updateButton.setOnAction(event -> {
			Boolean value = booleanValueComboBox.getValue();
			getBlockModel().setConstantValue(value);
			observable.updateObservers();
		});
		return editingPane;
	}
}
