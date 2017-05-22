package com.ogpe.project;

import com.ogpe.blockx.Point;
import com.ogpe.blockx.WireNode;
import com.ogpe.blockx.WireNodeHighlight;
import com.ogpe.blockx.WireNodeType;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class WireCursorTool extends CursorTool {

	private boolean wiring;
	private WireNode wiringNode;
	private Point wiringStart;
	private Point wireCurrent;

	public WireCursorTool(ProjectModel projectModel) {
		super(projectModel, CursorToolSelection.WIRE);

		wiring = false;
		wiringNode = null;
		wiringStart = new Point();
		wireCurrent = new Point();
	}

	private WireNode getClosestWireNode(Point point) {
		double maximumDistance = 16;
		WireNode closestNetworkNode = null;
		double closestNetworkNodeDistance = 0;
		for (WireNode networkNode : getProjectModel().getWireNodes()) {
			double distance = point.distance(networkNode.getLocation());
			if (distance <= maximumDistance && (closestNetworkNode == null || distance <= closestNetworkNodeDistance)) {
				closestNetworkNode = networkNode;
				closestNetworkNodeDistance = distance;
			}
		}
		return closestNetworkNode;
	}

	private boolean isValidNodeForWiring(WireNode wireNode) {
		return !wiringNode.isSet() && wiringNode.isValidProvider(wireNode);
	}

	@Override
	public void drawDisplay(GraphicsContext context) {
		if (wiring) {
			context.setStroke(Color.GREEN);
			double wireX1 = Math.round(wiringStart.x) - 0.5;
			double wireY1 = Math.round(wiringStart.y) - 0.5;
			double wireX2 = Math.round(wireCurrent.x) - 0.5;
			double wireY2 = Math.round(wireCurrent.y) - 0.5;
			context.strokeLine(wireX1, wireY1, wireX2, wireY2);
		}
	}

	@Override
	public void softResetDisplayingContext() {

	}

	@Override
	public void hardResetDisplayingContext() {
		wiring = false;
		getProjectModel().getWireNodes().forEach(wireNode -> {
			switch (wireNode.getHighlight()) {
			case HOVERING:
				wireNode.setHighlight(WireNodeHighlight.UNSET);
				break;
			case WIRING:
				wireNode.setHighlight(WireNodeHighlight.UNSET);
				break;
			case HOVERING_WIRING_VALID:
				wireNode.setHighlight(WireNodeHighlight.UNSET);
				break;
			case HOVERING_WIRING_INVALID:
				wireNode.setHighlight(WireNodeHighlight.UNSET);
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void selectedCursorTool() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMoved(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

		// Reset Highlight
		getProjectModel().getWireNodes().forEach(wireNode -> {
			switch (wireNode.getHighlight()) {
			case HOVERING:
				wireNode.setHighlight(WireNodeHighlight.UNSET);
				break;
			default:
				break;
			}
		});

		// Highlight
		WireNode closestWireNode = getClosestWireNode(point);
		if (closestWireNode != null) {
			switch (closestWireNode.getHighlight()) {
			case UNSET:
				closestWireNode.setHighlight(WireNodeHighlight.HOVERING);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onMousePressed(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

		wiringNode = getClosestWireNode(point);
		if (wiringNode != null && !wiringNode.isSet()) {
			wiringNode.set();
			wiringNode.setHighlight(WireNodeHighlight.WIRING);

			wiring = true;
			wiringStart = wiringNode.getLocation();
			wireCurrent = point;
		}
	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

		if (wiring) {
			// Reset Highlight
			getProjectModel().getWireNodes().forEach(wireNode -> {
				switch (wireNode.getHighlight()) {
				case HOVERING_WIRING_VALID:
					wireNode.setHighlight(WireNodeHighlight.UNSET);
					break;
				case HOVERING_WIRING_INVALID:
					wireNode.setHighlight(WireNodeHighlight.UNSET);
					break;
				default:
					break;
				}
			});

			// Highlight
			WireNode closestWireNode = getClosestWireNode(point);
			if (closestWireNode != null && closestWireNode != wiringNode
					&& closestWireNode.getHighlight().equals(WireNodeHighlight.UNSET)) {
				if (isValidNodeForWiring(closestWireNode)) {
					closestWireNode.setHighlight(WireNodeHighlight.HOVERING_WIRING_VALID);
				} else {
					closestWireNode.setHighlight(WireNodeHighlight.HOVERING_WIRING_INVALID);
				}
			}

			wireCurrent = point;
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

		if (wiring) {
			WireNode closestWireNode = getClosestWireNode(point);
			if (closestWireNode != null && closestWireNode != wiringNode) {
				if (isValidNodeForWiring(closestWireNode)) {
					closestWireNode.set();
					if (closestWireNode.nodeType == WireNodeType.OUTPUT) {
						closestWireNode.setProvider(wiringNode);
					} else {
						wiringNode.setProvider(closestWireNode);
					}
					wiringNode.setHighlight(WireNodeHighlight.SET);
					closestWireNode.setHighlight(WireNodeHighlight.SET);
				} else {
					wiringNode.setHighlight(WireNodeHighlight.UNSET);
					wiringNode.unset();
				}
			} else {
				wiringNode.setHighlight(WireNodeHighlight.UNSET);
				wiringNode.unset();
			}

			// Reset Highlight
			getProjectModel().getWireNodes().forEach(wireNode -> {
				switch (wireNode.getHighlight()) {
				case HOVERING_WIRING_VALID:
					wireNode.setHighlight(WireNodeHighlight.UNSET);
					break;
				case HOVERING_WIRING_INVALID:
					wireNode.setHighlight(WireNodeHighlight.UNSET);
					break;
				default:
					break;
				}
			});

			wiring = false;
			wiringStart = new Point();
			wiringNode = null;
		}
	}
	
}
