package com.ogpe.block;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;

public class Block<T extends BlockModel> {

	private BlockBehavior<T> blockBehavior;
	private BlockView<T> blockView;

	public Block(BlockBehavior<T> blockBehavior, BlockView<T> blockView) {
		this.blockBehavior = blockBehavior;
		this.blockView = blockView;
	}

	public BlockBehavior<T> getBlockBehavior() {
		return blockBehavior;
	}

	public BlockView<T> getBlockView() {
		return blockView;
	}
}
