package com.ogpe.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.ogpe.block.BlockType;
import com.ogpe.block.Provider;
import com.ogpe.observable.Callback;
import com.ogpe.observable.Observer;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Project {

	private ProjectModel projectModel;
	private CursorTools cursorTools;

	private Provider<Stage> stageProvider;

	private CursorToolSelection cursorTool;

	private Callback exitFileMenuItemPressed;
	private Callback panCursorToolMenuItemPressed;
	private Callback placeCursorToolMenuItemPressed;
	private Callback selectCursorToolMenuItemPressed;
	private Callback moveCursorToolMenuItemPressed;
	private Callback wireCursorToolMenuItemPressed;

	public Project(Provider<Stage> stageProvider, Callback redrawCallback, Observer<Node> editingPanePaneObserver,
			Observer<String> consoleOutputObserver) {
		this.stageProvider = stageProvider;
		projectModel = new ProjectModel(redrawCallback, editingPanePaneObserver, consoleOutputObserver);
		cursorTools = new CursorTools(projectModel);
	}

	public void drawCanvas(GraphicsContext context) {
		projectModel.getBlocks().forEach(block -> {
			block.drawBlock(context);
		});

		cursorTools.drawDisplay(context);
	}

	// Event Handlers
	public void onMouseMoved(MouseEvent mouseEvent) {
		cursorTools.onMouseMoved(cursorTool, mouseEvent);
		projectModel.callbackRedraw();
	}

	public void onMousePressed(MouseEvent mouseEvent) {
		cursorTools.onMousePressed(cursorTool, mouseEvent);
		projectModel.callbackRedraw();
	}

	public void onMouseDragged(MouseEvent mouseEvent) {
		cursorTools.onMouseDragged(cursorTool, mouseEvent);
		projectModel.callbackRedraw();
	}

	public void onMouseReleased(MouseEvent mouseEvent) {
		cursorTools.onMouseReleased(cursorTool, mouseEvent);
		projectModel.callbackRedraw();
	}

	public void onMouseEntered(MouseEvent mouseEvent) {
		softResetDisplayingContext();
		projectModel.callbackRedraw();
	}

	public void onMouseExited(MouseEvent mouseEvent) {
		softResetDisplayingContext();
		projectModel.callbackRedraw();
	}

	public void onBlockSelectionChanged(ObservableValue<? extends BlockType> observable, BlockType oldValue,
			BlockType newValue) {
		cursorTools.setPlacingBlockSelection(newValue);
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
		projectModel.callbackRedraw();
	}

	private void setPlaceCursorTool() {
		changeCursorTool(CursorToolSelection.PLACE);
		projectModel.callbackRedraw();
	}

	private void setSelectCursorTool() {
		changeCursorTool(CursorToolSelection.SELECT);
		projectModel.callbackRedraw();
	}

	private void setMoveCursorTool() {
		changeCursorTool(CursorToolSelection.MOVE);
		projectModel.callbackRedraw();
	}

	private void setWireCursorTool() {
		changeCursorTool(CursorToolSelection.WIRE);
		projectModel.callbackRedraw();
	}

	// New/Save/Open Project
	private void newProject() {
		projectModel.newProject();
	}
	
	private void openProject() {
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter extensionFilter = new ExtensionFilter("GPE files (*.gpe)", "*.gpe");
		fileChooser.getExtensionFilters().add(extensionFilter);

		File openFile = fileChooser.showOpenDialog(stageProvider.provide());

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(openFile));
			projectModel.openProject(bufferedReader);
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveProject() {
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter extensionFilter = new ExtensionFilter("GPE files (*.gpe)", "*.gpe");
		fileChooser.getExtensionFilters().add(extensionFilter);

		File saveFile = fileChooser.showSaveDialog(stageProvider.provide());

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
			projectModel.saveProject(bufferedWriter);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Menu
	// File Menu
	// File -> New Project
	public void newProjectFileMenuItemEventHandler(ActionEvent event) {
		newProject();
	}

	// File -> Open Project
	public void openProjectFileMenuItemEventHandler(ActionEvent event) {
		openProject();
	}

	// File -> Save Project
	public void saveProjectFileMenuItemEventHandler(ActionEvent event) {
		saveProject();
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
		cursorTools.deleteSelectedBlocks();
		projectModel.callbackRedraw();
	}

	// Run Menu
	// Run -> Run
	public void runRunMenuItemEventHandler(ActionEvent event) {
		projectModel.run();
	}

	// Run -> Run Continuously
	public void runRunContinuouslyMenuItemEventHandler(ActionEvent event) {
		projectModel.runContinuously();
	}

	// Help Menu
	// Help -> Tutorial
	public void tutorialHelpItemEventHandler(ActionEvent event) {

	}

	// ToolBar
	// File ToolBar
	// File -> New Project
	public void newProjectFileToolBarItemEventHandler(ActionEvent event) {
		newProject();
	}

	// File -> Open Project
	public void openProjectFileToolBarItemEventHandler(ActionEvent event) {
		openProject();
	}

	// File -> Save Project
	public void saveProjectFileToolBarItemEventHandler(ActionEvent event) {
		saveProject();
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
		cursorTools.deleteSelectedBlocks();
		projectModel.callbackRedraw();
	}

	// Run ToolBar
	// Run -> Run
	public void runRunToolBarItemEventHandler(ActionEvent event) {
		projectModel.run();
	}

	// Run -> Run Continuously
	public void runRunContinuouslyToolBarItemEventHandler(ActionEvent event) {
		projectModel.runContinuously();
	}

	public void runRunContinuouslyToolBarChangeListener(ObservableValue<? extends String> observable, String oldValue,
			String newValue) {
		if (newValue.matches("\\d*")) {
			int newMaxRunningIterations;
			if (newValue.isEmpty()) {
				newMaxRunningIterations = 0;
			} else {
				newMaxRunningIterations = Integer.parseInt(newValue);
			}
			projectModel.setMaxRunnningIterations(newMaxRunningIterations);
		}
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

}
