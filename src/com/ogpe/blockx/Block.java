package com.ogpe.blockx;

import java.util.Map;

import com.ogpe.blockx.wire.WireNode;
import com.ogpe.observable.Callback;
import com.ogpe.observable.Observable;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

public class Block {

	private final Map<String, WireNode> wireNodes;
	private final BlockRunner blockRunner;

	private Rectangle rectangle;
	private final BlockDrawer blockDrawer;

	private final EditingPaneProducer editingPaneProducer;
	
	private boolean moving;
	private boolean selected;
	private boolean editing;

	public Block(Map<String, WireNode> wireNodes, BlockRunner blockRunner, Rectangle rectangle, BlockDrawer blockDrawer,
			EditingPaneProducer editingPaneProducer) {
		this.wireNodes = wireNodes;
		this.wireNodes.values().forEach(wireNode -> wireNode.setOffsetProvider(() -> getRectangle().min));
		this.blockRunner = blockRunner;
		this.rectangle = rectangle;
		this.blockDrawer = blockDrawer;
		this.editingPaneProducer = editingPaneProducer;
	}

	public Map<String, WireNode> getWireNodes() {
		return wireNodes;
	}

	public void runBlock(Observable<String> console) {
		blockRunner.runBlock(this, console);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setLocation(Point p) {
		rectangle = rectangle.setLocation(p);
	}

	public void drawBlock(GraphicsContext context) {
		blockDrawer.drawBlock(this, context);
		wireNodes.values().forEach(wireNode -> wireNode.drawWireNode(context));
	}

	public Node produceEditingPane(Callback redrawCallback) {
		return editingPaneProducer.produceEditingPane(redrawCallback);
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
