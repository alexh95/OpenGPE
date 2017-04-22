package com.ogpe.project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ogpe.block.Block;
import com.ogpe.block.factory.BlockFactory;
import com.ogpe.block.network.NetworkNode;
import com.ogpe.block.network.NetworkNodeHighlight;
import com.ogpe.block.view.implementation.AdditionBlockView;
import com.ogpe.block.view.implementation.ConstantBooleanBlockView;
import com.ogpe.block.view.implementation.ConstantNumberBlockView;
import com.ogpe.block.view.implementation.ConstantStringBlockView;
import com.ogpe.block.view.implementation.PrintBlockView;
import com.ogpe.fx.BlockSelection;
import com.ogpe.observable.Observable;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Project {

	private BlockFactory blockFactory;

	private List<Block<?, ?>> blocks;

	public List<NetworkNode<?>> networkNodes;

	private boolean isDisplayPlacing = false;
	private boolean selectedBlockPlaceble = false;
	private Rectangle selectingBlockPlacingRectangle;

	private List<Block<?, ?>> selectedBlocks;
	private boolean isDragSelecting = false;
	private double startDragSelectionX;
	private double startDragSelectionY;
	private Rectangle dragSelectionRectangle;

	private Block<?, ?> movingBlock;
	private double movingBlockOffsetX;
	private double movingBlockOffsetY;

	public Project() {
		blockFactory = new BlockFactory();
		blocks = new ArrayList<>();
		networkNodes = new ArrayList<>();

		selectedBlocks = new ArrayList<>();
		movingBlock = null;
	}

	public void drawCanvas(GraphicsContext graphicsContext) {
		blocks.forEach(block -> {
			block.getBlockView().drawBlock(graphicsContext);
		});

		networkNodes.forEach(node -> {
			node.drawNode(graphicsContext);
			node.drawWire(graphicsContext);
		});

		if (isDisplayPlacing && selectingBlockPlacingRectangle != null) {
			if (isSelectedBlockPlaceble()) {
				graphicsContext.setStroke(Color.GREEN);
			} else {
				graphicsContext.setStroke(Color.RED);
			}
			double x = selectingBlockPlacingRectangle.getX();
			double y = selectingBlockPlacingRectangle.getY();
			double w = selectingBlockPlacingRectangle.getWidth();
			double h = selectingBlockPlacingRectangle.getHeight();
			graphicsContext.strokeRect(x + 0.5, y + 0.5, w, h);
		}

		if (isDragSelecting && dragSelectionRectangle != null) {
			graphicsContext.setStroke(Color.GRAY);
			double dragX = dragSelectionRectangle.getX();
			double dragY = dragSelectionRectangle.getY();
			double dragW = dragSelectionRectangle.getWidth();
			double dragH = dragSelectionRectangle.getHeight();
			graphicsContext.strokeRect(dragX + 0.5, dragY + 0.5, dragW, dragH);
		}

		if (wiring) {
			graphicsContext.setStroke(Color.GREEN);
			double wireX1 = Math.round(wiringStartX) - 0.5;
			double wireY1 = Math.round(wiringStartY) - 0.5;
			double wireX2 = Math.round(wireX) - 0.5;
			double wireY2 = Math.round(wireY) - 0.5;
			graphicsContext.strokeLine(wireX1, wireY1, wireX2, wireY2);
		}
	}

	private boolean canPlace(double x, double y, double w, double h) {
		boolean intersects = false;
		for (Block<?, ?> block : blocks) {
			if (!block.getBlockView().isMoving()) {
				intersects |= block.getBlockView().intersects(x, y, w, h);
			}
		}
		return !intersects;
	}

	public void resetDisplayingContext() {
		isDisplayPlacing = false;
		isDragSelecting = false;
		deselectAllBlocks();
		releaseMoveBlock();
		networkNodes.forEach(networkNode -> {
			if (networkNode.getHighlight() == NetworkNodeHighlight.HOVER) {
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
			}
		});
	}

	// Block Placement
	public void startDisplayingPlacing() {
		isDisplayPlacing = true;
	}

	public void placeBlock(double x, double y, BlockSelection selectedBlock) {
		hoverSelectedBlockPlaceble(x, y, selectedBlock);
		if (selectedBlockPlaceble) {
			Block<?, ?> block = null;
			switch (selectedBlock) {
			case CONSTANT_NUMBER:
				block = blockFactory.makeConstantNumberBlock(x, y);
				break;
			case CONSTANT_BOOLEAN:
				block = blockFactory.makeConstantBooleanBlock(x, y);
				break;
			case CONSTANT_STRING:
				block = blockFactory.makeConstantStringBlock(x, y);
				break;
			case ADDITION_BLOCK:
				block = blockFactory.makeAdditionBlock(x, y);
				break;
			case PRINT:
				block = blockFactory.makePrintBlock(x, y);
				break;
			}
			if (block != null) {
				networkNodes.addAll(block.getBlockModel().getNetworkNodes());
				blocks.add(block);
			}
		}
	}

	public void hoverSelectedBlockPlaceble(double x, double y, BlockSelection selectedBlock) {
		isDisplayPlacing = true;

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

	// Block Selection
	private void selectBlock(Block<?, ?> block) {
		if (!selectedBlocks.contains(block)) {
			selectedBlocks.add(block);
			block.getBlockView().setSelected(true);
		}
	}

	private void deselectBlock(Block<?, ?> block) {
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
			List<Block<?, ?>> targetBlocks = blocks.stream().filter(block -> block.getBlockView().isInside(x, y))
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
		List<Block<?, ?>> targetBlocks = blocks.stream()
				.filter(block -> block.getBlockView().intersects(dragX, dragY, dragW, dragH))
				.collect(Collectors.toList());

		if (!multipleSelection) {
			deselectAllBlocks();
		}
		targetBlocks.forEach(block -> selectBlock(block));
	}

	public void deleteSelected() {
		blocks.forEach(block -> networkNodes.removeAll(block.getBlockModel().getNetworkNodes()));
		blocks.removeAll(selectedBlocks);
	}

	public void editBlock(double x, double y, Observable<?> observable, Group panel) {
		List<Block<?, ?>> targetBlocks = blocks.stream().filter(block -> block.getBlockView().isInside(x, y))
				.collect(Collectors.toList());
		if (targetBlocks.size() == 1) {
			Block<?, ?> targetBlock = targetBlocks.get(0);
			targetBlock.getBlockView().populateEditPanel(observable, panel);
		} else {
			panel.getChildren().clear();
		}
	}

	// Block Move
	public void pressMoveBlock(double x, double y) {
		Block<?, ?> selectedBlock = null;
		for (Block<?, ?> block : blocks) {
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

	// Wire
	private NetworkNode<?> getClosestNetworkNode(double x, double y) {
		double maximumDistance = 16;
		NetworkNode<?> closestNetworkNode = null;
		double closestNetworkNodedistance = 0;
		for (NetworkNode<?> networkNode : networkNodes) {
			double dx = x - networkNode.getX();
			double dy = y - networkNode.getY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance <= maximumDistance && (closestNetworkNode == null || distance < closestNetworkNodedistance)) {
				closestNetworkNode = networkNode;
				distance = closestNetworkNodedistance;
			}
		}
		return closestNetworkNode;
	}

	private boolean wiring;
	private NetworkNode<?> wiringNetworkNode;
	private double wiringStartX;
	private double wiringStartY;
	private double wireX = 0;
	private double wireY = 0;

	public void hoverWire(double x, double y) {
		networkNodes.forEach(networkNode -> {
			switch (networkNode.getHighlight()) {
			case HOVER:
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				break;
			case HOVER_VALID_WIRING:
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				break;
			default:
				break;
			}
		});

		NetworkNode<?> closestNetworkNode = getClosestNetworkNode(x, y);
		if (closestNetworkNode != null) {
			if (wiring) {
				if (closestNetworkNode.getHighlight() == NetworkNodeHighlight.UNSET) {
					closestNetworkNode.setHighlighted(NetworkNodeHighlight.HOVER_VALID_WIRING);
				}
			} else {
				if (closestNetworkNode.getHighlight() == NetworkNodeHighlight.UNSET) {
					closestNetworkNode.setHighlighted(NetworkNodeHighlight.HOVER);
				}
			}
		}
	}

	public void pressWire(double x, double y) {
		hoverWire(x, y);
		wiringNetworkNode = getClosestNetworkNode(x, y);
		if (wiringNetworkNode != null) {
			wiringNetworkNode.setHighlighted(NetworkNodeHighlight.WIRING);
			
			wiring = true;
			wiringStartX = wiringNetworkNode.getX();
			wiringStartY = wiringNetworkNode.getY();
			dragWire(x, y);
		}
	}

	public void releaseWire(double x, double y) {
		hoverWire(x, y);
		if (wiring) {
			NetworkNode<?> closestNetworkNode = getClosestNetworkNode(x, y);
			if (closestNetworkNode != null) {
				closestNetworkNode.setNetworkNode(wiringNetworkNode);
				wiringNetworkNode.setHighlighted(NetworkNodeHighlight.SET);
				closestNetworkNode.setHighlighted(NetworkNodeHighlight.SET);
			}
			wiring = false;
			wiringStartX = 0;
			wiringStartY = 0;
			wiringNetworkNode = null;
		}
	}

	public void dragWire(double x, double y) {
		hoverWire(x, y);
		if (wiring) {
			wireX = x;
			wireY = y;
		}
	}

	// Run
	public void run() {
		blocks.forEach(block -> block.run());
	}
}
