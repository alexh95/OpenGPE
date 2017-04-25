package com.ogpe.project;

import com.ogpe.block.Block;

import javafx.scene.input.MouseEvent;

public class MoveCursorTool extends CursorTool {

	public MoveCursorTool(ProjectModel projectModel) {
		super(projectModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void softResetDisplayingContext() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hardResetDisplayingContext() {
		getProjectModel().unsetMovingBlock();
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

		Block<?, ?> selectedBlock = null;
		for (Block<?, ?> block : getProjectModel().getBlocks()) {
			if (block.getBlockView().isInside(x, y)) {
				selectedBlock = block;
			}
		}

		if (selectedBlock != null) {
			if (getProjectModel().getMovingBlock() != null) {
				getProjectModel().getMovingBlock().getBlockView().setMoving(false);
			}
			getProjectModel().setMovingBlock(selectedBlock);
			getProjectModel().setMovingBlockOffsetX(getProjectModel().getMovingBlock().getBlockView().getX() - x);
			getProjectModel().setMovingBlockOffsetY(getProjectModel().getMovingBlock().getBlockView().getY() - y);
			getProjectModel().getMovingBlock().getBlockView().setMoving(true);
		}
	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		double nextX = x + getProjectModel().getMovingBlockOffsetX();
		double nextY = y + getProjectModel().getMovingBlockOffsetY();
		Block<?, ?> movingBlock = getProjectModel().getMovingBlock();
		if (movingBlock != null) {
			if (getProjectModel().canPlace(nextX, nextY, movingBlock.getBlockView().getW(),
					movingBlock.getBlockView().getH())) {
				movingBlock.getBlockView().setX(nextX);
				movingBlock.getBlockView().setY(nextY);
			}
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		getProjectModel().unsetMovingBlock();
	}
}
