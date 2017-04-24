package com.ogpe.block;

import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;
import com.ogpe.observable.Observable;

public abstract class Block<M extends BlockModel, V extends BlockView<M>> {

	private M blockModel;
	private V blockView;

	public Block(M blockModel, V blockView) {
		this.blockModel = blockModel;
		this.blockView = blockView;

	}

	public M getBlockModel() {
		return blockModel;
	}

	public V getBlockView() {
		return blockView;
	}

	public void run(Observable<String> consoleOutputObservable) {
	}
}
