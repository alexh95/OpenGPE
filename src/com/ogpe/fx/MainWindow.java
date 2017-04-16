package com.ogpe.fx;

import com.ogpe.block.implementation.ConstantBlock;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantBlockView;
import com.ogpe.project.Project;
import com.sun.glass.ui.Screen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private Project project;

	private CursorTool selectedCursorTool;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("OpenGPE Editor");
		BorderPane root = new BorderPane();

		// Canvas
		CanvasPane canvasPane = new CanvasPane();
		root.setCenter(canvasPane);
		project = new Project();
		canvasPane.setDrawer(graphicsContext -> {
			project.forEachBlockView(blockView -> {
				blockView.drawBlock(graphicsContext);
			});
		});
		canvasPane.setOnMousePressed(event -> {
			double x = event.getX();
			double y = event.getY();
			boolean controlDown = event.isControlDown();

			switch (selectedCursorTool) {
			case PAN:
				break;
			case SELECT:
				project.selectBlock(x, y, controlDown);
				canvasPane.redraw();
				break;
			case PLACE:
				if (project.canPlace(x, y, ConstantBlockView.WIDTH, ConstantBlockView.HEIGHT)) {
					ConstantBlock constantBlock = new ConstantBlock(new ConstantBlockModel(0), x, y);
					project.addBlock(constantBlock);
					canvasPane.redraw();
				}
				break;
			case MOVE:
				break;
			}
		});

		// Top menu
		VBox topContaioner = new VBox();
		root.setTop(topContaioner);

		MenuBar menuBar = new MenuBar();
		ToolBar toolBar = new ToolBar();
		topContaioner.getChildren().addAll(menuBar, toolBar);

		// Sub-menus
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu runMenu = new Menu("Run");
		Menu helpMenu = new Menu("Help");
		menuBar.getMenus().addAll(fileMenu, editMenu, runMenu, helpMenu);

		// File menu items
		MenuItem newProjectFileMenuItem = new MenuItem("New Project");
		MenuItem openProjectFileMenuItem = new MenuItem("Open Project");
		MenuItem saveProjectFileMenuItem = new MenuItem("Save Project");
		MenuItem exitFileMenuItem = new MenuItem("Exit");
		fileMenu.getItems().addAll(newProjectFileMenuItem, openProjectFileMenuItem, saveProjectFileMenuItem,
				new SeparatorMenuItem(), exitFileMenuItem);

		// File -> New Project
		newProjectFileMenuItem.setOnAction(event -> {
		});
		newProjectFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
		// File -> Open Project
		openProjectFileMenuItem.setOnAction(event -> {
		});
		openProjectFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN));
		// File -> Save Project
		saveProjectFileMenuItem.setOnAction(event -> {
		});
		saveProjectFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));
		// File -> Exit
		exitFileMenuItem.setOnAction(event -> {
			// TODO: check unsaved changes and dialog stuff
			primaryStage.close();
		});
		exitFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.CONTROL_DOWN));

		// Edit menu items
		MenuItem cutEditMenuItem = new MenuItem("Cut");
		MenuItem copyEditMenuItem = new MenuItem("Copy");
		MenuItem pasteEditMenuItem = new MenuItem("Paste");
		MenuItem deleteEditMenuItem = new MenuItem("Delete");
		editMenu.getItems().addAll(cutEditMenuItem, copyEditMenuItem, pasteEditMenuItem, deleteEditMenuItem);

		// Edit -> Cut
		cutEditMenuItem.setOnAction(event -> {

		});
		cutEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCodeCombination.CONTROL_DOWN));
		// Edit -> Copy
		copyEditMenuItem.setOnAction(event -> {

		});
		copyEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN));
		// Edit -> Paste
		pasteEditMenuItem.setOnAction(event -> {

		});
		pasteEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCodeCombination.CONTROL_DOWN));
		// Edit -> Delete
		deleteEditMenuItem.setOnAction(event -> {
			project.deleteSelected();
			canvasPane.redraw();
		});
		deleteEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

		// Run menu items
		MenuItem runRunMenuItem = new MenuItem("Run");
		MenuItem runContinuouslyRunMenuItem = new MenuItem("Run Continously");
		runMenu.getItems().addAll(runRunMenuItem, runContinuouslyRunMenuItem);

		// Run -> Run
		runRunMenuItem.setOnAction(event -> {

		});
		// Run -> Run Continuously
		runContinuouslyRunMenuItem.setOnAction(event -> {

		});

		// Help menu items
		MenuItem tutorialHelpMenuItem = new MenuItem("Tutorial");
		helpMenu.getItems().addAll(tutorialHelpMenuItem);

		// ToolBar items
		// ToolBar file items
		Button newProjectToolBarItem = new Button("New");
		Button openProjectToolBarItem = new Button("Open");
		Button saveProjectToolBarItem = new Button("Save");
		// ToolBar cursor items
		RadioButton cursorPanToolBarItem = new RadioButton("Pan");
		RadioButton cursorSelectToolBarItem = new RadioButton("Select");
		RadioButton cursorPlaceToolBarItem = new RadioButton("Place");
		RadioButton cursorMoveToolBarItem = new RadioButton("Move");
		ToggleGroup cursorToolBarToggleGroup = new ToggleGroup();
		cursorToolBarToggleGroup.getToggles().addAll(cursorPanToolBarItem, cursorSelectToolBarItem,
				cursorPlaceToolBarItem, cursorMoveToolBarItem);
		// ToolBar edit items
		Button cutToolBarItem = new Button("Cut");
		Button copyToolBarItem = new Button("Copy");
		Button pasteToolBarItem = new Button("Paste");
		Button deleteToolBarItem = new Button("Delete");
		// ToolBar run items
		Button runToolBarItem = new Button("Run");
		toolBar.getItems().addAll(newProjectToolBarItem, openProjectToolBarItem, saveProjectToolBarItem,
				new Separator(), cursorPanToolBarItem, cursorSelectToolBarItem, cursorPlaceToolBarItem,
				cursorMoveToolBarItem, new Separator(), cutToolBarItem, copyToolBarItem, pasteToolBarItem,
				deleteToolBarItem, new Separator(), runToolBarItem);

		// ToolBar file items
		// ToolBar file new project
		newProjectToolBarItem.setOnAction(newProjectFileMenuItem.getOnAction());
		// ToolBar file open project
		openProjectToolBarItem.setOnAction(openProjectFileMenuItem.getOnAction());
		// ToolBar file save project
		saveProjectToolBarItem.setOnAction(saveProjectFileMenuItem.getOnAction());
		// ToolBar cursor items
		// ToolBar cursor pan
		cursorPanToolBarItem.getStyleClass().remove("radio-button");
		cursorPanToolBarItem.getStyleClass().add("toggle-button");
		cursorPanToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorTool.PAN;
		});
		cursorPanToolBarItem.fire();
		// ToolBar cursor select
		cursorSelectToolBarItem.getStyleClass().remove("radio-button");
		cursorSelectToolBarItem.getStyleClass().add("toggle-button");
		cursorSelectToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorTool.SELECT;
		});
		// ToolBar cursor place
		cursorPlaceToolBarItem.getStyleClass().remove("radio-button");
		cursorPlaceToolBarItem.getStyleClass().add("toggle-button");
		cursorPlaceToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorTool.PLACE;
		});
		// ToolBar cursor move
		cursorMoveToolBarItem.getStyleClass().remove("radio-button");
		cursorMoveToolBarItem.getStyleClass().add("toggle-button");
		cursorMoveToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorTool.MOVE;
		});

		// ToolBar edit items
		// ToolBar edit cut
		cutToolBarItem.setOnAction(event -> cutEditMenuItem.fire());
		// ToolBar edit copy
		copyToolBarItem.setOnAction(event -> copyEditMenuItem.fire());
		// ToolBar edit paste
		pasteToolBarItem.setOnAction(event -> pasteEditMenuItem.fire());
		// ToolBar edit delete
		deleteToolBarItem.setOnAction(event -> deleteEditMenuItem.fire());

		// ToolBar run items
		// ToolBar run run
		runToolBarItem.setOnAction(event -> runRunMenuItem.fire());

		Scene primaryScene = new Scene(root);
		primaryStage.setScene(primaryScene);

		primaryStage.setWidth(Screen.getMainScreen().getVisibleWidth() >> 1);
		primaryStage.setHeight(Screen.getMainScreen().getVisibleHeight() >> 1);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}
