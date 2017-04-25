package com.ogpe.project;

import javafx.scene.input.MouseEvent;

public abstract class CursorTool {

	private ProjectModel projectModel;

	public CursorTool(ProjectModel projectModel) {
		this.projectModel = projectModel;
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
}
