package com.ogpe.blockx.factory;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.Point;

public abstract class BlockFactory {

	public abstract Point getSize();

	public abstract Block makeBlock(Point position);

	public Block makeBlock() {
		return makeBlock(new Point());
	}

}
