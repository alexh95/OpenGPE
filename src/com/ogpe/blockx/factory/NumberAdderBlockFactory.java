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

public class NumberAdderBlockFactory extends BlockFactory {

	public static final String INPUT_KEY_1 = "input1";
	public static final String INPUT_KEY_2 = "input2";
	public static final String OUTPUT_KEY = "output";
	
	public static final Point SIZE = new Point(26, 22);

	@Override
	public Point getSize() {
		return SIZE;
	}

	@Override
	public Block makeBlock(Point position) {
		Map<String, WireNode> wireNodes = new HashMap<>();
		WireNode input1 = new WireNode(WireNodeType.INPUT, DataType.NUMBER, new Point(7.5, 3.5));
		WireNode input2 = new WireNode(WireNodeType.INPUT, DataType.NUMBER, new Point(SIZE.x - 6.5, 3.5));
		WireNode output = new WireNode(WireNodeType.OUTPUT, DataType.NUMBER, new Point(13.5, SIZE.y - 2.5), () -> {
			BigDecimal addend = (BigDecimal) input1.provide();
			BigDecimal augend = (BigDecimal) input2.provide();
			BigDecimal result = addend.add(augend);
			return result;
		});
		wireNodes.put(INPUT_KEY_1, input1);
		wireNodes.put(INPUT_KEY_2, input2);
		wireNodes.put(OUTPUT_KEY, output);

		BlockRunner blockRunner = (context) -> {

		};

		Rectangle rectangle = new Rectangle(position).setSize(SIZE);

		BlockDrawer blockDrawer = (block, context) -> {
			Rectangle rect = block.getRectangle();
			
			if (block.isMoving()) {
				context.setFill(Color.YELLOW);
			} else {
				context.setFill(Color.YELLOWGREEN);
			}
			double rectX = rect.x;
			double rectY = rect.y;
			double rectW = rect.w;
			double rectH = rect.h;
			context.fillRect(rectX, rectY, rectW, rectH);

			if (block.isSelected()) {
				context.setStroke(Color.RED);
			} else {
				context.setStroke(Color.BLACK);
			}

			double borderRectX = rect.x;
			double borderRectY = rect.y;
			double borderRectW = rect.w;
			double borderRectH = rect.h;
			context.strokeRect(borderRectX + 0.5, borderRectY + 0.5, borderRectW, borderRectH);

			context.setFill(Color.BLACK);
			context.setTextAlign(TextAlignment.CENTER);
			context.setTextBaseline(VPos.CENTER);
			String text = "+";
			double textX = rect.x + 13;
			double textY = rect.y + 8;
			context.fillText(text, textX + 0.5, textY + 0.5);
		};

		EditingPaneProducer editingPaneProducer = (redrawCallback) -> {
			return null;
		};

		return new Block(wireNodes, blockRunner, rectangle, blockDrawer, editingPaneProducer);
	}
}
