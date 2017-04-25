package com.ogpe.project;

import java.util.ArrayList;
import java.util.List;

import com.ogpe.observable.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Project {

	private ProjectModel projectModel;
	
	private List<CursorTool> cursorTools;
	private PanCursorTool panCursorTool;
	private PlaceCursorTool placeCursorTool;
	private SelectCursorTool selectCursorTool;
	private MoveCursorTool moveCursorTool;
	private WireCursorTool wireCursorTool;

	private CursorToolSelection cursorTool;

	public Project(Observer<Object> redrawObserver, Observer<Node> editingPanePaneObserver,
			Observer<String> consoleOutputObserver) {
		projectModel = new ProjectModel(redrawObserver, editingPanePaneObserver, consoleOutputObserver);
		
		cursorTools = new ArrayList<>();
		panCursorTool = new PanCursorTool(projectModel);
		cursorTools.add(panCursorTool);
		placeCursorTool = new PlaceCursorTool(projectModel);
		cursorTools.add(placeCursorTool);
		selectCursorTool = new SelectCursorTool(projectModel);
		cursorTools.add(selectCursorTool);
		moveCursorTool = new MoveCursorTool(projectModel);
		cursorTools.add(moveCursorTool);
		wireCursorTool = new WireCursorTool(projectModel);
		cursorTools.add(wireCursorTool);
	}

	public void drawCanvas(GraphicsContext graphicsContext) {
		projectModel.getBlocks().forEach(block -> {
			block.getBlockView().drawBlock(graphicsContext);
		});

		projectModel.getNetworkNodes().forEach(node -> {
			node.drawNode(graphicsContext);
			node.drawWire(graphicsContext);
		});

		Rectangle selectingBlockPlacingRectangle = projectModel.getSelectingBlockPlacingRectangle();
		if (projectModel.isDisplayPlacing() && projectModel.getSelectingBlockPlacingRectangle() != null) {
			if (projectModel.isSelectedBlockPlaceble()) {
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

		Rectangle dragSelectionRectangle = projectModel.getDragSelectionRectangle();
		if (projectModel.isDragSelecting() && dragSelectionRectangle != null) {
			graphicsContext.setStroke(Color.GRAY);
			double dragX = dragSelectionRectangle.getX();
			double dragY = dragSelectionRectangle.getY();
			double dragW = dragSelectionRectangle.getWidth();
			double dragH = dragSelectionRectangle.getHeight();
			graphicsContext.strokeRect(dragX + 0.5, dragY + 0.5, dragW, dragH);
		}

		if (projectModel.isWiring()) {
			graphicsContext.setStroke(Color.GREEN);
			double wireX1 = Math.round(projectModel.getWiringStartX()) - 0.5;
			double wireY1 = Math.round(projectModel.getWiringStartY()) - 0.5;
			double wireX2 = Math.round(projectModel.getWireX()) - 0.5;
			double wireY2 = Math.round(projectModel.getWireY()) - 0.5;
			graphicsContext.strokeLine(wireX1, wireY1, wireX2, wireY2);
		}
	}

	public void onMouseMoved(MouseEvent mouseEvent) {
		switch (cursorTool) {
		case PAN:
			panCursorTool.onMouseMoved(mouseEvent);
			break;
		case PLACE:
			placeCursorTool.onMouseMoved(mouseEvent);
			break;
		case SELECT:
			selectCursorTool.onMouseMoved(mouseEvent);
			break;
		case MOVE:
			moveCursorTool.onMouseMoved(mouseEvent);
			break;
		case WIRE:
			wireCursorTool.onMouseMoved(mouseEvent);
			break;
		}
		projectModel.updateRedrawObservable();
	}

	public void onMousePressed(MouseEvent mouseEvent) {
		switch (cursorTool) {
		case PAN:
			panCursorTool.onMousePressed(mouseEvent);
			break;
		case PLACE:
			placeCursorTool.onMousePressed(mouseEvent);
			break;
		case SELECT:
			selectCursorTool.onMousePressed(mouseEvent);
			break;
		case MOVE:
			moveCursorTool.onMousePressed(mouseEvent);
			break;
		case WIRE:
			wireCursorTool.onMousePressed(mouseEvent);
			break;
		}
		projectModel.updateRedrawObservable();
	}

	public void onMouseDragged(MouseEvent mouseEvent) {
		switch (cursorTool) {
		case PAN:
			panCursorTool.onMouseDragged(mouseEvent);
			break;
		case PLACE:
			placeCursorTool.onMouseDragged(mouseEvent);
			break;
		case SELECT:
			selectCursorTool.onMouseDragged(mouseEvent);
			break;
		case MOVE:
			moveCursorTool.onMouseDragged(mouseEvent);
			break;
		case WIRE:
			wireCursorTool.onMouseDragged(mouseEvent);
			break;
		}
		projectModel.updateRedrawObservable();
	}

	public void onMouseRelease(MouseEvent mouseEvent) {
		switch (cursorTool) {
		case PAN:
			panCursorTool.onMouseReleased(mouseEvent);
			break;
		case PLACE:
			placeCursorTool.onMouseReleased(mouseEvent);
			break;
		case SELECT:
			selectCursorTool.onMouseReleased(mouseEvent);
			break;
		case MOVE:
			moveCursorTool.onMouseReleased(mouseEvent);
			break;
		case WIRE:
			wireCursorTool.onMouseReleased(mouseEvent);
			break;
		}
		projectModel.updateRedrawObservable();
	}

	public void onMouseEntered(MouseEvent mouseEvent) {
		softResetDisplayingContext();
		projectModel.updateRedrawObservable();
	}

	public void onMouseExited(MouseEvent mouseEvent) {
		softResetDisplayingContext();
		projectModel.updateRedrawObservable();
	}

	private void softResetDisplayingContext() {
		cursorTools.forEach(cursorTool -> cursorTool.softResetDisplayingContext());
	}

	private void hardResetDisplayingContext() {
		softResetDisplayingContext();
		cursorTools.forEach(cursorTool -> cursorTool.hardResetDisplayingContext());
	}

	public void changeCursorTool(CursorToolSelection newCursorTool) {
		hardResetDisplayingContext();

		this.cursorTool = newCursorTool;
		switch (newCursorTool) {
		case PAN:
			panCursorTool.selectedCursorTool();
			break;
		case PLACE:
			placeCursorTool.selectedCursorTool();
			break;
		case SELECT:
			selectCursorTool.selectedCursorTool();
			break;
		case MOVE:
			moveCursorTool.selectedCursorTool();
			break;
		case WIRE:
			wireCursorTool.selectedCursorTool();
			break;
		}
	}

	// Run
	public void run() {
		projectModel.getBlocks().forEach(block -> block.run(projectModel.getConsoleOutputObservable()));
	}

	public ProjectModel getProjectModel() {
		return projectModel;
	}
}
