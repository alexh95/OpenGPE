package com.ogpe.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.ogpe.block.Block;
import com.ogpe.block.implementation.AdditionBlock;
import com.ogpe.block.implementation.ConstantBooleanBlock;
import com.ogpe.block.implementation.ConstantNumberBlock;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.implementation.PrintBlock;
import com.ogpe.block.model.BlockModel;
import com.ogpe.block.model.implementation.AdditionBlockModel;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.model.implementation.PrintBlockModel;
import com.ogpe.block.view.BlockView;
import com.ogpe.block.view.implementation.AdditionBlockView;
import com.ogpe.block.view.implementation.ConstantBooleanBlockView;
import com.ogpe.block.view.implementation.ConstantNumberBlockView;
import com.ogpe.block.view.implementation.ConstantStringBlockView;
import com.ogpe.block.view.implementation.PrintBlockView;
import com.ogpe.fx.BlockSelection;
import com.ogpe.observable.Observable;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Project {

	private List<Block<?, ?, ?>> blocks;

	private boolean displayPlacing = false;
	private boolean selectedBlockPlaceble = false;
	private Rectangle selectingBlockPlacingRectangle;

	private List<Block<?, ?, ?>> selectedBlocks;
	private boolean isDragSelecting = false;
	private double startDragSelectionX;
	private double startDragSelectionY;
	private Rectangle dragSelectionRectangle;

	private Block<?, ?, ?> movingBlock;
	private double movingBlockOffsetX;
	private double movingBlockOffsetY;

	public Project() {
		blocks = new ArrayList<>();
		selectedBlocks = new ArrayList<>();
		movingBlock = null;
	}

	public void forEachBlockView(Consumer<? super BlockView<? extends BlockModel>> action) {
		blocks.stream().map(block -> block.getBlockView()).forEach(action);
	}

	private boolean canPlace(double x, double y, double w, double h) {
		boolean intersects = false;
		for (Block<?, ?, ?> block : blocks) {
			if (!block.getBlockView().isMoving()) {
				intersects |= block.getBlockView().intersects(x, y, w, h);
			}
		}
		return !intersects;
	}

	public void resetDisplayingContext() {
		displayPlacing = false;
		isDragSelecting = false;
		deselectAllBlocks();
		releaseMoveBlock();
	}

	// Block Placement
	public void startDisplayingPlacing() {
		displayPlacing = true;
	}

	public void placeBlock(double x, double y, BlockSelection selectedBlock) {
		hoverSelectedBlockPlaceble(x, y, selectedBlock);
		if (selectedBlockPlaceble) {
			Block<?, ?, ?> block = null;
			switch (selectedBlock) {
			case CONSTANT_NUMBER:
				block = new ConstantNumberBlock(new ConstantBlockModel<BigDecimal>(BigDecimal.valueOf(0)), x, y);
				break;
			case CONSTANT_BOOLEAN:
				block = new ConstantBooleanBlock(new ConstantBlockModel<Boolean>(false), x, y);
				break;
			case CONSTANT_STRING:
				block = new ConstantStringBlock(new ConstantBlockModel<String>("text"), x, y);
				break;
			case ADDITION_BLOCK:
				block = new AdditionBlock(new AdditionBlockModel(), x, y);
				break;
			case PRINT:
				block = new PrintBlock(new PrintBlockModel(() -> "printed"), x, y);
				break;
			}
			if (block != null) {
				blocks.add(block);
			}
		}
	}

	public void hoverSelectedBlockPlaceble(double x, double y, BlockSelection selectedBlock) {
		displayPlacing = true;

		switch (selectedBlock) {
		case CONSTANT_NUMBER:
			selectedBlockPlaceble = canPlace(x, y, ConstantNumberBlockView.WIDTH, ConstantNumberBlockView.HEIGHT);
			selectingBlockPlacingRectangle = new Rectangle(x, y, ConstantNumberBlockView.WIDTH,
					ConstantNumberBlockView.HEIGHT);
			break;
		case CONSTANT_BOOLEAN:
			selectedBlockPlaceble = canPlace(x, y, ConstantBooleanBlockView.WIDTH, ConstantBooleanBlockView.HEIGHT);
			selectingBlockPlacingRectangle = new Rectangle(x, y, ConstantBooleanBlockView.WIDTH,
					ConstantBooleanBlockView.HEIGHT);
			break;
		case CONSTANT_STRING:
			selectedBlockPlaceble = canPlace(x, y, ConstantStringBlockView.WIDTH, ConstantStringBlockView.HEIGHT);
			selectingBlockPlacingRectangle = new Rectangle(x, y, ConstantStringBlockView.WIDTH,
					ConstantStringBlockView.HEIGHT);
			break;
		case ADDITION_BLOCK:
			selectedBlockPlaceble = canPlace(x, y, AdditionBlockView.WIDTH, AdditionBlockView.HEIGHT);
			selectingBlockPlacingRectangle = new Rectangle(x, y, AdditionBlockView.WIDTH, AdditionBlockView.HEIGHT);
			break;
		case PRINT:
			selectedBlockPlaceble = canPlace(x, y, PrintBlockView.WIDTH, PrintBlockView.HEIGHT);
			selectingBlockPlacingRectangle = new Rectangle(x, y, PrintBlockView.WIDTH, PrintBlockView.HEIGHT);
			break;
		}
	}

	public boolean isSelectedBlockPlaceble() {
		return selectedBlockPlaceble;
	}

	public Rectangle getSelectingBlockPlacingRectangle() {
		if (displayPlacing) {
			return selectingBlockPlacingRectangle;
		} else {
			return null;
		}
	}

	// Block Selection
	private void selectBlock(Block<?, ?, ?> block) {
		if (!selectedBlocks.contains(block)) {
			selectedBlocks.add(block);
			block.getBlockView().setSelected(true);
		}
	}

	private void deselectBlock(Block<?, ?, ?> block) {
		if (selectedBlocks.contains(block)) {
			selectedBlocks.remove(block);
			block.getBlockView().setSelected(false);
		}
	}

	public void deselectAllBlocks() {
		selectedBlocks.forEach(block -> block.getBlockView().setSelected(false));
		selectedBlocks.clear();
	}

	public void pressSelectBlock(double x, double y, boolean multipleSelection) {
		isDragSelecting = false;
		startDragSelectionX = x;
		startDragSelectionY = y;
		if (!multipleSelection) {
			deselectAllBlocks();
		}
	}

	public void releaseSelectBlock(double x, double y, boolean multipleSelection) {
		if (!isDragSelecting) {
			List<Block<?, ?, ?>> targetBlocks = blocks.stream().filter(block -> block.getBlockView().isInside(x, y))
					.collect(Collectors.toList());

			if (multipleSelection) {
				targetBlocks.forEach(block -> {
					if (selectedBlocks.contains(block)) {
						deselectBlock(block);
					} else {
						selectBlock(block);
					}
				});
			} else {
				deselectAllBlocks();
				targetBlocks.forEach(block -> selectBlock(block));
			}
		}
		isDragSelecting = false;
	}

	public void dragSelectBlock(double x, double y, boolean multipleSelection) {
		isDragSelecting = true;

		double dragX = Math.min(startDragSelectionX, x);
		double dragY = Math.min(startDragSelectionY, y);
		double dragW = Math.abs(startDragSelectionX - x);
		double dragH = Math.abs(startDragSelectionY - y);
		dragSelectionRectangle = new Rectangle(dragX, dragY, dragW, dragH);
		List<Block<?, ?, ?>> targetBlocks = blocks.stream()
				.filter(block -> block.getBlockView().intersects(dragX, dragY, dragW, dragH))
				.collect(Collectors.toList());

		if (!multipleSelection) {
			deselectAllBlocks();
		}
		targetBlocks.forEach(block -> selectBlock(block));
	}

	public void deleteSelected() {
		blocks.removeAll(selectedBlocks);
	}

	public Rectangle getDragSelectionRectangle() {
		if (isDragSelecting) {
			return dragSelectionRectangle;
		} else {
			return null;
		}
	}

	public void editBlock(double x, double y, Observable observable, Group panel) {
		List<Block<?, ?, ?>> targetBlocks = blocks.stream().filter(block -> block.getBlockView().isInside(x, y))
				.collect(Collectors.toList());
		if (targetBlocks.size() == 1) {
			Block<?, ?, ?> targetBlock = targetBlocks.get(0);
			targetBlock.getBlockView().populateEditPanel(observable, panel);
		} else {
			panel.getChildren().clear();
		}
	}

	// Block Move
	public void pressMoveBlock(double x, double y) {
		Block<?, ?, ?> selectedBlock = null;
		for (Block<?, ?, ?> block : blocks) {
			if (block.getBlockView().isInside(x, y)) {
				selectedBlock = block;
			}
		}

		if (selectedBlock != null) {
			if (movingBlock != null) {
				movingBlock.getBlockView().setMoving(false);
			}
			movingBlock = selectedBlock;
			movingBlockOffsetX = movingBlock.getBlockView().getX() - x;
			movingBlockOffsetY = movingBlock.getBlockView().getY() - y;
			movingBlock.getBlockView().setMoving(true);
		}
	}

	public void releaseMoveBlock() {
		if (movingBlock != null) {
			movingBlock.getBlockView().setMoving(false);
			movingBlock = null;
		}
	}

	public void dragMoveBlock(double x, double y) {
		double nextX = x + movingBlockOffsetX;
		double nextY = y + movingBlockOffsetY;
		if (movingBlock != null) {
			if (canPlace(nextX, nextY, movingBlock.getBlockView().getW(), movingBlock.getBlockView().getH())) {
				movingBlock.getBlockView().setX(nextX);
				movingBlock.getBlockView().setY(nextY);
			}
		}
	}
}
