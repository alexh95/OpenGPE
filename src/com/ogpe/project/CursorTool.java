package com.ogpe.project;

import javafx.scene.input.MouseEvent;

public abstract class CursorTool {

	private ProjectModel projectModel;
	
	private CursorToolSelection cursorToolSelection;

	public CursorTool(ProjectModel projectModel, CursorToolSelection cursorToolSelection) {
		this.projectModel = projectModel;
		this.cursorToolSelection = cursorToolSelection;
	}

	public abstract void softResetDisplayingContext();

	public abstract void hardResetDisplayingContext();

	public abstract void selectedCursorTool();

	public abstract void onMouseMoved(MouseEvent mouseEvent);

	public abstract void onMousePressed(MouseEvent mouseEvent);

	public abstract void onMouseDragged(MouseEvent mouseEvent);

	public abstract void onMouseReleased(MouseEvent mouseEvent);

	protected ProjectModel getProjectModel() {
		return projectModel;
	}

	public CursorToolSelection getCursorToolSelection() {
		return cursorToolSelection;
	}
}
