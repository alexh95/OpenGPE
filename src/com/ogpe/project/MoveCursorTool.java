package com.ogpe.project;

import com.ogpe.block.Block;
import com.ogpe.block.geometry.Point;
import com.ogpe.block.geometry.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class MoveCursorTool extends CursorTool {

	private Block movingBlock;
	private Point movingBlockOffset;

	public MoveCursorTool(ProjectModel projectModel) {
		super(projectModel, CursorToolSelection.MOVE);

		movingBlock = null;
		movingBlockOffset = new Point();
	}

	private void unsetMovingBlock() {
		if (movingBlock != null) {
			movingBlock.setMoving(false);
			movingBlock = null;
		}
	}

	@Override
	public void drawDisplay(GraphicsContext context) {

	}

	@Override
	public void softResetDisplayingContext() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hardResetDisplayingContext() {
		unsetMovingBlock();
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
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

		Block selectedBlock = null;
		for (Block block : getProjectModel().getBlocks()) {
			if (block.getRectangle().inside(point)) {
				selectedBlock = block;
			}
		}

		if (selectedBlock != null) {
			if (movingBlock != null) {
				movingBlock.setMoving(false);
			}
			movingBlock = selectedBlock;
			movingBlockOffset = movingBlock.getRectangle().min.sub(point);
			movingBlock.setMoving(true);
		}
	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
		if (movingBlock != null) {
			Point nextPoint = point.add(movingBlockOffset);
			Rectangle rectangle = new Rectangle(nextPoint).setSize(movingBlock.getRectangle().getSize());
			if (getProjectModel().canPlace(rectangle)) {
				movingBlock.setLocation(nextPoint);
			}
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		unsetMovingBlock();
	}
}
