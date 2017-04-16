package com.ogpe.project;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.ogpe.block.Block;
import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.view.BlockView;

public class Project {

	private List<Block<? extends BlockModel, ? extends BlockBehavior<? extends BlockModel>, ? extends BlockView<? extends BlockModel>>> blocks;

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

	public void forEachBlockView(Consumer<? super BlockView<? extends BlockModel>> action) {
		blocks.stream().map(block -> block.getBlockView()).forEach(action);
	}

	public boolean canPlace(double x, double y, double w, double h) {
		boolean intersects = false;
		for (Block<?, ?, ?> block : blocks) {
			intersects |= block.getBlockView().intersects(x, y, w, h);
		}
		return !intersects;
	}

	public boolean selectBlock(double x, double y, boolean multipleSelection) {
		Block<?, ?, ?> selectedBlock = null;
		for (Block<?, ?, ?> block : blocks) {
			if (block.getBlockView().isInside(x, y)) {
				selectedBlock = block;
			}
		}

		if (selectedBlock != null) {
			boolean isSelected = selectedBlock.getBlockView().isSelected();
			if (!multipleSelection) {
				deselectAll();
			}
			selectedBlock.getBlockView().setSelected(!isSelected);
			return false;
		} else {
			deselectAll();
			return false;
		}
	}

	public void deselectAll() {
		blocks.forEach(block -> block.getBlockView().setSelected(false));
	}

	public void deleteSelected() {
		blocks = blocks.stream().filter(blocks -> !blocks.getBlockView().isSelected()).collect(Collectors.toList());
	}
}
