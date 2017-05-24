package com.ogpe.blockx.factory.number;

import java.math.BigDecimal;
import java.util.Map;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.DataType;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Rectangle;
import com.ogpe.blockx.RunningContext;
import com.ogpe.blockx.factory.BlockFactory;
import com.ogpe.blockx.wire.WireNode;
import com.ogpe.blockx.wire.WireNodeType;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class NumberMemoryBlockFactory extends BlockFactory {

	public static final String INPUT_KEY_1 = "input1";
	public static final String INPUT_KEY_2 = "input2";
	public static final String OUTPUT_KEY = "output";
	public static final String THROUGHPUT_KEY = "throughput";

	public NumberMemoryBlockFactory() {
		super(new Point(26, 26));
	}

	@Override
	protected void addWireNodes(Map<String, WireNode> wireNodes) {
		wireNodes.put(INPUT_KEY_1, new WireNode(INPUT_KEY_1, WireNodeType.INPUT, DataType.NUMBER, new Point(7.5, 3.5)));
		wireNodes.put(INPUT_KEY_2,
				new WireNode(INPUT_KEY_2, WireNodeType.INPUT, DataType.NUMBER, new Point(size.x - 6.5, 3.5)));
		wireNodes.put(THROUGHPUT_KEY,
				new WireNode(THROUGHPUT_KEY, WireNodeType.THROUGHPUT, DataType.NUMBER, new Point(-10000, -10000)));
		wireNodes.put(OUTPUT_KEY, new WireNode(OUTPUT_KEY, WireNodeType.OUTPUT, DataType.NUMBER,
				new Point(13.5, size.y - 2.5), wireNodes.get(INPUT_KEY_1)));
	}

	@Override
	protected void blockReset(Block block) {
		block.getWireNodes().get(OUTPUT_KEY).setProvider(block.getWireNodes().get(INPUT_KEY_1));
	}

	@Override
	protected void blockRun(Block block, RunningContext context) {
		Object value = block.getWireNodes().get(INPUT_KEY_2).provide();
		BigDecimal number = (BigDecimal) value;
		block.getWireNodes().get(THROUGHPUT_KEY).setProvider(() -> number);
	}

	@Override
	protected void blockPostRun(Block block, RunningContext context) {
		Object value = block.getWireNodes().get(THROUGHPUT_KEY).provide();
		BigDecimal number = (BigDecimal) value;
		block.getWireNodes().get(OUTPUT_KEY).setProvider(() -> number);
	}

	@Override
	protected void drawBlock(Block block, GraphicsContext context) {
		Rectangle rect = block.getRectangle();
		context.setFill(Color.BLACK);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		String text = "N";
		double textX = rect.x + 13;
		double textY = rect.y + 13;
		context.fillText(text, textX + 0.5, textY + 0.5);
	}

}
