package com.ogpe.blockx.factory;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.Point;

public abstract class BlockFactory {

	public final Point size;
	
	protected BlockFactory(Point size) {
		this.size = size;
	}
	
	public abstract Block makeBlock(Point position);
	
	public Block makeBlock() {
		return makeBlock(new Point());
	}
	
}
