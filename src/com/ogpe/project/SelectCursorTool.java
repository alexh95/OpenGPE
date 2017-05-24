package com.ogpe.project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ogpe.block.Block;
import com.ogpe.block.geometry.Point;
import com.ogpe.block.geometry.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SelectCursorTool extends CursorTool {

	private List<Block> selectedBlocks;
	private boolean dragSelecting;
	private double startDragSelectionX;
	private double startDragSelectionY;
	private Rectangle dragSelectionRectangle;

	public SelectCursorTool(ProjectModel projectModel) {
		super(projectModel, CursorToolSelection.SELECT);
		selectedBlocks = new ArrayList<>();
	}

	private void selectBlock(Block block) {
		if (!selectedBlocks.contains(block)) {
			selectedBlocks.add(block);
			block.setSelected(true);
		}
	}

	private void deselectBlock(Block block) {
		if (selectedBlocks.contains(block)) {
			selectedBlocks.remove(block);
			block.setSelected(false);
		}
	}

	private void deselectAllSelectedBlocks() {
		selectedBlocks.forEach(block -> block.setSelected(false));
		selectedBlocks.clear();
	}

	public void deleteSelectedBlocks() {
		getProjectModel().removeBlock(selectedBlocks);
	}

	@Override
	public void drawDisplay(GraphicsContext context) {
		if (dragSelecting && dragSelectionRectangle != null) {
			context.setStroke(Color.GRAY);
			double dragX = dragSelectionRectangle.x;
			double dragY = dragSelectionRectangle.y;
			double dragW = dragSelectionRectangle.w;
			double dragH = dragSelectionRectangle.h;
			context.strokeRect(dragX + 0.5, dragY + 0.5, dragW, dragH);
		}
	}

	@Override
	public void softResetDisplayingContext() {
		dragSelecting = false;
	}

	@Override
	public void hardResetDisplayingContext() {
		deselectAllSelectedBlocks();
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
			dragSelecting = false;
			startDragSelectionX = x;
			startDragSelectionY = y;
			if (!controlDown) {
				deselectAllSelectedBlocks();
			}
		}
	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();
		MouseButton mouseButton = mouseEvent.getButton();
		boolean controlDown = mouseEvent.isControlDown();

		if (mouseButton == MouseButton.PRIMARY) {
			dragSelecting = true;
			double dragX = Math.min(startDragSelectionX, x);
			double dragY = Math.min(startDragSelectionY, y);
			double dragW = Math.abs(startDragSelectionX - x);
			double dragH = Math.abs(startDragSelectionY - y);
			dragSelectionRectangle = new Rectangle(dragX, dragY, dragW, dragH);
			List<Block> targetBlocks = getProjectModel().getBlocks().stream()
					.filter(block -> block.getRectangle().intersects(new Rectangle(dragX, dragY, dragW, dragH)))
					.collect(Collectors.toList());

			if (!controlDown) {
				deselectAllSelectedBlocks();
			}
			targetBlocks.forEach(block -> selectBlock(block));
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();
		MouseButton mouseButton = mouseEvent.getButton();
		boolean controlDown = mouseEvent.isControlDown();

		if (mouseButton == MouseButton.PRIMARY) {
			if (!dragSelecting) {
				List<Block> targetBlocks = getProjectModel().getBlocks().stream()
						.filter(block -> block.getRectangle().inside(new Point(x, y))).collect(Collectors.toList());

				if (controlDown) {
					targetBlocks.forEach(block -> {
						if (selectedBlocks.contains(block)) {
							deselectBlock(block);
						} else {
							selectBlock(block);
						}
					});
				} else {
					deselectAllSelectedBlocks();
					targetBlocks.forEach(block -> selectBlock(block));
				}
			}
			dragSelecting = false;
		}
	}

}
