package com.ogpe.block.view.implementation;

import com.ogpe.block.model.implementation.ConstantStringBlockModel;
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

public class ConstantStringBlockView extends ConstantBlockView<String> {

	public static final double WIDTH = 64;
	public static final double HEIGHT = 21;

	public ConstantStringBlockView(ConstantStringBlockModel constantStringBlockModel) {
		super(constantStringBlockModel, WIDTH, HEIGHT);
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

		if (isEdited()) {
			graphicsContext.setFill(Color.FUCHSIA);
		} else {
			graphicsContext.setFill(Color.BLACK);
		}
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.setTextBaseline(VPos.CENTER);
		String text = "\"" + getBlockModel().getConstantValue() + "\"";
		double textX = getX() + Math.round(getW() / 2);
		double textY = getY() + Math.round(getH() / 2) - 2;
		graphicsContext.fillText(text, textX, textY);
	}

	@Override
	public Node getEditingPane(Observable<?> redrawObservable) {
		VBox editingPane = new VBox();
		Label stringValueLabel = new Label("Value:");
		TextField stringValueTextField = new TextField();
		Label stringValueResultLabel = new Label("");
		Button updateButton = new Button("Update");
		editingPane.getChildren().addAll(stringValueLabel, stringValueTextField, stringValueResultLabel, updateButton);

		String currentStringValue = getBlockModel().getConstantValue();
		stringValueTextField.setText(currentStringValue);

		updateButton.setOnAction(event -> {
			String value = stringValueTextField.getText();
			getBlockModel().setConstantValue(value);
			redrawObservable.updateObservers(null);
		});
		return editingPane;
	}
}
