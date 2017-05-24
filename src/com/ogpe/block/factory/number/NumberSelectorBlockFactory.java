package com.ogpe.block.factory.number;

import java.math.BigDecimal;
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

public class NumberSelectorBlockFactory extends BlockFactory {

	public static final String INPUT_KEY_1 = "input1";
	public static final String INPUT_KEY_2 = "input2";
	public static final String INPUT_KEY_3 = "input3";
	public static final String OUTPUT_KEY = "output";

	public NumberSelectorBlockFactory() {
		super(new Point(64, 32));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY_1,
				new WireNode(INPUT_KEY_1, WireNodeType.INPUT, DataType.BOOLEAN, new Point(16.5, 3.5)));
		wireNodes.put(INPUT_KEY_2,
				new WireNode(INPUT_KEY_2, WireNodeType.INPUT, DataType.NUMBER, new Point(32.5, 3.5)));
		wireNodes.put(INPUT_KEY_3,
				new WireNode(INPUT_KEY_3, WireNodeType.INPUT, DataType.NUMBER, new Point(48.5, 3.5)));
		wireNodes.put(OUTPUT_KEY,
				new WireNode(OUTPUT_KEY, WireNodeType.OUTPUT, DataType.BOOLEAN, new Point(32.5, size.y - 2.5), () -> {
					Boolean condition = (Boolean) wireNodes.get(INPUT_KEY_1).provide();
					BigDecimal result;
					if (condition) {
						result = (BigDecimal) wireNodes.get(INPUT_KEY_2).provide();
					} else {
						result = (BigDecimal) wireNodes.get(INPUT_KEY_3).provide();
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
		String text = "C ? A: B";
		double textX = rect.x + Math.round(rect.w / 2);
		double textY = rect.y + 10;
		context.fillText(text, textX, textY);
		text = "Number";
		textX = rect.x + Math.round(rect.w / 2);
		textY = rect.y + 20;
		context.fillText(text, textX, textY);
	}

}
