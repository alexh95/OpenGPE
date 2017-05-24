package com.ogpe.blockx.wire;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.DataType;
import com.ogpe.blockx.Point;
import com.ogpe.blockx.Provider;

import javafx.scene.canvas.GraphicsContext;

public class WireNode implements Provider<Object> {

	public final String key;
	
	private Provider<Object> provider;

	public final WireNodeType nodeType;
	public final DataType dataType;

	private Point location;
	private Provider<Block> blockProvider;
	private WireNodeHighlight highlight;

	public WireNode(String key, WireNodeType nodeType, DataType dataType, Point location, Provider<Object> provider) {
		this.key = key;
		this.nodeType = nodeType;
		this.dataType = dataType;
		this.location = location;
		this.provider = provider;
		highlight = WireNodeHighlight.UNSET;
	}

	public WireNode(String key, WireNodeType nodeType, DataType dataType, Point location) {
		this(key, nodeType, dataType, location, () -> null);
	}

	@Override
	public Object provide() {
		return provider.provide();
	}

	public boolean isValidProvider(WireNode that) {
		boolean validNodeType = !nodeType.equals(that.nodeType);
		boolean validDataType = dataType.equals(DataType.ANY) || that.dataType.equals(DataType.ANY)
				|| dataType.equals(that.dataType);
		boolean differetBlock = !getBlock().equals(that.getBlock());
		return validNodeType && validDataType && differetBlock;
	}

	public void setProvider(Provider<Object> provider) {
		this.provider = provider;
	}

	public void unsetProvider() {
		this.provider = null;
	}

	public Point getLocation() {
		return blockProvider.provide().getRectangle().min.add(location);
	}
	
	public Block getBlock() {
		return blockProvider.provide();
	}
	
	public void setBlockProvider(Provider<Block> blockProvider) {
		this.blockProvider = blockProvider;
	}

	public WireNodeHighlight getHighlight() {
		return highlight;
	}

	public void setHighlight(WireNodeHighlight highlight) {
		this.highlight = highlight;
	}

	public void drawWireNode(GraphicsContext context) {
		nodeType.getWireNodeDrawer().drawWireNode(this, context);
	}

}
