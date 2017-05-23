package com.ogpe.blockx.factory.number;

import java.math.BigDecimal;
import java.util.Map;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.DataType;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Rectangle;
import com.ogpe.blockx.factory.BlockFactory;
import com.ogpe.blockx.wire.WireNode;
import com.ogpe.blockx.wire.WireNodeType;
import com.ogpe.observable.Callback;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class NumberValueBlockFactory extends BlockFactory {

	public static final String OUTPUT_KEY = "output";

	public NumberValueBlockFactory() {
		super(new Point(46, 22));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(OUTPUT_KEY, new WireNode(WireNodeType.OUTPUT, DataType.NUMBER, new Point(23.5, size.y - 2.5),
				() -> BigDecimal.valueOf(0)));
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		if (block.isEditing()) {
			context.setFill(Color.FUCHSIA);
		} else {
			context.setFill(Color.BLACK);
		}
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = ((BigDecimal) block.getWireNodes().get(OUTPUT_KEY).provide()).toString();
		double textX = rect.x + Math.round(rect.w / 2);
		double textY = rect.y + Math.round(rect.h / 2) - 3;
		context.fillText(text, textX, textY);
	}

	@Override
	protected Node produceEditingPane(Block block, Callback redrawCallback) {
		VBox editingPane = new VBox();
		Label numberValueLabel = new Label("Value:");
		TextField numberValueTextField = new TextField();
		Label numberValueResultLabel = new Label("");
		editingPane.getChildren().addAll(numberValueLabel, numberValueTextField, numberValueResultLabel);

		BigDecimal currentNumberValue = (BigDecimal) block.getWireNodes().get(OUTPUT_KEY).provide();
		String currentValue = currentNumberValue.toString();
		numberValueTextField.setText(currentValue);

		numberValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				numberValueTextField.setText(oldValue);
			} else {
				String value = newValue;
				if (newValue.isEmpty()) {
					value = "0";
				}
				BigDecimal numberValue = new BigDecimal(value);
				block.getWireNodes().get(OUTPUT_KEY).setProvider(() -> numberValue);
				redrawCallback.callback();
			}
		});

		return editingPane;
	}

}
