package com.ogpe.block.factory.bool;

import java.util.Map;

import com.ogpe.block.Block;
import com.ogpe.block.factory.BlockFactory;
import com.ogpe.block.geometry.Point;
import com.ogpe.block.geometry.Rectangle;
import com.ogpe.block.wire.DataType;
import com.ogpe.block.wire.WireNode;
import com.ogpe.block.wire.WireNodeType;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class BooleanAndBlockFactory extends BlockFactory {

	public static final String INPUT_KEY_1 = "input1";
	public static final String INPUT_KEY_2 = "input2";
	public static final String OUTPUT_KEY = "output";

	public BooleanAndBlockFactory() {
		super(new Point(26, 26));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY_1,
				new WireNode(INPUT_KEY_1, WireNodeType.INPUT, DataType.BOOLEAN, new Point(7.5, 3.5)));
		wireNodes.put(INPUT_KEY_2,
				new WireNode(INPUT_KEY_2, WireNodeType.INPUT, DataType.BOOLEAN, new Point(size.x - 6.5, 3.5)));
		wireNodes.put(OUTPUT_KEY,
				new WireNode(OUTPUT_KEY, WireNodeType.OUTPUT, DataType.BOOLEAN, new Point(13.5, size.y - 2.5), () -> {
					Boolean operand1 = (Boolean) wireNodes.get(INPUT_KEY_1).provide();
					Boolean operand2 = (Boolean) wireNodes.get(INPUT_KEY_2).provide();
					Boolean result = operand1 && operand2;
					return result;
				}));
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "A&B";
		double textX = rect.x + 13;
		double textY = rect.y + 13;
		context.fillText(text, textX + 0.5, textY + 0.5);
	}

}
