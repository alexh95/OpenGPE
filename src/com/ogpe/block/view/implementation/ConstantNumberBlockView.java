package com.ogpe.block.view.implementation;

import java.math.BigDecimal;

import com.ogpe.block.model.implementation.ConstantNumberBlockModel;
import com.ogpe.observable.Observable;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ConstantNumberBlockView extends ConstantBlockView<BigDecimal> {

	public static final double WIDTH = 46;
	public static final double HEIGHT = 21;

	public ConstantNumberBlockView(ConstantNumberBlockModel constantNumberBlockModel) {
		super(constantNumberBlockModel, WIDTH, HEIGHT);
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

		graphicsContext.setFill(graphicsContext.getStroke());
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.setTextBaseline(VPos.CENTER);
		String text = getBlockModel().getConstantValue().toString();
		double textX = getX() + Math.round(getW() / 2);
		double textY = getY() + Math.round(getH() / 2) - 2;
		graphicsContext.fillText(text, textX, textY);
	}

	@Override
	protected Node getEditingPane(Observable<?> observable) {
		VBox editingPane = new VBox();
		Label numberValueLabel = new Label("Value:");
		TextField numberValueTextField = new TextField();
		Label numberValueResultLabel = new Label("");
		Button updateButton = new Button("Update");
		editingPane.getChildren().addAll(numberValueLabel, numberValueTextField, numberValueResultLabel, updateButton);

		BigDecimal currentNumberValue = getBlockModel().getConstantValue();
		String currentValue = currentNumberValue.toString();
		numberValueTextField.setText(currentValue);

		updateButton.setOnAction(event -> {
			String value = numberValueTextField.getText();
			try {
				BigDecimal numberValue = new BigDecimal(value);
				getBlockModel().setConstantValue(numberValue);
				numberValueResultLabel.setText("Value set");
			} catch (NumberFormatException e) {
				numberValueResultLabel.setText("Invalid value");
			}
			observable.updateObservers(null);
		});
		return editingPane;
	}
}
