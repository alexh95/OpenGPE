package com.ogpe.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("OpenGPE Editor");

		BorderPane primaryPane = new BorderPane();

		Scene primaryScene = new Scene(primaryPane);

		primaryStage.setScene(primaryScene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}
