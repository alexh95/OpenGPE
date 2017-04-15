package com.ogpe.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.view.BlockView;

public class Project {

	private Collection<BlockBehavior<?>> blocks;
	private Collection<BlockView<?>> blockViews;
	
	public Project() {
		blocks = new ArrayList<>();
		blockViews = new ArrayList<>();
	}
	
	public void addBlock(BlockBehavior<?> block, BlockView<?> blockView) {
		blocks.add(block);
		blockViews.add(blockView);
	}
	
	public void removeBlock(BlockBehavior<?> block, BlockView<?> blockView) {
		blocks.remove(block);
		blockViews.remove(blockView);
	}

	public void forEachBlockView(Consumer<? super BlockView<?>> action) {
		blockViews.forEach(action);
	}
}
