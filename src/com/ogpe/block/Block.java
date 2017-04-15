package com.ogpe.block;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.behaviour.Provider;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;

public abstract class Block<T extends BlockModel, B extends BlockBehavior<T>, V extends BlockView<T>> {

	private T blockModel;
	private Provider<T> blockModelProvider;

	private B blockBehavior;
	private V blockView;

	public Block(T blockModel, B blockBehavior, V blockView) {
		this.blockModel = blockModel;
		blockModelProvider = () -> this.blockModel;
		this.blockBehavior = blockBehavior;
		this.blockView = blockView;

		this.blockBehavior.setBlockModelProvider(blockModelProvider);
		this.blockView.setBlockModelProvider(blockModelProvider);
	}

	public T getBlockModel() {
		return blockModel;
	}

	public B getBlockBehavior() {
		return blockBehavior;
	}

	public V getBlockView() {
		return blockView;
	}
}
