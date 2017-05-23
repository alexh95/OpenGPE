package com.ogpe.blockx;

import com.ogpe.blockx.factory.BlockFactory;
import com.ogpe.blockx.factory.BooleanSelectorBlockFactory;
import com.ogpe.blockx.factory.BooleanValueBlockFactory;
import com.ogpe.blockx.factory.NumberAdderBlockFactory;
import com.ogpe.blockx.factory.NumberValueBlockFactory;
import com.ogpe.blockx.factory.PrinterBlockFactory;
import com.ogpe.blockx.factory.RunningIndexBlockFactory;
import com.ogpe.blockx.factory.StopBlockFactory;

public enum BlockType {

	// Number Blocks
	NUMBER_VALUE(new NumberValueBlockFactory(), "Number Value"),
	//NUMBER_OPPOSITE
	//NUMBER_INVERTER
	NUMBER_ADDER(new NumberAdderBlockFactory(), "Number Adder"),
	//NUMBER_MULTIPLIER
	//NUMBER_SELECTOR
	// Boolean Blocks
	BOOLEAN_VALUE(new BooleanValueBlockFactory(), "Boolean Value"),
	//BOOLEAN_NOT
	//BOOLEAN_AND
	//BOOLEAN_OR
	//BOOLEAN_XOR
	BOOLEAN_SELECTOR(new BooleanSelectorBlockFactory(), "Boolean Selector"),
	// Effect Blocks
	PRINTER(new PrinterBlockFactory(), "Printer"),
	// Loop Blocks
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
		return blockFactory.makeBlock();
	}
	
	@Override
	public String toString() {
		return displayName;
	}
	
}
