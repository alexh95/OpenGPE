package com.ogpe.block;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;
import com.ogpe.requester.Requester;

public abstract class Block<M extends BlockModel, B extends BlockBehavior<M>, V extends BlockView<M>> {

	private M blockModel;
	private Requester<M> blockModelRequester;

	private B blockBehavior;
	private V blockView;

	public Block(M blockModel, B blockBehavior, V blockView) {
		this.blockModel = blockModel;
		blockModelRequester = () -> this.blockModel;
		this.blockBehavior = blockBehavior;
		this.blockView = blockView;

		this.blockBehavior.setBlockModelRequester(blockModelRequester);
		this.blockView.setBlockModelRequester(blockModelRequester);
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
