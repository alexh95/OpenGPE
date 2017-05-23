package com.ogpe.blockx.factory;

import java.math.BigDecimal;
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

public class NumberAdderBlockFactory extends BlockFactory {

	public static final String INPUT_KEY_1 = "input1";
	public static final String INPUT_KEY_2 = "input2";
	public static final String OUTPUT_KEY = "output";

	public NumberAdderBlockFactory() {
		super(new Point(26, 22));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY_1, new WireNode(WireNodeType.INPUT, DataType.NUMBER, new Point(7.5, 3.5)));
		wireNodes.put(INPUT_KEY_2, new WireNode(WireNodeType.INPUT, DataType.NUMBER, new Point(size.x - 6.5, 3.5)));
		wireNodes.put(OUTPUT_KEY,
				new WireNode(WireNodeType.OUTPUT, DataType.NUMBER, new Point(13.5, size.y - 2.5), () -> {
					BigDecimal addend = (BigDecimal) wireNodes.get(INPUT_KEY_1).provide();
					BigDecimal augend = (BigDecimal) wireNodes.get(INPUT_KEY_1).provide();
					BigDecimal result = addend.add(augend);
					return result;
				}));
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();

		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "+";
		double textX = rect.x + 13;
		double textY = rect.y + 8;
		context.fillText(text, textX + 0.5, textY + 0.5);
	}

}
