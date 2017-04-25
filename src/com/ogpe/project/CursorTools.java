package com.ogpe.project;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.input.MouseEvent;

public class CursorTools {

	private List<CursorTool> cursorTools;

	public CursorTools(ProjectModel projectModel) {
		cursorTools = new ArrayList<>();
		cursorTools.add(new PanCursorTool(projectModel));
		cursorTools.add(new PlaceCursorTool(projectModel));
		cursorTools.add(new SelectCursorTool(projectModel));
		cursorTools.add(new MoveCursorTool(projectModel));
		cursorTools.add(new WireCursorTool(projectModel));
	}

	private void forEachCursorTool(Consumer<? super CursorTool> action) {
		cursorTools.stream().forEach(action);
	}

	private void forCursorTool(CursorToolSelection cursorToolSelection, Consumer<? super CursorTool> action) {
		cursorTools.stream().filter(cursorTool -> cursorTool.getCursorToolSelection().equals(cursorToolSelection))
				.forEach(action);
	}

	public void onMouseMoved(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		forCursorTool(cursorToolSelection, cursorTool -> cursorTool.onMouseMoved(mouseEvent));
	}

	public void onMousePressed(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		forCursorTool(cursorToolSelection, cursorTool -> cursorTool.onMousePressed(mouseEvent));
	}

	public void onMouseDragged(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		forCursorTool(cursorToolSelection, cursorTool -> cursorTool.onMouseDragged(mouseEvent));
	}

	public void onMouseReleased(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		forCursorTool(cursorToolSelection, cursorTool -> cursorTool.onMouseReleased(mouseEvent));
	}

	public void changeCursorTool(CursorToolSelection cursorToolSelection) {
		forCursorTool(cursorToolSelection, cursorTool -> cursorTool.selectedCursorTool());
	}

	public void softResetDisplayingContext() {
		forEachCursorTool(cursorTool -> cursorTool.softResetDisplayingContext());
	}

	public void hardResetDisplayingContext() {
		forEachCursorTool(cursorTool -> cursorTool.hardResetDisplayingContext());
	}
}
