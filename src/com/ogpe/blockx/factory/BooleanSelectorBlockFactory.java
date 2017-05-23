package com.ogpe.blockx.factory;

import java.util.Map;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.DataType;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Rectangle;
import com.ogpe.blockx.wire.WireNode;
import com.ogpe.blockx.wire.WireNodeType;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class BooleanSelectorBlockFactory extends BlockFactory {

	public static final String INPUT_KEY_1 = "input1";
	public static final String INPUT_KEY_2 = "input2";
	public static final String INPUT_KEY_3 = "input3";
	public static final String OUTPUT_KEY = "output";

	public BooleanSelectorBlockFactory() {
		super(new Point(64, 32));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY_1, new WireNode(WireNodeType.INPUT, DataType.BOOLEAN, new Point(16.5, 3.5)));
		wireNodes.put(INPUT_KEY_2, new WireNode(WireNodeType.INPUT, DataType.BOOLEAN, new Point(32.5, 3.5)));
		wireNodes.put(INPUT_KEY_3, new WireNode(WireNodeType.INPUT, DataType.BOOLEAN, new Point(48.5, 3.5)));
		wireNodes.put(OUTPUT_KEY,
				new WireNode(WireNodeType.OUTPUT, DataType.BOOLEAN, new Point(32.5, size.y - 2.5), () -> {
					Boolean condition = (Boolean) wireNodes.get(INPUT_KEY_1).provide();
					Boolean result;
					if (condition) {
						result = (Boolean) wireNodes.get(INPUT_KEY_2).provide();
					} else {
						result = (Boolean) wireNodes.get(INPUT_KEY_3).provide();
					}
					return result;
				}));
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "If then else";
		double textX = rect.x + Math.round(rect.w / 2);
		double textY = rect.y + 10;
		context.fillText(text, textX, textY);
		text = "Boolean";
		textX = rect.x + Math.round(rect.w / 2);
		textY = rect.y + 20;
		context.fillText(text, textX, textY);
	}

}
