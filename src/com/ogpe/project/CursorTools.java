package com.ogpe.project;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.ogpe.blockx.BlockType;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class CursorTools {

	private Map<CursorToolSelection, CursorTool> cursorTools;

	private PanCursorTool panCursorTool;
	private PlaceCursorTool placeCursorTool;
	private SelectCursorTool selectCursorTool;
	private MoveCursorTool moveCursorTool;
	private WireCursorTool wireCursorTool;

	public CursorTools(ProjectModel projectModel) {
		cursorTools = new HashMap<>();

		panCursorTool = new PanCursorTool(projectModel);
		placeCursorTool = new PlaceCursorTool(projectModel);
		selectCursorTool = new SelectCursorTool(projectModel);
		moveCursorTool = new MoveCursorTool(projectModel);
		wireCursorTool = new WireCursorTool(projectModel);

		cursorTools.put(CursorToolSelection.PAN, panCursorTool);
		cursorTools.put(CursorToolSelection.PLACE, placeCursorTool);
		cursorTools.put(CursorToolSelection.SELECT, selectCursorTool);
		cursorTools.put(CursorToolSelection.MOVE, moveCursorTool);
		cursorTools.put(CursorToolSelection.WIRE, wireCursorTool);
	}

	private void forEachCursorTool(Consumer<? super CursorTool> action) {
		cursorTools.values().stream().forEach(action);
	}

	public void drawDisplay(GraphicsContext context) {
		forEachCursorTool(cursorTool -> cursorTool.drawDisplay(context));
	}

	public void softResetDisplayingContext() {
		forEachCursorTool(cursorTool -> cursorTool.softResetDisplayingContext());
	}

	public void hardResetDisplayingContext() {
		forEachCursorTool(cursorTool -> cursorTool.hardResetDisplayingContext());
	}

	public void onMouseMoved(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		cursorTools.get(cursorToolSelection).onMouseMoved(mouseEvent);
	}

	public void onMousePressed(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		cursorTools.get(cursorToolSelection).onMousePressed(mouseEvent);
	}

	public void onMouseDragged(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		cursorTools.get(cursorToolSelection).onMouseDragged(mouseEvent);
	}

	public void onMouseReleased(CursorToolSelection cursorToolSelection, MouseEvent mouseEvent) {
		cursorTools.get(cursorToolSelection).onMouseReleased(mouseEvent);
	}

	public void changeCursorTool(CursorToolSelection cursorToolSelection) {
		cursorTools.get(cursorToolSelection).selectedCursorTool();
	}

	public void setPlacingBlockSelection(BlockType placingBlockSelection) {
		placeCursorTool.setPlacingBlockSelection(placingBlockSelection);
	}

	public void deleteSelectedBlocks() {
		selectCursorTool.deleteSelectedBlocks();
	}
}
