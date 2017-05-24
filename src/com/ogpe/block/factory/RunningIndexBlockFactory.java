package com.ogpe.block.factory;

import java.math.BigDecimal;
import java.util.Map;

import com.ogpe.block.Block;
import com.ogpe.block.DataType;
import com.ogpe.block.Point;
import com.ogpe.block.Rectangle;
import com.ogpe.block.RunningContext;
import com.ogpe.block.wire.WireNode;
import com.ogpe.block.wire.WireNodeType;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class RunningIndexBlockFactory extends BlockFactory {

	public static final String OUTPUT_KEY = "output";

	public RunningIndexBlockFactory() {
		super(new Point(26, 22));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(OUTPUT_KEY, new WireNode(OUTPUT_KEY, WireNodeType.OUTPUT, DataType.NUMBER,
				new Point(size.x / 2 + 0.5, size.y - 2.5)));
	}

	@Override
	protected void blockPreRun(Block block, RunningContext context) {
		block.getWireNodes().get(OUTPUT_KEY).setProvider(() -> BigDecimal.valueOf(context.runningIndex));
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "i";
		double textX = rect.x + Math.round(rect.w / 2);
		double textY = rect.y + Math.round(rect.h / 2) - 3;
		context.fillText(text, textX, textY);
	}

}
