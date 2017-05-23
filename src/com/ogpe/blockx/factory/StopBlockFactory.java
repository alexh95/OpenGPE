package com.ogpe.blockx.factory;

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
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class StopBlockFactory extends BlockFactory {

	public static final String INPUT_KEY = "input";
	
	public StopBlockFactory() {
		super(new Point(26, 22));
	}

	@Override
	public Block makeBlock(Point position) {
		Map<String, WireNode> wireNodes = new HashMap<>();
		WireNode input = new WireNode(WireNodeType.INPUT, DataType.BOOLEAN, new Point(size.x / 2 + 0.5, 3.5));
		wireNodes.put(INPUT_KEY, input);

		BlockRunner blockRunner = (context) -> {
			Object value = input.provide();
			if (value != null) {
				Boolean stop = (Boolean) value;
				if (stop) {
					System.out.println("DEBUG: Stopped");
				}
			}
		};

		Rectangle rectangle = new Rectangle(position).setSize(size);

		BlockDrawer blockDrawer = (block, context) -> {
			Rectangle rect = block.getRectangle();
			
			context.setFill(Color.BLACK);
			context.setTextAlign(TextAlignment.CENTER);
			context.setTextBaseline(VPos.CENTER);
			String text = "Stop";
			double textX = rect.x + Math.round(rect.w / 2);
			double textY = rect.y + Math.round(rect.h / 2) + 3;
			context.fillText(text, textX, textY);
		};

		EditingPaneProducer editingPaneProducer = (redrawCallback) -> null;

		return new Block(wireNodes, blockRunner, rectangle, blockDrawer, editingPaneProducer);
	}

}
