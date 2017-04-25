package com.ogpe.project;

import com.ogpe.observable.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Project {

	private ProjectModel projectModel;
	private CursorTools cursorTools;

	private CursorToolSelection cursorTool;

	public Project(Observer<Object> redrawObserver, Observer<Node> editingPanePaneObserver,
			Observer<String> consoleOutputObserver) {
		projectModel = new ProjectModel(redrawObserver, editingPanePaneObserver, consoleOutputObserver);
		cursorTools = new CursorTools(projectModel);
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
		cursorTools.onMouseMoved(cursorTool, mouseEvent);
		projectModel.updateRedrawObservable();
	}

	public void onMousePressed(MouseEvent mouseEvent) {
		cursorTools.onMousePressed(cursorTool, mouseEvent);
		projectModel.updateRedrawObservable();
	}

	public void onMouseDragged(MouseEvent mouseEvent) {
		cursorTools.onMouseDragged(cursorTool, mouseEvent);
		projectModel.updateRedrawObservable();
	}

	public void onMouseReleased(MouseEvent mouseEvent) {
		cursorTools.onMouseReleased(cursorTool, mouseEvent);
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

	public void changeCursorTool(CursorToolSelection newCursorTool) {
		hardResetDisplayingContext();
		cursorTools.changeCursorTool(newCursorTool);
		this.cursorTool = newCursorTool;
	}

	private void softResetDisplayingContext() {
		cursorTools.softResetDisplayingContext();
	}

	private void hardResetDisplayingContext() {
		softResetDisplayingContext();
		cursorTools.hardResetDisplayingContext();
	}

	// Run
	public void run() {
		projectModel.getBlocks().forEach(block -> block.run(projectModel.getConsoleOutputObservable()));
	}

	public ProjectModel getProjectModel() {
		return projectModel;
	}
}
