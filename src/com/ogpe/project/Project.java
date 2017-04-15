package com.ogpe.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;

public class Project {

	private Collection<Block<? extends BlockModel, ? extends BlockBehavior<? extends BlockModel>, ? extends BlockView<? extends BlockModel>>> blocks;

	public Project() {
		blocks = new ArrayList<>();
	}

	public void addBlock(
			Block<? extends BlockModel, ? extends BlockBehavior<? extends BlockModel>, ? extends BlockView<? extends BlockModel>> block) {
		blocks.add(block);
	}

	public void removeBlock(
			Block<? extends BlockModel, ? extends BlockBehavior<? extends BlockModel>, ? extends BlockView<? extends BlockModel>> block) {
		blocks.remove(block);
	}

	public void forEachBlockView(Consumer<? super BlockView<?>> action) {
		blocks.stream().map(block -> block.getBlockView()).forEach(action);
	}
}
