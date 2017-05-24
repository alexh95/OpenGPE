package com.ogpe.project;

import com.ogpe.block.Point;
import com.ogpe.block.wire.WireLink;
import com.ogpe.block.wire.WireNode;
import com.ogpe.block.wire.WireNodeHighlight;
import com.ogpe.block.wire.WireNodeType;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class WireCursorTool extends CursorTool {

	private boolean wiring;
	private WireNode wiringNode;
	private Point wiringStart;
	private Point wireCurrent;
	private boolean validWiring;

	public WireCursorTool(ProjectModel projectModel) {
		super(projectModel, CursorToolSelection.WIRE);

		wiring = false;
		wiringNode = null;
		wiringStart = new Point();
		wireCurrent = new Point();
		validWiring = false;
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

	private boolean isNodeSettable(WireNode wireNode) {
		if (wireNode == null) {
			return false;
		}

		boolean contained = projectModel.getWireNetwork().contains(wireNode);
		boolean allowsMultiplicity = !wireNode.nodeType.equals(WireNodeType.INPUT);
		return !contained || allowsMultiplicity;
	}

	private boolean isValidLinkingNode(WireNode wireNode) {
		if (wireNode == null || wireNode == wiringNode) {
			return false;
		}

		boolean settable = isNodeSettable(wireNode);
		boolean validProvider = wiringNode.isValidProvider(wireNode);
		return settable && validProvider;
	}

	@Override
	public void drawDisplay(GraphicsContext context) {
		projectModel.getWireNetwork().getLinks().forEach(link -> {
			context.setStroke(Color.BLACK);
			Point srcLocation = link.getSrc().getLocation();
			Point dstLocation = link.getDst().getLocation();
			context.setStroke(Color.BLACK);
			double x1 = srcLocation.x;
			double y1 = srcLocation.y;
			double x2 = dstLocation.x;
			double y2 = dstLocation.y;
			context.strokeLine(x1, y1, x2, y2);
		});

		if (wiring) {
			if (validWiring) {
				context.setStroke(Color.GREEN);
			} else {
				context.setStroke(Color.RED);
			}
			double wireX1 = Math.round(wiringStart.x) + 0.5;
			double wireY1 = Math.round(wiringStart.y) + 0.5;
			double wireX2 = Math.round(wireCurrent.x) + 0.5;
			double wireY2 = Math.round(wireCurrent.y) + 0.5;
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
			case WIRING:
			case HOVERING_WIRING_VALID:
			case HOVERING_WIRING_INVALID:
				if (projectModel.getWireNetwork().contains(wireNode)) {
					wireNode.setHighlight(WireNodeHighlight.SET);
				} else {
					wireNode.setHighlight(WireNodeHighlight.UNSET);
				}
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
				if (projectModel.getWireNetwork().contains(wireNode)) {
					wireNode.setHighlight(WireNodeHighlight.SET);
				} else {
					wireNode.setHighlight(WireNodeHighlight.UNSET);
				}
				break;
			default:
				break;
			}
		});

		// Highlight
		WireNode closestWireNode = getClosestWireNode(point);
		if (closestWireNode != null) {
			if (isNodeSettable(closestWireNode)) {
				closestWireNode.setHighlight(WireNodeHighlight.HOVERING);
			}
		}
	}

	@Override
	public void onMousePressed(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
		boolean controlDown = mouseEvent.isControlDown();

		WireNode closestWireNode = getClosestWireNode(point);
		if (closestWireNode != null) {
			if (controlDown) {
				if (projectModel.getWireNetwork().contains(closestWireNode)) {
					projectModel.getWireNetwork().removeLinksContaining(closestWireNode);
					projectModel.getWireNodes().stream()
							.filter(wireNode -> !projectModel.getWireNetwork().contains(wireNode))
							.forEach(wireNode -> wireNode.setHighlight(WireNodeHighlight.UNSET));
				}
			} else {
				if (isNodeSettable(closestWireNode)) {
					wiringNode = closestWireNode;
					wiringNode.setHighlight(WireNodeHighlight.WIRING);

					wiring = true;
					wiringStart = wiringNode.getLocation();
					wireCurrent = point;
					validWiring = false;
				}
			}
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
				case HOVERING_WIRING_INVALID:
					if (projectModel.getWireNetwork().contains(wireNode)) {
						wireNode.setHighlight(WireNodeHighlight.SET);
					} else {
						wireNode.setHighlight(WireNodeHighlight.UNSET);
					}
					break;
				default:
					break;
				}
			});

			// Highlight
			WireNode closestWireNode = getClosestWireNode(point);
			if (closestWireNode != null && closestWireNode != wiringNode) {
				validWiring = isValidLinkingNode(closestWireNode);
				if (validWiring) {
					closestWireNode.setHighlight(WireNodeHighlight.HOVERING_WIRING_VALID);
				} else {
					closestWireNode.setHighlight(WireNodeHighlight.HOVERING_WIRING_INVALID);
				}
			} else {
				validWiring = false;
			}

			wireCurrent = point;
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

		if (wiring) {
			WireNode closestWireNode = getClosestWireNode(point);
			if (isValidLinkingNode(closestWireNode)) {
				WireNode src;
				WireNode dst;

				if (wiringNode.nodeType == WireNodeType.OUTPUT) {
					src = wiringNode;
					dst = closestWireNode;
				} else {
					src = closestWireNode;
					dst = wiringNode;
				}

				WireLink wireLink = new WireLink(src, dst);
				projectModel.getWireNetwork().addLink(wireLink);

				src.setHighlight(WireNodeHighlight.SET);
				dst.setHighlight(WireNodeHighlight.SET);
			}

			// Reset Highlight
			getProjectModel().getWireNodes().forEach(wireNode -> {
				switch (wireNode.getHighlight()) {
				case WIRING:
				case HOVERING_WIRING_VALID:
				case HOVERING_WIRING_INVALID:
					if (projectModel.getWireNetwork().contains(wireNode)) {
						wireNode.setHighlight(WireNodeHighlight.SET);
					} else {
						wireNode.setHighlight(WireNodeHighlight.UNSET);
					}
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
