package com.ogpe.blockx.wire;

import com.ogpe.blockx.DataType;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Provider;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WireNode implements Provider<Object> {

	private Provider<Object> provider;

	public final WireNodeType nodeType;
	public final DataType dataType;

	private boolean set;

	private Point location;
	private Provider<Point> offsetProvider;
	private WireNodeHighlight highlight;

	public WireNode(WireNodeType nodeType, DataType dataType, Point location, Provider<Object> provider) {
		this.nodeType = nodeType;
		this.dataType = dataType;
		set = false;
		this.location = location;
		this.provider = provider;
		highlight = WireNodeHighlight.UNSET;
	}

	public WireNode(WireNodeType nodeType, DataType dataType, Point location) {
		this(nodeType, dataType, location, null);
	}

	@Override
	public Object provide() {
		return provider.provide();
	}

	public boolean isValidProvider(WireNode that) {
		boolean validNodeType = !nodeType.equals(that.nodeType);
		boolean validDataType = dataType.equals(DataType.ANY) || that.dataType.equals(DataType.ANY)
				|| dataType.equals(that.dataType);
		return validNodeType && validDataType;
	}

	public void setProvider(Provider<Object> provider) {
		this.provider = provider;
	}

	public void unsetProvider() {
		this.provider = null;
	}

	public void set() {
		this.set = true;
	}

	public void unset() {
		this.set = false;
	}

	public boolean isSet() {
		return set;
	}

	public Point getLocation() {
		return offsetProvider.provide().add(location);
	}
	
	public void setOffsetProvider(Provider<Point> locationProvider) {
		this.offsetProvider = locationProvider;
	}

	public WireNodeHighlight getHighlight() {
		return highlight;
	}

	public void setHighlight(WireNodeHighlight highlight) {
		this.highlight = highlight;
	}

	public void drawWireNode(GraphicsContext context) {
		nodeType.getWireNodeDrawer().drawWireNode(this, context);
		if (provider != null && nodeType == WireNodeType.INPUT || nodeType == WireNodeType.THROUGHPUT) {
			WireNode that = (WireNode) provider;
			Point p1 = getLocation();
			Point p2 = that.getLocation();
			double x1 = p1.x;
			double y1 = p1.y;
			double x2 = p2.x;
			double y2 = p2.y;
			context.setStroke(Color.BLACK);
			context.strokeLine(x1, y1, x2, y2);
		}
	}

}
