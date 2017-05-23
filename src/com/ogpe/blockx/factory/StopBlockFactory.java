package com.ogpe.blockx.factory;

import java.util.Map;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.DataType;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Rectangle;
import com.ogpe.blockx.RunningContext;
import com.ogpe.blockx.wire.WireNode;
import com.ogpe.blockx.wire.WireNodeType;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class StopBlockFactory extends BlockFactory {

	public static final String INPUT_KEY = "input";

	public StopBlockFactory() {
		super(new Point(26, 22));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY, new WireNode(WireNodeType.INPUT, DataType.BOOLEAN, new Point(size.x / 2 + 0.5, 3.5)));
	}

	@Override
	protected void blockRun(Block block, RunningContext context) {
		Object value = block.getWireNodes().get(INPUT_KEY).provide();
		if (value != null) {
			Boolean stop = (Boolean) value;
			if (stop) {
				context.stop();
				System.out.println("DEBUG: Stopped");
			}
		}
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "Stop";
		double textX = rect.x + Math.round(rect.w / 2);
		double textY = rect.y + Math.round(rect.h / 2) + 3;
		context.fillText(text, textX, textY);
	}

}
