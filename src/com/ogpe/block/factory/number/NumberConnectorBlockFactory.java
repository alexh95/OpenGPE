package com.ogpe.block.factory.number;

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

public class NumberConnectorBlockFactory extends BlockFactory {

	public static final String INPUT_KEY = "input";
	public static final String OUTPUT_KEY = "output";

	public NumberConnectorBlockFactory() {
		super(new Point(22, 26));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY, new WireNode(INPUT_KEY, WireNodeType.INPUT, DataType.NUMBER, new Point(11.5, 3.5)));
		wireNodes.put(OUTPUT_KEY, new WireNode(OUTPUT_KEY, WireNodeType.OUTPUT, DataType.NUMBER,
				new Point(11.5, size.y - 2.5), wireNodes.get(INPUT_KEY)));
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "N C";
		double textX = rect.x + 11;
		double textY = rect.y + 13;
		context.fillText(text, textX + 0.5, textY + 0.5);
	}

}
