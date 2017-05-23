package com.ogpe.fx;

import com.ogpe.blockx.BlockType;
import com.ogpe.observable.Callback;
import com.ogpe.observable.Observer;
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
import javafx.scene.control.TextField;
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

	private ConsoleOutputPane consoleOutputPane;

	private RadioButton panCursorToolToolBarItem;
	private RadioButton placeCursorToolToolBarItem;
	private RadioButton selectCursorToolToolBarItem;
	private RadioButton moveCursorToolToolBarItem;
	private RadioButton wireCursorToolToolBarItem;
	private ToggleGroup cursorToolToolBarToggleGroup;

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

		// Canvas
		CanvasPane canvasPane = new CanvasPane();
		root.setCenter(canvasPane);

		Callback canvasRedrawCallback = () -> canvasPane.redraw();
		Observer<Node> editingPaneObserver = editingPane -> {
			blockEditingPaneCenter.getChildren().clear();
			if (editingPane != null) {
				blockEditingPaneCenter.getChildren().add(editingPane);
			}
			canvasPane.redraw();
		};
		Observer<String> consoleOutputObserver = text -> consoleOutputPane.appendText(text);
		project = new Project(canvasRedrawCallback, editingPaneObserver, consoleOutputObserver);
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
		// Block selection pane -> block selection list
		ListView<BlockType> blockSelectionList = new ListView<>();
		blockSelectionPane.setCenter(blockSelectionList);

		for (BlockType block : BlockType.values()) {
			blockSelectionList.getItems().add(block);
		}
		blockSelectionList.getSelectionModel().selectedItemProperty().addListener(project::onBlockSelectionChanged);
		blockSelectionList.getSelectionModel().select(0);

		// Console Pane
		consoleOutputPane = new ConsoleOutputPane();
		root.setBottom(consoleOutputPane);

		// Top menu
		VBox topContaioner = new VBox();
		root.setTop(topContaioner);

		MenuBar menuBar = new MenuBar();
		ToolBar toolBar = new ToolBar();
		topContaioner.getChildren().addAll(menuBar, toolBar);

		// MenuBar
		Menu fileMenu = new Menu("File");
		Menu cursorToolMenu = new Menu("Tool");
		Menu editMenu = new Menu("Edit");
		Menu runMenu = new Menu("Run");
		Menu helpMenu = new Menu("Help");
		menuBar.getMenus().addAll(fileMenu, cursorToolMenu, editMenu, runMenu, helpMenu);

		// File menu items
		MenuItem newProjectFileMenuItem = new MenuItem("New Project");
		MenuItem openProjectFileMenuItem = new MenuItem("Open Project");
		MenuItem saveProjectFileMenuItem = new MenuItem("Save Project");
		MenuItem exitFileMenuItem = new MenuItem("Exit");
		fileMenu.getItems().addAll(newProjectFileMenuItem, openProjectFileMenuItem, saveProjectFileMenuItem,
				new SeparatorMenuItem(), exitFileMenuItem);

		// File -> New Project
		newProjectFileMenuItem.setOnAction(project::newProjectFileMenuItemEventHandler);
		newProjectFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
		// File -> Open Project
		openProjectFileMenuItem.setOnAction(project::openProjectFileMenuItemEventHandler);
		openProjectFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN));
		// File -> Save Project
		saveProjectFileMenuItem.setOnAction(project::saveProjectFileMenuItemEventHandler);
		saveProjectFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));
		// File -> Exit
		project.setOnExitFileMenuItemPressed(() -> primaryStage.close());
		exitFileMenuItem.setOnAction(project::exitFileMenuItemEventHandler);
		exitFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.CONTROL_DOWN));

		// Tool Menu
		MenuItem panCursorToolMenuItem = new MenuItem("Pan Tool");
		MenuItem placeCursorToolMenuItem = new MenuItem("Place Tool");
		MenuItem selectCursorToolMenuItem = new MenuItem("Select Tool");
		MenuItem moveCursorToolMenuItem = new MenuItem("Move Tool");
		MenuItem wireCursorToolMenuItem = new MenuItem("Wire Tool");
		cursorToolMenu.getItems().addAll(panCursorToolMenuItem, placeCursorToolMenuItem, selectCursorToolMenuItem,
				moveCursorToolMenuItem, wireCursorToolMenuItem);

		// CursorTool Menu
		// CursorTool -> Pan Tool
		project.setOnPanCursorToolMenuItemPressed(
				() -> cursorToolToolBarToggleGroup.selectToggle(panCursorToolToolBarItem));
		panCursorToolMenuItem.setOnAction(project::panCursorToolMenuItemEventHandler);
		panCursorToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q));
		// CursorTool -> Place Tool
		project.setOnPlaceCursorToolMenuItemPressed(
				() -> cursorToolToolBarToggleGroup.selectToggle(placeCursorToolToolBarItem));
		placeCursorToolMenuItem.setOnAction(project::placeCursorToolMenuItemEventHandler);
		placeCursorToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A));
		// CursorTool -> Select Tool
		project.setOnSelectCursorToolMenuItemPressed(
				() -> cursorToolToolBarToggleGroup.selectToggle(selectCursorToolToolBarItem));
		selectCursorToolMenuItem.setOnAction(project::selectCursorToolMenuItemEventHandler);
		selectCursorToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S));
		// CursorTool -> Move Tool
		project.setOnMoveCursorToolMenuItemPressed(
				() -> cursorToolToolBarToggleGroup.selectToggle(moveCursorToolToolBarItem));
		moveCursorToolMenuItem.setOnAction(project::moveCursorToolMenuItemEventHandler);
		moveCursorToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D));
		// CursorTool -> Wire Tool
		project.setOnWireCursorToolMenuItemPressed(
				() -> cursorToolToolBarToggleGroup.selectToggle(wireCursorToolToolBarItem));
		wireCursorToolMenuItem.setOnAction(project::wireCursorToolMenuItemEventHandler);
		wireCursorToolMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.W));

		// Edit Menu
		MenuItem cutEditMenuItem = new MenuItem("Cut");
		MenuItem copyEditMenuItem = new MenuItem("Copy");
		MenuItem pasteEditMenuItem = new MenuItem("Paste");
		MenuItem deleteEditMenuItem = new MenuItem("Delete");
		editMenu.getItems().addAll(cutEditMenuItem, copyEditMenuItem, pasteEditMenuItem, deleteEditMenuItem);

		// Edit -> Cut
		cutEditMenuItem.setOnAction(project::cutEditMenuItemEventHandler);
		cutEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCodeCombination.CONTROL_DOWN));
		// Edit -> Copy
		copyEditMenuItem.setOnAction(project::copyEditMenuItemEventHandler);
		copyEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN));
		// Edit -> Paste
		pasteEditMenuItem.setOnAction(project::pasteEditMenuItemEventHandler);
		pasteEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCodeCombination.CONTROL_DOWN));
		// Edit -> Delete
		deleteEditMenuItem.setOnAction(project::deleteEditMenuItemEventHandler);
		deleteEditMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN));

		// Run Menu
		MenuItem runRunMenuItem = new MenuItem("Run");
		MenuItem runRunContinuouslyMenuItem = new MenuItem("Run Continously");
		runMenu.getItems().addAll(runRunMenuItem, runRunContinuouslyMenuItem);

		// Run -> Run
		runRunMenuItem.setOnAction(project::runRunMenuItemEventHandler);
		runRunMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCodeCombination.CONTROL_DOWN));
		// Run -> Run Continuously
		runRunContinuouslyMenuItem.setOnAction(project::runRunContinuouslyMenuItemEventHandler);
		runRunContinuouslyMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCodeCombination.CONTROL_DOWN));

		// Help Menu
		MenuItem tutorialHelpMenuItem = new MenuItem("Tutorial");
		helpMenu.getItems().addAll(tutorialHelpMenuItem);

		// Help -> Tutorial
		tutorialHelpMenuItem.setOnAction(project::tutorialHelpItemEventHandler);
		tutorialHelpMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));

		// ToolBar
		// File ToolBar
		Button newProjectFileToolBarItem = new Button("New");
		Button openProjectFileToolBarItem = new Button("Open");
		Button saveProjectFileToolBarItem = new Button("Save");
		// CursorTool ToolBar
		panCursorToolToolBarItem = new RadioButton("Pan");
		placeCursorToolToolBarItem = new RadioButton("Place");
		selectCursorToolToolBarItem = new RadioButton("Select");
		moveCursorToolToolBarItem = new RadioButton("Move");
		wireCursorToolToolBarItem = new RadioButton("Wire");
		cursorToolToolBarToggleGroup = new ToggleGroup();
		cursorToolToolBarToggleGroup.getToggles().addAll(panCursorToolToolBarItem, placeCursorToolToolBarItem,
				selectCursorToolToolBarItem, moveCursorToolToolBarItem, wireCursorToolToolBarItem);
		// Edit ToolBar
		Button cutEditToolBarItem = new Button("Cut");
		Button copyEditToolBarItem = new Button("Copy");
		Button pasteEditToolBarItem = new Button("Paste");
		Button deleteEditToolBarItem = new Button("Delete");
		// Run ToolBar
		Button runRunToolBarItem = new Button("Run");
		Button runRunContinuouslyToolBarItem = new Button("Run Continuously");
		Label runRunContinuouslyMaxIterationsLabel = new Label("Max iterations:");
		TextField runRunContinuouslyMaxIterationsTextField = new TextField();
		toolBar.getItems().addAll(newProjectFileToolBarItem, openProjectFileToolBarItem, saveProjectFileToolBarItem,
				new Separator(), panCursorToolToolBarItem, placeCursorToolToolBarItem, selectCursorToolToolBarItem,
				moveCursorToolToolBarItem, wireCursorToolToolBarItem, new Separator(), cutEditToolBarItem,
				copyEditToolBarItem, pasteEditToolBarItem, deleteEditToolBarItem, new Separator(), runRunToolBarItem,
				runRunContinuouslyToolBarItem, runRunContinuouslyMaxIterationsLabel,
				runRunContinuouslyMaxIterationsTextField, new Separator());

		// File ToolBar
		// File -> New Project
		newProjectFileToolBarItem.setOnAction(project::newProjectFileToolBarItemEventHandler);
		// File -> Open Project
		openProjectFileToolBarItem.setOnAction(project::openProjectFileToolBarItemEventHandler);
		// File -> Save Project
		saveProjectFileToolBarItem.setOnAction(project::saveProjectFileToolBarItemEventHandler);

		// CursorTool ToolBar
		// CursorTool -> Pan
		panCursorToolToolBarItem.getStyleClass().remove("radio-button");
		panCursorToolToolBarItem.getStyleClass().add("toggle-button");
		panCursorToolToolBarItem.setOnAction(project::panCursorToolItemEventHandler);
		panCursorToolToolBarItem.fire();
		// CursorTool -> Place
		placeCursorToolToolBarItem.getStyleClass().remove("radio-button");
		placeCursorToolToolBarItem.getStyleClass().add("toggle-button");
		placeCursorToolToolBarItem.setOnAction(project::placeCursorToolItemEventHandler);
		// CursorTool -> Select
		selectCursorToolToolBarItem.getStyleClass().remove("radio-button");
		selectCursorToolToolBarItem.getStyleClass().add("toggle-button");
		selectCursorToolToolBarItem.setOnAction(project::selectCursorToolItemEventHandler);
		// CursorTool -> Move
		moveCursorToolToolBarItem.getStyleClass().remove("radio-button");
		moveCursorToolToolBarItem.getStyleClass().add("toggle-button");
		moveCursorToolToolBarItem.setOnAction(project::moveCursorToolItemEventHandler);
		// CursorTool -> Wire
		wireCursorToolToolBarItem.getStyleClass().remove("radio-button");
		wireCursorToolToolBarItem.getStyleClass().add("toggle-button");
		wireCursorToolToolBarItem.setOnAction(project::wireCursorToolItemEventHandler);

		// Edit ToolBar
		// Edit -> Cut
		cutEditToolBarItem.setOnAction(project::cutEditToolBarItemEventHandler);
		// Edit -> Copy
		copyEditToolBarItem.setOnAction(project::copyEditToolBarItemEventHandler);
		// Edit -> Paste
		pasteEditToolBarItem.setOnAction(project::pasteEditToolBarItemEventHandler);
		// Edit -> Delete
		deleteEditToolBarItem.setOnAction(project::deleteEditToolBarItemEventHandler);

		// Run ToolBar
		// Run -> Run
		runRunToolBarItem.setOnAction(project::runRunToolBarItemEventHandler);
		// Run -> Run Continuously
		runRunContinuouslyToolBarItem.setOnAction(project::runRunContinuouslyToolBarItemEventHandler);
		// Run -> Run Continuously Max Iterations
		runRunContinuouslyMaxIterationsTextField.textProperty()
				.addListener(project::runRunContinuouslyToolBarChangeListener);
		runRunContinuouslyMaxIterationsTextField.setText("1000");

		Scene primaryScene = new Scene(root);
		primaryStage.setScene(primaryScene);

		primaryStage.setWidth(Screen.getMainScreen().getVisibleWidth() >> 1);
		primaryStage.setHeight(Screen.getMainScreen().getVisibleHeight() >> 1);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}
