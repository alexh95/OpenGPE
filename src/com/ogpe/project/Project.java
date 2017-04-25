package com.ogpe.project;

import com.ogpe.observable.Callback;
import com.ogpe.observable.Observer;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Project {

	private ProjectModel projectModel;
	private CursorTools cursorTools;

	private CursorToolSelection cursorTool;

	private Callback exitFileMenuItemPressed;
	private Callback panCursorToolMenuItemPressed;
	private Callback placeCursorToolMenuItemPressed;
	private Callback selectCursorToolMenuItemPressed;
	private Callback moveCursorToolMenuItemPressed;
	private Callback wireCursorToolMenuItemPressed;

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

	// Event Handlers
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

	public void onBlockSelectionChanged(ObservableValue<? extends BlockSelection> observable, BlockSelection oldValue,
			BlockSelection newValue) {
		projectModel.setPlacingBlockSelection(newValue);
	}
	
	// Callback
	public void setOnExitFileMenuItemPressed(Callback callback) {
		exitFileMenuItemPressed = callback;
	}
	
	public void setOnPanCursorToolMenuItemPressed(Callback callback) {
		panCursorToolMenuItemPressed = callback;
	}
	
	public void setOnPlaceCursorToolMenuItemPressed(Callback callback) {
		placeCursorToolMenuItemPressed = callback;
	}
	
	public void setOnSelectCursorToolMenuItemPressed(Callback callback) {
		selectCursorToolMenuItemPressed = callback;
	}
	
	public void setOnMoveCursorToolMenuItemPressed(Callback callback) {
		moveCursorToolMenuItemPressed = callback;
	}
	
	public void setOnWireCursorToolMenuItemPressed(Callback callback) {
		wireCursorToolMenuItemPressed = callback;
	}

	// Set CursorTool
	private void setPanCursorTool() {
		changeCursorTool(CursorToolSelection.PAN);
		projectModel.updateRedrawObservable();
	}

	private void setPlaceCursorTool() {
		changeCursorTool(CursorToolSelection.PLACE);
		projectModel.updateRedrawObservable();
	}

	private void setSelectCursorTool() {
		changeCursorTool(CursorToolSelection.SELECT);
		projectModel.updateRedrawObservable();
	}

	private void setMoveCursorTool() {
		changeCursorTool(CursorToolSelection.MOVE);
		projectModel.updateRedrawObservable();
	}

	private void setWireCursorTool() {
		changeCursorTool(CursorToolSelection.WIRE);
		projectModel.updateRedrawObservable();
	}

	// Menu
	// File Menu
	// File -> New Project
	public void newProjectFileMenuItemEventHandler(ActionEvent event) {

	}

	// File -> Open Project
	public void openProjectFileMenuItemEventHandler(ActionEvent event) {

	}

	// File -> Save Project
	public void saveProjectFileMenuItemEventHandler(ActionEvent event) {

	}

	// File -> Exit
	public void exitFileMenuItemEventHandler(ActionEvent event) {
		exitFileMenuItemPressed.callback();
	}

	// CursorTool Menu
	// CursorTool -> Pan Tool
	public void panCursorToolMenuItemEventHandler(ActionEvent event) {
		panCursorToolMenuItemPressed.callback();
		setPanCursorTool();
	}

	// CursorTool -> Place Tool
	public void placeCursorToolMenuItemEventHandler(ActionEvent event) {
		placeCursorToolMenuItemPressed.callback();
		setPlaceCursorTool();
	}

	// CursorTool -> Select Tool
	public void selectCursorToolMenuItemEventHandler(ActionEvent event) {
		selectCursorToolMenuItemPressed.callback();
		setSelectCursorTool();
	}

	// CursorTool -> Move Tool
	public void moveCursorToolMenuItemEventHandler(ActionEvent event) {
		moveCursorToolMenuItemPressed.callback();
		setMoveCursorTool();
	}

	// CursorTool -> Wire Tool
	public void wireCursorToolMenuItemEventHandler(ActionEvent event) {
		wireCursorToolMenuItemPressed.callback();
		setWireCursorTool();
	}

	// Edit Menu
	// Edit -> Cut
	public void cutEditMenuItemEventHandler(ActionEvent event) {

	}

	// Edit -> Copy
	public void copyEditMenuItemEventHandler(ActionEvent event) {

	}

	// Edit -> Paste
	public void pasteEditMenuItemEventHandler(ActionEvent event) {

	}

	// Edit -> Delete
	public void deleteEditMenuItemEventHandler(ActionEvent event) {
		projectModel.deleteSelected();
		projectModel.updateRedrawObservable();
	}

	// Run Menu
	// Run -> Run
	public void runRunMenuItemEventHandler(ActionEvent event) {
		run();
	}

	// Run -> Run Continuously
	public void runContinuouslyRunMenuItemEventHandler(ActionEvent event) {

	}

	// Help Menu
	// Help -> Tutorial
	public void tutorialHelpItemEventHandler(ActionEvent event) {

	}

	// ToolBar
	// File ToolBar
	// File -> New Project
	public void newProjectFileToolBarItemEventHandler(ActionEvent event) {

	}

	// File -> Open Project
	public void openProjectFileToolBarItemEventHandler(ActionEvent event) {

	}

	// File -> Save Project
	public void saveProjectFileToolBarItemEventHandler(ActionEvent event) {

	}

	// CursorTool ToolBar
	// CursorTool -> Pan
	public void panCursorToolItemEventHandler(ActionEvent event) {
		setPanCursorTool();
	}

	// CursorTool -> Place
	public void placeCursorToolItemEventHandler(ActionEvent event) {
		setPlaceCursorTool();
	}

	// CursorTool -> Select
	public void selectCursorToolItemEventHandler(ActionEvent event) {
		setSelectCursorTool();
	}

	// CursorTool -> Move
	public void moveCursorToolItemEventHandler(ActionEvent event) {
		setMoveCursorTool();
	}

	// CursorTool -> Wire
	public void wireCursorToolItemEventHandler(ActionEvent event) {
		setWireCursorTool();
	}

	// Edit ToolBar
	// Edit -> Cut
	public void cutEditToolBarItemEventHandler(ActionEvent event) {

	}

	// Edit -> Copy
	public void copyEditToolBarItemEventHandler(ActionEvent event) {

	}

	// Edit -> Paste
	public void pasteEditToolBarItemEventHandler(ActionEvent event) {

	}

	// Edit -> Delete
	public void deleteEditToolBarItemEventHandler(ActionEvent event) {

	}

	// Run ToolBar
	// Run -> Run
	public void runRunToolBarItemEventHandler(ActionEvent event) {

	}

	// Run -> Run Continuously
	public void runContinuouslyRunToolBarItemEventHandler(ActionEvent event) {

	}

	private void changeCursorTool(CursorToolSelection newCursorTool) {
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
	private void run() {
		projectModel.getBlocks().forEach(block -> block.run(projectModel.getConsoleOutputObservable()));
	}
}
