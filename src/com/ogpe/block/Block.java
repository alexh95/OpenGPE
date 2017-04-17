package com.ogpe.block;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;

public abstract class Block<M extends BlockModel, B extends BlockBehavior<M>, V extends BlockView<M>> {

	private M blockModel;
	private V blockView;

	public Block(M blockModel, V blockView) {
		this.blockModel = blockModel;
		this.blockView = blockView;
	}

	public M getBlockModel() {
		return blockModel;
	}

	public abstract B makeBlockBehavior();

	public V getBlockView() {
		return blockView;
	}
}
