package com.ogpe.fx;

import com.ogpe.observable.Observer;
import com.ogpe.project.BlockSelection;
import com.ogpe.project.CursorToolSelection;
import com.ogpe.project.Project;
import com.sun.glass.ui.Screen;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
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

	private CanvasPane canvasPane;

	private CursorToolSelection selectedCursorTool;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("OpenGPE Editor");
		BorderPane root = new BorderPane();

		// Block editing pane
		BorderPane blockEditingPane = new BorderPane();
		root.setRight(blockEditingPane);

		Label blockEditingLabel = new Label("Block Editor");
		blockEditingPane.setTop(blockEditingLabel);

		Group blockEditingPaneCenter = new Group();
		blockEditingPane.setCenter(new ScrollPane(blockEditingPaneCenter));

		// Console Pane
		ConsoleOutputPane consoleOutputPane = new ConsoleOutputPane();
		root.setBottom(consoleOutputPane);
		Observer<String> consoleOutputObserver = text -> consoleOutputPane.appendText(text);

		// Canvas
		canvasPane = new CanvasPane();
		root.setCenter(canvasPane);

		Observer<Object> canvasRedrawObserver = value -> canvasPane.redraw();
		Observer<Node> editingPaneObserver = editingPane -> {
			blockEditingPaneCenter.getChildren().clear();
			if (editingPane != null) {
				blockEditingPaneCenter.getChildren().add(editingPane);
			}
			canvasPane.redraw();
		};
		project = new Project(canvasRedrawObserver, editingPaneObserver, consoleOutputObserver);
		canvasPane.setDrawer(project::drawCanvas);
		canvasPane.setOnMouseMoved(project::onMouseMoved);
		canvasPane.setOnMousePressed(project::onMousePressed);
		canvasPane.setOnMouseReleased(project::onMouseReleased);
		canvasPane.setOnMouseDragged(project::onMouseDragged);
		canvasPane.setOnMouseEntered(project::onMouseEntered);
		canvasPane.setOnMouseExited(project::onMouseExited);

		// Block selection pane
		BorderPane blockSelectionPane = new BorderPane();
		root.setLeft(new ScrollPane(blockSelectionPane));
		// Block selection pane -> selected block
		Label selectedBlockLabel = new Label();
		blockSelectionPane.setTop(selectedBlockLabel);
		// Block selection pane -> block selection list
		ListView<String> blockSelectionList = new ListView<>();
		blockSelectionPane.setCenter(blockSelectionList);

		for (BlockSelection block : BlockSelection.values()) {
			blockSelectionList.getItems().add(block.getDisplayName());
		}
		blockSelectionList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			project.getProjectModel().setPlacingBlockSelection(BlockSelection.valueOfDisplayName(newValue));
			selectedBlockLabel.setText("Selected: " + newValue);
		});
		blockSelectionList.getSelectionModel().select(0);

		// Top menu
		VBox topContaioner = new VBox();
		root.setTop(topContaioner);

		MenuBar menuBar = new MenuBar();
		ToolBar toolBar = new ToolBar();
		topContaioner.getChildren().addAll(menuBar, toolBar);

		// Sub-menus
		Menu fileMenu = new Menu("File");
		Menu toolMenu = new Menu("Tool");
		Menu editMenu = new Menu("Edit");
		Menu runMenu = new Menu("Run");
		Menu helpMenu = new Menu("Help");
		menuBar.getMenus().addAll(fileMenu, toolMenu, editMenu, runMenu, helpMenu);

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

		// Tool menu items
		MenuItem panToolMenuItem = new MenuItem("Pan Tool");
		MenuItem placeToolMenuItem = new MenuItem("Place Tool");
		MenuItem selectToolMenuItem = new MenuItem("Select Tool");
		MenuItem moveToolMenuItem = new MenuItem("Move Tool");
		MenuItem wireToolMenuItem = new MenuItem("Wire Tool");
		toolMenu.getItems().addAll(panToolMenuItem, placeToolMenuItem, selectToolMenuItem, moveToolMenuItem,
				wireToolMenuItem);

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
			project.getProjectModel().deleteSelected();
			canvasPane.redraw();
		});
		deleteEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN));

		// Run menu items
		MenuItem runRunMenuItem = new MenuItem("Run");
		MenuItem runContinuouslyRunMenuItem = new MenuItem("Run Continously");
		runMenu.getItems().addAll(runRunMenuItem, runContinuouslyRunMenuItem);

		// Run -> Run
		runRunMenuItem.setOnAction(event -> {
			project.run();
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
		RadioButton toolPanToolBarItem = new RadioButton("Pan");
		RadioButton toolPlaceToolBarItem = new RadioButton("Place");
		RadioButton toolSelectToolBarItem = new RadioButton("Select");
		RadioButton toolMoveToolBarItem = new RadioButton("Move");
		RadioButton toolWireToolBarItem = new RadioButton("Wire");
		ToggleGroup toolToolBarToggleGroup = new ToggleGroup();
		toolToolBarToggleGroup.getToggles().addAll(toolPanToolBarItem, toolPlaceToolBarItem, toolSelectToolBarItem,
				toolMoveToolBarItem, toolWireToolBarItem);
		// ToolBar edit items
		Button cutToolBarItem = new Button("Cut");
		Button copyToolBarItem = new Button("Copy");
		Button pasteToolBarItem = new Button("Paste");
		Button deleteToolBarItem = new Button("Delete");
		// ToolBar run items
		Button runToolBarItem = new Button("Run");
		toolBar.getItems().addAll(newProjectToolBarItem, openProjectToolBarItem, saveProjectToolBarItem,
				new Separator(), toolPanToolBarItem, toolPlaceToolBarItem, toolSelectToolBarItem, toolMoveToolBarItem,
				toolWireToolBarItem, new Separator(), cutToolBarItem, copyToolBarItem, pasteToolBarItem,
				deleteToolBarItem, new Separator(), runToolBarItem);

		// ToolBar file items
		// ToolBar file new project
		newProjectToolBarItem.setOnAction(newProjectFileMenuItem.getOnAction());
		// ToolBar file open project
		openProjectToolBarItem.setOnAction(openProjectFileMenuItem.getOnAction());
		// ToolBar file save project
		saveProjectToolBarItem.setOnAction(saveProjectFileMenuItem.getOnAction());
		// ToolBar tool items
		// ToolBar pan tool
		toolPanToolBarItem.getStyleClass().remove("radio-button");
		toolPanToolBarItem.getStyleClass().add("toggle-button");
		toolPanToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorToolSelection.PAN;
			project.changeCursorTool(selectedCursorTool);
			canvasPane.redraw();
		});
		toolPanToolBarItem.fire();
		// ToolBar place tool
		toolPlaceToolBarItem.getStyleClass().remove("radio-button");
		toolPlaceToolBarItem.getStyleClass().add("toggle-button");
		toolPlaceToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorToolSelection.PLACE;
			project.changeCursorTool(selectedCursorTool);
			canvasPane.redraw();
		});
		// ToolBar select tool
		toolSelectToolBarItem.getStyleClass().remove("radio-button");
		toolSelectToolBarItem.getStyleClass().add("toggle-button");
		toolSelectToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorToolSelection.SELECT;
			project.changeCursorTool(selectedCursorTool);
			canvasPane.redraw();
		});
		// ToolBar move tool
		toolMoveToolBarItem.getStyleClass().remove("radio-button");
		toolMoveToolBarItem.getStyleClass().add("toggle-button");
		toolMoveToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorToolSelection.MOVE;
			project.changeCursorTool(selectedCursorTool);
			canvasPane.redraw();
		});
		// ToolBar wire tool
		toolWireToolBarItem.getStyleClass().remove("radio-button");
		toolWireToolBarItem.getStyleClass().add("toggle-button");
		toolWireToolBarItem.setOnAction(event -> {
			selectedCursorTool = CursorToolSelection.WIRE;
			project.changeCursorTool(selectedCursorTool);
			canvasPane.redraw();
		});

		// Tool menu items
		// Tool -> Pan Tool
		panToolMenuItem.setOnAction(event -> toolPanToolBarItem.fire());
		panToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q));
		// Tool -> Place Tool
		placeToolMenuItem.setOnAction(event -> toolPlaceToolBarItem.fire());
		placeToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A));
		// Tool -> Select Tool
		selectToolMenuItem.setOnAction(event -> toolSelectToolBarItem.fire());
		selectToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S));
		// Tool -> Move Tool
		moveToolMenuItem.setOnAction(event -> toolMoveToolBarItem.fire());
		moveToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D));
		// Tool -> Wire Tool
		wireToolMenuItem.setOnAction(event -> toolWireToolBarItem.fire());
		wireToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.W));

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
