package com.ogpe.blockx.factory;

import java.util.Map;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.DataType;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Rectangle;
import com.ogpe.blockx.wire.WireNode;
import com.ogpe.blockx.wire.WireNodeType;
import com.ogpe.observable.Callback;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class BooleanValueBlockFactory extends BlockFactory {

	public static final String OUTPUT_KEY = "output";

	public BooleanValueBlockFactory() {
		super(new Point(46, 22));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(OUTPUT_KEY,
				new WireNode(WireNodeType.OUTPUT, DataType.BOOLEAN, new Point(23.5, size.y - 2.5), () -> false));
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
		String text = ((Boolean) block.getWireNodes().get(OUTPUT_KEY).provide()).toString();
		double textX = rect.x + Math.round(rect.w / 2);
		double textY = rect.y + Math.round(rect.h / 2) - 3;
		context.fillText(text, textX, textY);
	}

	@Override
	protected Node produceEditingPane(Block block, Callback redrawCallback) {
		VBox editingPane = new VBox();
		Label booleanValueLabel = new Label("Value:");
		ComboBox<Boolean> booleanValueComboBox = new ComboBox<>();
		Label booleanValueResultLabel = new Label("");
		editingPane.getChildren().addAll(booleanValueLabel, booleanValueComboBox, booleanValueResultLabel);

		booleanValueComboBox.getItems().addAll(false, true);
		Boolean currentBooleanValue = (Boolean) block.getWireNodes().get(OUTPUT_KEY).provide();
		booleanValueComboBox.setValue(currentBooleanValue);

		booleanValueComboBox.setOnAction(event -> {
			Boolean booleanValue = (Boolean) booleanValueComboBox.getValue();
			block.getWireNodes().get(OUTPUT_KEY).setProvider(() -> booleanValue);
			redrawCallback.callback();
		});

		return editingPane;
	}

}
