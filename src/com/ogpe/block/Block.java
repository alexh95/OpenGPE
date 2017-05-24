package com.ogpe.block;

import java.util.Map;

import com.ogpe.block.wire.WireNode;
import com.ogpe.observable.Callback;
import com.ogpe.observable.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Block {

	public final BlockType blockType;
	
	private final Map<String, WireNode> wireNodes;
	private final Observer<Block> blockReseter;
	private final BlockRunner blockPreRunner;
	private final BlockRunner blockRunner;
	private final BlockRunner blockPostRunner;

	private Rectangle rectangle;
	private final BlockDrawer blockDrawer;

	private final EditingPaneProducer editingPaneProducer;

	private boolean moving;
	private boolean selected;
	private boolean editing;

	public Block(BlockType blockType, Map<String, WireNode> wireNodes, Observer<Block> blockReseter, BlockRunner blockPreRunner, BlockRunner blockRunner, BlockRunner blockPostRunner, Rectangle rectangle, BlockDrawer blockDrawer,
			EditingPaneProducer editingPaneProducer) {
		this.blockType = blockType;
		this.wireNodes = wireNodes;
		this.wireNodes.values().forEach(wireNode -> wireNode.setBlockProvider(() -> this));
		this.blockReseter = blockReseter;
		this.blockPreRunner = blockPreRunner;
		this.blockRunner = blockRunner;
		this.blockPostRunner = blockPostRunner;
		this.rectangle = rectangle;
		this.blockDrawer = blockDrawer;
		this.editingPaneProducer = editingPaneProducer;
	}

	public Map<String, WireNode> getWireNodes() {
		return wireNodes;
	}
	
	public void reset() {
		blockReseter.update(this);
	}

	public void preRunBlock(RunningContext context) {
		blockPreRunner.runBlock(this, context);
	}
	
	public void runBlock(RunningContext context) {
		blockRunner.runBlock(this, context);
	}
	
	public void postRunBlock(RunningContext context) {
		blockPostRunner.runBlock(this, context);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setLocation(Point p) {
		rectangle = rectangle.setLocation(p);
	}

	public void drawBlock(GraphicsContext context) {
		drawBlockRectangle(context);
		blockDrawer.drawBlock(this, context);
		wireNodes.values().forEach(wireNode -> wireNode.drawWireNode(context));
	}

	private void drawBlockRectangle(GraphicsContext context) {
		if (moving) {
			context.setFill(Color.YELLOW);
		} else {
			context.setFill(Color.YELLOWGREEN);
		}
		double rectX = rectangle.x;
		double rectY = rectangle.y;
		double rectW = rectangle.w;
		double rectH = rectangle.h;
		context.fillRect(rectX, rectY, rectW, rectH);

		if (selected) {
			context.setStroke(Color.RED);
		} else {
			context.setStroke(Color.BLACK);
		}
		double borderRectX = rectangle.x;
		double borderRectY = rectangle.y;
		double borderRectW = rectangle.w;
		double borderRectH = rectangle.h;
		context.strokeRect(borderRectX + 0.5, borderRectY + 0.5, borderRectW, borderRectH);
	}
	
	public Node produceEditingPane(Callback redrawCallback) {
		return editingPaneProducer.produceEditingPane(this, redrawCallback);
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

}
