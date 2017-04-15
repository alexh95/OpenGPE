package com.ogpe.block;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.behaviour.Provider;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;

public abstract class Block<M extends BlockModel, B extends BlockBehavior<M>, V extends BlockView<M>> {

	private M blockModel;
	private Provider<M> blockModelProvider;

	private B blockBehavior;
	private V blockView;

	public Block(M blockModel, B blockBehavior, V blockView) {
		this.blockModel = blockModel;
		blockModelProvider = () -> this.blockModel;
		this.blockBehavior = blockBehavior;
		this.blockView = blockView;

		this.blockBehavior.setBlockModelProvider(blockModelProvider);
		this.blockView.setBlockModelProvider(blockModelProvider);
	}

	public M getBlockModel() {
		return blockModel;
	}

	public B getBlockBehavior() {
		return blockBehavior;
	}

	public V getBlockView() {
		return blockView;
	}
}
