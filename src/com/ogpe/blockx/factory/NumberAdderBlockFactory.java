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
import com.ogpe.blockx.WireNode;
import com.ogpe.blockx.WireNodeType;

public class NumberAdderBlockFactory extends BlockFactory {

	public static final String INPUT_KEY_1 = "input1";
	public static final String INPUT_KEY_2 = "input2";
	public static final String OUTPUT_KEY = "output";
	
	public static final Point SIZE = new Point(26, 21);

	@Override
	public Point getSize() {
		return SIZE;
	}

	@Override
	public Block makeBlock(Point position) {
		Map<String, WireNode> wireNodes = new HashMap<>();
		WireNode input1 = new WireNode(WireNodeType.INPUT, DataType.NUMBER, new Point());
		WireNode input2 = new WireNode(WireNodeType.INPUT, DataType.NUMBER, new Point());
		WireNode output = new WireNode(WireNodeType.OUTPUT, DataType.NUMBER, new Point(), () -> {
			BigDecimal addend = (BigDecimal) input1.provide();
			BigDecimal augend = (BigDecimal) input2.provide();
			BigDecimal result = addend.add(augend);
			return result;
		});
		wireNodes.put(INPUT_KEY_1, input1);
		wireNodes.put(INPUT_KEY_2, input2);
		wireNodes.put(OUTPUT_KEY, output);

		BlockRunner blockRunner = (block, console) -> {

		};

		Rectangle rectangle = new Rectangle(position).setSize(SIZE);

		BlockDrawer blockDrawer = (block, context) -> {

		};

		EditingPaneProducer editingPaneProducer = (redrawCallback) -> {
			return null;
		};

		return new Block(wireNodes, blockRunner, rectangle, blockDrawer, editingPaneProducer);
	}
}
