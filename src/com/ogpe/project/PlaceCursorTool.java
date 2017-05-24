package com.ogpe.project;

import java.util.List;
import java.util.stream.Collectors;

import com.ogpe.block.Block;
import com.ogpe.block.BlockType;
import com.ogpe.block.geometry.Point;
import com.ogpe.block.geometry.Rectangle;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PlaceCursorTool extends CursorTool {

	private BlockType placingBlockSelection;
	private boolean displayPlacing;
	private boolean selectedBlockPlaceble;
	private Rectangle blockPlacingRectangle;
	private Block editingBlock;

	public PlaceCursorTool(ProjectModel projectModel) {
		super(projectModel, CursorToolSelection.PLACE);
		placingBlockSelection = BlockType.NUMBER_VALUE;
		displayPlacing = false;
		selectedBlockPlaceble = false;
	}

	public void setPlacingBlockSelection(BlockType placingBlockSelection) {
		this.placingBlockSelection = placingBlockSelection;
	}

	private void setEditingBlock(Block block) {
		unsetEditingBlock();
		if (block != null) {
			editingBlock = block;
			editingBlock.setEditing(true);
			Node editingPane = editingBlock.produceEditingPane(getProjectModel().getRedrawCallback());
			getProjectModel().getEditingPanePaneObservable().updateObservers(editingPane);
		}
	}

	private void unsetEditingBlock() {
		if (editingBlock != null) {
			editingBlock.setEditing(false);
			getProjectModel().getEditingPanePaneObservable().updateObservers(null);
			editingBlock = null;
		}
	}

	private Block findBlock(Point point) {
		List<Block> foundBlocks = getProjectModel().getBlocks().stream()
				.filter(block -> block.getRectangle().inside(point)).collect(Collectors.toList());
		if (foundBlocks.size() == 1) {
			return foundBlocks.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void drawDisplay(GraphicsContext context) {
		if (displayPlacing && blockPlacingRectangle != null) {
			if (selectedBlockPlaceble) {
				context.setStroke(Color.GREEN);
			} else {
				context.setStroke(Color.RED);
			}
			double x = blockPlacingRectangle.x;
			double y = blockPlacingRectangle.y;
			double w = blockPlacingRectangle.w;
			double h = blockPlacingRectangle.h;
			context.strokeRect(x + 0.5, y + 0.5, w, h);
		}
	}

	@Override
	public void softResetDisplayingContext() {
		displayPlacing = false;
	}

	@Override
	public void hardResetDisplayingContext() {
		unsetEditingBlock();
	}

	@Override
	public void selectedCursorTool() {
		displayPlacing = true;
	}

	@Override
	public void onMouseMoved(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

		displayPlacing = true;
		blockPlacingRectangle = new Rectangle(point).setSize(placingBlockSelection.getSize());
		selectedBlockPlaceble = getProjectModel().canPlace(blockPlacingRectangle);
	}

	@Override
	public void onMousePressed(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
		MouseButton mouseButton = mouseEvent.getButton();

		if (mouseButton == MouseButton.PRIMARY) {
			displayPlacing = false;
			if (selectedBlockPlaceble) {
				Block block = placingBlockSelection.makeBlock(point);
				getProjectModel().addBlock(block);
				setEditingBlock(block);

			}
		} else if (mouseButton == MouseButton.SECONDARY) {
			Block foundBlock = findBlock(point);
			setEditingBlock(foundBlock);
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {

	}

}
