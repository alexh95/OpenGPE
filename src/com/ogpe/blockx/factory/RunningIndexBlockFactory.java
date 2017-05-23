package com.ogpe.blockx.factory;

import java.math.BigDecimal;
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

public class RunningIndexBlockFactory extends BlockFactory {

	public static int runningIndex = 0;
	
	public static final String OUTPUT_KEY = "output";

	public RunningIndexBlockFactory() {
		super(new Point(26, 22));
	}

	@Override
	public Block makeBlock(Point position) {
		Map<String, WireNode> wireNodes = new HashMap<>();
		WireNode output = new WireNode(WireNodeType.OUTPUT, DataType.NUMBER, new Point(size.x / 2 + 0.5, size.y - 2.5), () -> {
			return BigDecimal.valueOf(runningIndex);
		});
		wireNodes.put(OUTPUT_KEY, output);

		BlockRunner blockRunner = (context) -> {

		};

		Rectangle rectangle = new Rectangle(position).setSize(size);
		
		BlockDrawer blockDrawer = (block, context) -> {
			Rectangle rect = block.getRectangle();
			
			context.setFill(Color.BLACK);
			context.setTextAlign(TextAlignment.CENTER);
			context.setTextBaseline(VPos.CENTER);
			String text = "i";
			double textX = rect.x + Math.round(rect.w / 2);
			double textY = rect.y + Math.round(rect.h / 2) - 3;
			context.fillText(text, textX, textY);
		};
		
		EditingPaneProducer editingPaneProducer = (redrawCallback) -> null;

		return new Block(wireNodes, blockRunner, rectangle, blockDrawer, editingPaneProducer);
	}

}
