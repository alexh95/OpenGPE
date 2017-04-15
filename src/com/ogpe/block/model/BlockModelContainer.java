package com.ogpe.block.model;

public abstract class BlockModelContainer<T extends BlockModel> {

	private T blockModel;

	public BlockModelContainer(T blockModel) {
		this.blockModel = blockModel;
	}

	protected T getBlockModel() {
		return blockModel;
	}
}
