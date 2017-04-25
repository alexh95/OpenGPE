package com.ogpe.project;

import com.ogpe.block.network.NetworkNode;
import com.ogpe.block.network.NetworkNodeHighlight;
import com.ogpe.block.network.NetworkNodeType;

import javafx.scene.input.MouseEvent;

public class WireCursorTool extends CursorTool {

	public WireCursorTool(ProjectModel projectModel) {
		super(projectModel, CursorToolSelection.WIRE);
	}

	@Override
	public void softResetDisplayingContext() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hardResetDisplayingContext() {
		getProjectModel().setWiring(false);
		getProjectModel().getNetworkNodes().forEach(networkNode -> {
			switch (networkNode.getHighlight()) {
			case HOVER:
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				break;
			case WIRING:
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				break;
			case HOVER_VALID_WIRING:
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				break;
			case HOVER_INVALID_WIRING:
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
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
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		// Reset Highlight
		getProjectModel().getNetworkNodes().forEach(networkNode -> {
			switch (networkNode.getHighlight()) {
			case HOVER:
				networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				break;
			default:
				break;
			}
		});

		// Highlight
		NetworkNode<?> closestNetworkNode = getProjectModel().getClosestNetworkNode(x, y);
		if (closestNetworkNode != null) {
			switch (closestNetworkNode.getHighlight()) {
			case UNSET:
				closestNetworkNode.setHighlighted(NetworkNodeHighlight.HOVER);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onMousePressed(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		getProjectModel().setWiringNetworkNode(getProjectModel().getClosestNetworkNode(x, y));
		if (getProjectModel().getWiringNetworkNode() != null && !getProjectModel().getWiringNetworkNode().isNodeSet()) {
			getProjectModel().getWiringNetworkNode().setNodeSet(true);
			getProjectModel().getWiringNetworkNode().setHighlighted(NetworkNodeHighlight.WIRING);

			getProjectModel().setWiring(true);
			getProjectModel().setWiringStartX(getProjectModel().getWiringNetworkNode().getX());
			getProjectModel().setWiringStartY(getProjectModel().getWiringNetworkNode().getY());
			getProjectModel().setWireX(x);
			getProjectModel().setWireY(y);
		}
	}

	@Override
	public void onMouseDragged(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		if (getProjectModel().isWiring()) {
			// Reset Highlight
			getProjectModel().getNetworkNodes().forEach(networkNode -> {
				switch (networkNode.getHighlight()) {
				case HOVER_VALID_WIRING:
					networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
					break;
				case HOVER_INVALID_WIRING:
					networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
					break;
				default:
					break;
				}
			});

			// Highlight
			NetworkNode<?> closestNetworkNode = getProjectModel().getClosestNetworkNode(x, y);
			if (closestNetworkNode != null && closestNetworkNode != getProjectModel().getWiringNetworkNode()
					&& closestNetworkNode.getHighlight().equals(NetworkNodeHighlight.UNSET)) {
				if (getProjectModel().isValidNodeForWiring(closestNetworkNode)) {
					closestNetworkNode.setHighlighted(NetworkNodeHighlight.HOVER_VALID_WIRING);
				} else {
					closestNetworkNode.setHighlighted(NetworkNodeHighlight.HOVER_INVALID_WIRING);
				}
			}

			getProjectModel().setWireX(x);
			getProjectModel().setWireY(y);
		}
	}

	@Override
	public void onMouseReleased(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		if (getProjectModel().isWiring()) {
			NetworkNode<?> closestNetworkNode = getProjectModel().getClosestNetworkNode(x, y);
			if (closestNetworkNode != null && closestNetworkNode != getProjectModel().getWiringNetworkNode()) {
				if (getProjectModel().isValidNodeForWiring(closestNetworkNode)) {
					closestNetworkNode.setNodeSet(true);
					if (closestNetworkNode.getNodeType() == NetworkNodeType.OUTPUT) {
						closestNetworkNode.setNetworkNode(getProjectModel().getWiringNetworkNode());
					} else {
						getProjectModel().getWiringNetworkNode().setNetworkNode(closestNetworkNode);
					}
					getProjectModel().getWiringNetworkNode().setHighlighted(NetworkNodeHighlight.SET);
					closestNetworkNode.setHighlighted(NetworkNodeHighlight.SET);
				} else {
					getProjectModel().getWiringNetworkNode().setHighlighted(NetworkNodeHighlight.UNSET);
					getProjectModel().getWiringNetworkNode().setNodeSet(false);
				}
			} else {
				getProjectModel().getWiringNetworkNode().setHighlighted(NetworkNodeHighlight.UNSET);
				getProjectModel().getWiringNetworkNode().setNodeSet(false);
			}

			getProjectModel().setWiring(false);
			getProjectModel().setWiringStartX(0);
			getProjectModel().setWiringStartY(0);
			getProjectModel().setWiringNetworkNode(null);
		}
	}
}
