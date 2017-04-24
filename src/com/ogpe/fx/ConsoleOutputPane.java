package com.ogpe.fx;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class ConsoleOutputPane extends BorderPane {

	private TextArea textArea;
	
	public ConsoleOutputPane() {
		Label label = new Label("Console Output:");
		textArea = new TextArea();
		setTop(label);
		setCenter(textArea);
		textArea.setEditable(false);
	}
	
	public void appendText(String text) {
		textArea.appendText(text);
		textArea.appendText("\n");
	}
}
