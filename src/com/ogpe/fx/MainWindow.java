package com.ogpe.fx;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindow extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private List<DrawOperation> drawOperations;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("OpenGPE Editor");
		BorderPane root = new BorderPane();

		VBox topContaioner = new VBox();
		root.setTop(topContaioner);

		MenuBar menuBar = new MenuBar();
		ToolBar toolBar = new ToolBar();
		topContaioner.getChildren().addAll(menuBar, toolBar);

		// Sub-menus
		Menu fileMenu = new Menu("File");
		Menu fileHelp = new Menu("Help");
		menuBar.getMenus().addAll(fileMenu, fileHelp);

		// File sub-menu items
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

		// Help sub-menu items
		MenuItem tutorialHelpMenuItem = new MenuItem("Tutorial");
		fileHelp.getItems().addAll(tutorialHelpMenuItem);

		// Canvas
		CanvasPane canvasPane = new CanvasPane();
		root.setCenter(canvasPane);
		drawOperations = new ArrayList<>();
		canvasPane.setDrawer(graphicsContext -> {
			graphicsContext.setFill(Color.BLUE);
			drawOperations.forEach(drawOperation -> {
				double x = drawOperation.getX();
				double y = drawOperation.getY();
				double w = drawOperation.getWidth();
				double h = drawOperation.getHeight();
				graphicsContext.fillRect(x, y, w, h);
			});
		});
		canvasPane.setOnMousePressed(event -> {
			double x = event.getX();
			double y = event.getY();
			drawOperations.add(new DrawOperation(x, y, 16, 16));
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
