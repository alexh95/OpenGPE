package com.ogpe.blockx.wire;

import com.ogpe.blockx.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum WireNodeType {

	INPUT(WireNodeType::drawInputWireNode), OUTPUT(WireNodeType::drawOutputWireNode), THROUGHPUT(
			WireNodeType::drawThroughputWireNode);

	private final WireNodeDrawer wireNodeDrawer;

	private WireNodeType(WireNodeDrawer wireNodeDrawer) {
		this.wireNodeDrawer = wireNodeDrawer;
	}

	private static void drawInputWireNode(WireNode wireNode, GraphicsContext context) {
		Point location = wireNode.getLocation();

		context.setFill(wireNode.getHighlight().getColor());
		double nodeX1 = location.x - 4.5;
		double nodeY1 = location.y - 2.5;
		double nodeX2 = location.x + 4.5;
		double nodeY2 = location.y - 2.5;
		double nodeX3 = location.x;
		double nodeY3 = location.y + 2.5;
		context.fillPolygon(new double[] { nodeX1, nodeX2, nodeX3 }, new double[] { nodeY1, nodeY2, nodeY3 }, 3);

		if (wireNode.getBlock().isSelected()) {
			context.setStroke(Color.RED);
		} else {
			context.setStroke(Color.BLACK);
		}
		double portX1 = location.x - 5;
		double portY1 = location.y - 3;
		double portX2 = location.x;
		double portY2 = location.y + 3;
		double portX3 = location.x + 5;
		double portY3 = location.y - 3;
		context.strokePolyline(new double[] { portX1, portX2, portX3 }, new double[] { portY1, portY2, portY3 }, 3);

		// context.setStroke(Color.RED);
		// context.strokeOval(location.x - 16, location.y - 16, 32, 32);
	}

	private static void drawOutputWireNode(WireNode wireNode, GraphicsContext context) {
		Point location = wireNode.getLocation();

		context.setFill(wireNode.getHighlight().getColor());
		double nodeX = location.x - 4.5;
		double nodeY = location.y - 2.5;
		double nodeW = 9;
		double nodeH = 5;
		context.fillRect(nodeX, nodeY, nodeW, nodeH);

		if (wireNode.getBlock().isSelected()) {
			context.setStroke(Color.RED);
		} else {
			context.setStroke(Color.BLACK);
		}
		double portX = location.x - 5;
		double portY = location.y - 3;
		double portW = 10;
		double portH = 6;
		context.strokeRect(portX, portY, portW, portH);

		// context.setStroke(Color.RED);
		// context.strokeOval(location.x - 16, location.y - 16, 32, 32);
	}

	private static void drawThroughputWireNode(WireNode wireNode, GraphicsContext context) {
		context.setFill(wireNode.getHighlight().getColor());
		double nodeX = wireNode.getLocation().x - 2;
		double nodeY = wireNode.getLocation().y - 2;
		double nodeW = 2;
		double nodeH = 2;
		context.fillOval(nodeX, nodeY, nodeW, nodeH);

		// context.setStroke(Color.RED);
		// context.strokeOval(location.x - 16, location.y - 16, 32, 32);
	}

	public WireNodeDrawer getWireNodeDrawer() {
		return wireNodeDrawer;
	}

}
