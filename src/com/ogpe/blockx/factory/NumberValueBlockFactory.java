package com.ogpe.blockx.factory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.BlockDrawer;
import com.ogpe.blockx.BlockRunner;
import com.ogpe.blockx.DataType;
import com.ogpe.blockx.EditingPaneProducer;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Rectangle;
import com.ogpe.blockx.wire.WireNode;
import com.ogpe.blockx.wire.WireNodeType;

import javafx.geometry.VPos;
import javafx.scene.control.Button;
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
	public Block makeBlock(Point position) {
		Map<String, WireNode> wireNodes = new HashMap<>();
		WireNode output = new WireNode(WireNodeType.OUTPUT, DataType.NUMBER, new Point(23.5, size.y - 2.5), () -> {
			return BigDecimal.valueOf(0);
		});
		wireNodes.put(OUTPUT_KEY, output);

		BlockRunner blockRunner = (context) -> {

		};

		Rectangle rectangle = new Rectangle(position).setSize(size);

		BlockDrawer blockDrawer = (block, context) -> {
			Rectangle rect = block.getRectangle();
			if (block.isEditing()) {
				context.setFill(Color.FUCHSIA);
			} else {
				context.setFill(Color.BLACK);
			}
			context.setTextAlign(TextAlignment.CENTER);
			context.setTextBaseline(VPos.CENTER);
			String text = ((BigDecimal) output.provide()).toString();
			double textX = rect.x + Math.round(rect.w / 2);
			double textY = rect.y + Math.round(rect.h / 2) - 3;
			context.fillText(text, textX, textY);
		};

		EditingPaneProducer editingPaneProducer = (redrawCallback) -> {
			VBox editingPane = new VBox();
			Label numberValueLabel = new Label("Value:");
			TextField numberValueTextField = new TextField();
			Label numberValueResultLabel = new Label("");
			Button updateButton = new Button("Update");
			editingPane.getChildren().addAll(numberValueLabel, numberValueTextField, numberValueResultLabel,
					updateButton);

			BigDecimal currentNumberValue = (BigDecimal) output.provide();
			String currentValue = currentNumberValue.toString();
			numberValueTextField.setText(currentValue);

			updateButton.setOnAction(event -> {
				String value = numberValueTextField.getText();
				try {
					BigDecimal numberValue = new BigDecimal(value);
					output.setProvider(() -> numberValue);
					numberValueResultLabel.setText("Value set");
				} catch (NumberFormatException e) {
					numberValueResultLabel.setText("Invalid value");
				}
				redrawCallback.callback();
			});

			return editingPane;
		};

		return new Block(wireNodes, blockRunner, rectangle, blockDrawer, editingPaneProducer);
	}

}
