package com.ogpe.project;

import java.util.List;
import java.util.stream.Collectors;

import com.ogpe.block.Block;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class SelectCursorTool extends CursorTool {

	public SelectCursorTool(ProjectModel projectModel) {
		super(projectModel);
	}

	@Override
	public void softResetDisplayingContext() {
		getProjectModel().setDragSelecting(false);
	}

	@Override
	public void hardResetDisplayingContext() {
		getProjectModel().deselectAllSelectedBlocks();
	}

	@Override
	public void selectedCursorTool() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMousePressed(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();
		MouseButton mouseButton = mouseEvent.getButton();
		boolean controlDown = mouseEvent.isControlDown(); 
		
		if (mouseButton == MouseButton.PRIMARY) {
			getProjectModel().setDragSelecting(false);
			getProjectModel().setStartDragSelectionX(x);
			getProjectModel().setStartDragSelectionY(y);
			if (!controlDown) {
				getProjectModel().deselectAllSelectedBlocks();
			}
		} else if (mouseButton == MouseButton.SECONDARY) {
			Block<?, ?> foundBlock = getProjectModel().findBlock(x, y);
			getProjectModel().setEditingBlock(foundBlock);
		}
	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();
		MouseButton mouseButton = mouseEvent.getButton();
		boolean controlDown = mouseEvent.isControlDown(); 
		
		if (mouseButton == MouseButton.PRIMARY) {
			getProjectModel().setDragSelecting(true);
			double dragX = Math.min(getProjectModel().getStartDragSelectionX(), x);
			double dragY = Math.min(getProjectModel().getStartDragSelectionY(), y);
			double dragW = Math.abs(getProjectModel().getStartDragSelectionX() - x);
			double dragH = Math.abs(getProjectModel().getStartDragSelectionY() - y);
			getProjectModel().setDragSelectionRectangle(new Rectangle(dragX, dragY, dragW, dragH));
			List<Block<?, ?>> targetBlocks = getProjectModel().getBlocks().stream()
					.filter(block -> block.getBlockView().intersects(dragX, dragY, dragW, dragH))
					.collect(Collectors.toList());

			if (!controlDown) {
				getProjectModel().deselectAllSelectedBlocks();
			}
			targetBlocks.forEach(block -> getProjectModel().selectBlock(block));
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();
		MouseButton mouseButton = mouseEvent.getButton();
		boolean controlDown = mouseEvent.isControlDown();
		
		if (mouseButton == MouseButton.PRIMARY) {
			if (!getProjectModel().isDragSelecting()) {
				List<Block<?, ?>> targetBlocks = getProjectModel().getBlocks().stream().filter(block -> block.getBlockView().isInside(x, y))
						.collect(Collectors.toList());

				if (controlDown) {
					targetBlocks.forEach(block -> {
						if (getProjectModel().getSelectedBlocks().contains(block)) {
							getProjectModel().deselectBlock(block);
						} else {
							getProjectModel().selectBlock(block);
						}
					});
				} else {
					getProjectModel().deselectAllSelectedBlocks();
					targetBlocks.forEach(block -> getProjectModel().selectBlock(block));
				}
			}
			getProjectModel().setDragSelecting(false);
		}
	}

}
