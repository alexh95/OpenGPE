package com.ogpe.blockx;

import com.ogpe.blockx.factory.BlockFactory;
import com.ogpe.blockx.factory.NumberAdderBlockFactory;
import com.ogpe.blockx.factory.NumberValueBlockFactory;
import com.ogpe.blockx.factory.PrinterBlockFactory;
import com.ogpe.blockx.factory.RunningIndexBlockFactory;
import com.ogpe.blockx.factory.StopBlockFactory;

public enum BlockType {

	NUMBER_VALUE(new NumberValueBlockFactory(), "Number Value"),
	NUMBER_ADDER(new NumberAdderBlockFactory(), "Number Adder"),
	PRINTER(new PrinterBlockFactory(), "Printer"),
	RUNNING_INDEX(new RunningIndexBlockFactory(), "Running Index"),
	STOP(new StopBlockFactory(), "Stop");
	
	
	private final BlockFactory blockFactory;
	private final String displayName;

	private BlockType(BlockFactory blockFactory, String displayName) {
		this.blockFactory = blockFactory;
		this.displayName = displayName;
	}
	
	public Point getSize() {
		return blockFactory.size;
	}
	
	public Block makeBlock(Point position) {
		return blockFactory.makeBlock(position);
	}
	
	public Block makeBlock() {
		return makeBlock(new Point());
	}
	
	@Override
	public String toString() {
		return displayName;
	}
	
}
