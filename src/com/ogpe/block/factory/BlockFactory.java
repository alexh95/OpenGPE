package com.ogpe.block.factory;

import java.util.HashMap;
import java.util.Map;

import com.ogpe.block.Block;
import com.ogpe.block.BlockType;
import com.ogpe.block.Point;
import com.ogpe.block.Rectangle;
import com.ogpe.block.RunningContext;
import com.ogpe.block.wire.WireNode;
import com.ogpe.observable.Callback;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

public abstract class BlockFactory {

	public final Point size;

	protected BlockFactory(Point size) {
		this.size = size;
	}

	protected abstract void addWireNodes(Map<String, WireNode> wireNodes);

	protected void blockReset(Block block) {

	}

	protected void blockPreRun(Block block, RunningContext context) {

	}

	protected void blockRun(Block block, RunningContext context) {

	}

	protected void blockPostRun(Block block, RunningContext context) {

	}

	protected abstract void drawBlock(Block block, GraphicsContext context);

	protected Node produceEditingPane(Block block, Callback redrawCallback) {
		return null;
	}

	public Block makeBlock(BlockType blockType, Point position) {
		Map<String, WireNode> wireNodes = new HashMap<>();
		addWireNodes(wireNodes);

		return new Block(blockType, wireNodes, this::blockReset, this::blockPreRun, this::blockRun, this::blockPostRun,
				new Rectangle(position).setSize(size), this::drawBlock, this::produceEditingPane);
	}

	public Block makeBlock(BlockType blockType) {
		return makeBlock(blockType, new Point());
	}

}
