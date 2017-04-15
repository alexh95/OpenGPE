package com.ogpe.fx;

import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBlockModel;
import com.ogpe.block.view.implementation.ConstantBlockView;
import com.ogpe.project.Project;
import com.sun.glass.ui.Screen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
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

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("OpenGPE Editor");
		BorderPane root = new BorderPane();

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
		// File -> Open Project
		openProjectFileMenuItem.setOnAction(event -> {
		});
		// File -> Save Project
		saveProjectFileMenuItem.setOnAction(event -> {
		});
		// File -> Exit
		exitFileMenuItem.setOnAction(event -> {
			primaryStage.close();
		});
		exitFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.CONTROL_DOWN));

		// Edit menu items
		MenuItem copyEditMenuItem = new MenuItem("Copy");
		MenuItem pasteEditMenuItem = new MenuItem("Paste");
		editMenu.getItems().addAll(copyEditMenuItem, pasteEditMenuItem);

		// Edit -> Copy
		copyEditMenuItem.setOnAction(event -> {

		});
		// Edit -> Paste
		pasteEditMenuItem.setOnAction(event -> {

		});

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
			ConstantBlockModel constantBlockModel = new ConstantBlockModel(0);
			ConstantBlockBehavior constantBlock = new ConstantBlockBehavior(constantBlockModel);
			ConstantBlockView constantBlockView = new ConstantBlockView(constantBlockModel, x, y);
			project.addBlock(constantBlock, constantBlockView);
			canvasPane.redraw();
		});

		Scene primaryScene = new Scene(root);
		primaryStage.setScene(primaryScene);

		primaryStage.setWidth(Screen.getMainScreen().getVisibleWidth() >> 1);
		primaryStage.setHeight(Screen.getMainScreen().getVisibleHeight() >> 1);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}
