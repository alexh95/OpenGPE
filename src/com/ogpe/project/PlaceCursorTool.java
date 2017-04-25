package com.ogpe.project;

import com.ogpe.block.Block;
import com.ogpe.block.view.implementation.AdditionBlockView;
import com.ogpe.block.view.implementation.ConstantBooleanBlockView;
import com.ogpe.block.view.implementation.ConstantNumberBlockView;
import com.ogpe.block.view.implementation.ConstantStringBlockView;
import com.ogpe.block.view.implementation.PrintBlockView;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class PlaceCursorTool extends CursorTool {

	public PlaceCursorTool(ProjectModel projectModel) {
		super(projectModel);
	}

	@Override
	public void softResetDisplayingContext() {
		getProjectModel().setDisplayPlacing(false);
	}

	@Override
	public void hardResetDisplayingContext() {
		getProjectModel().unsetEditingBlock();
	}

	@Override
	public void selectedCursorTool() {
		getProjectModel().setDisplayPlacing(true);
	}

	@Override
	public void onMouseMoved(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		getProjectModel().setDisplayPlacing(true);
		switch (getProjectModel().getPlacingBlockSelection()) {
		case CONSTANT_NUMBER:
			getProjectModel().setSelectedBlockPlaceble(
					getProjectModel().canPlace(x, y, ConstantNumberBlockView.WIDTH, ConstantNumberBlockView.HEIGHT));
			getProjectModel().setSelectingBlockPlacingRectangle(
					new Rectangle(x, y, ConstantNumberBlockView.WIDTH, ConstantNumberBlockView.HEIGHT));
			break;
		case CONSTANT_BOOLEAN:
			getProjectModel().setSelectedBlockPlaceble(
					getProjectModel().canPlace(x, y, ConstantBooleanBlockView.WIDTH, ConstantBooleanBlockView.HEIGHT));
			getProjectModel().setSelectingBlockPlacingRectangle(
					new Rectangle(x, y, ConstantBooleanBlockView.WIDTH, ConstantBooleanBlockView.HEIGHT));
			break;
		case CONSTANT_STRING:
			getProjectModel().setSelectedBlockPlaceble(
					getProjectModel().canPlace(x, y, ConstantStringBlockView.WIDTH, ConstantStringBlockView.HEIGHT));
			getProjectModel().setSelectingBlockPlacingRectangle(
					new Rectangle(x, y, ConstantStringBlockView.WIDTH, ConstantStringBlockView.HEIGHT));
			break;
		case ADDITION_BLOCK:
			getProjectModel().setSelectedBlockPlaceble(
					getProjectModel().canPlace(x, y, AdditionBlockView.WIDTH, AdditionBlockView.HEIGHT));
			getProjectModel().setSelectingBlockPlacingRectangle(
					new Rectangle(x, y, AdditionBlockView.WIDTH, AdditionBlockView.HEIGHT));
			break;
		case PRINT:
			getProjectModel().setSelectedBlockPlaceble(
					getProjectModel().canPlace(x, y, PrintBlockView.WIDTH, PrintBlockView.HEIGHT));
			getProjectModel().setSelectingBlockPlacingRectangle(
					new Rectangle(x, y, PrintBlockView.WIDTH, PrintBlockView.HEIGHT));
			break;
		}
	}

	@Override
	public void onMousePressed(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();
		MouseButton mouseButton = mouseEvent.getButton();

		if (mouseButton == MouseButton.PRIMARY) {
			getProjectModel().setDisplayPlacing(false);
			if (getProjectModel().isSelectedBlockPlaceble()) {
				Block<?, ?> block = null;
				switch (getProjectModel().getPlacingBlockSelection()) {
				case CONSTANT_NUMBER:
					block = getProjectModel().getBlockFactory().makeConstantNumberBlock(x, y);
					break;
				case CONSTANT_BOOLEAN:
					block = getProjectModel().getBlockFactory().makeConstantBooleanBlock(x, y);
					break;
				case CONSTANT_STRING:
					block = getProjectModel().getBlockFactory().makeConstantStringBlock(x, y);
					break;
				case ADDITION_BLOCK:
					block = getProjectModel().getBlockFactory().makeAdditionBlock(x, y);
					break;
				case PRINT:
					block = getProjectModel().getBlockFactory().makePrintBlock(x, y);
					break;
				}
				if (block != null) {
					getProjectModel().getNetworkNodes().addAll(block.getBlockModel().getNetworkNodes());
					getProjectModel().getBlocks().add(block);
					getProjectModel().setEditingBlock(block);
				}
			}
		} else if (mouseButton == MouseButton.SECONDARY) {
			Block<?, ?> foundBlock = getProjectModel().findBlock(x, y);
			getProjectModel().setEditingBlock(foundBlock);
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {

	}
}
