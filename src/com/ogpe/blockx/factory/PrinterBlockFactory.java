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

public class PrinterBlockFactory extends BlockFactory {

	public static final String INPUT_KEY = "input";

	public PrinterBlockFactory() {
		super(new Point(46, 22));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY, new WireNode(INPUT_KEY, WireNodeType.INPUT, DataType.ANY, new Point(23.5, 3.5)));
	}

	@Override
	protected void blockRun(Block block, RunningContext context) {
		Object value = block.getWireNodes().get(INPUT_KEY).provide();
		if (value != null) {
			context.console.updateObservers(value.toString());
			System.out.println("DEBUG: " + value.toString());
		}
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "Print";
		double textX = rect.x + Math.round(rect.w / 2);
		double textY = rect.y + Math.round(rect.h / 2) + 3;
		context.fillText(text, textX, textY);
	}

}
