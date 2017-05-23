package com.ogpe.blockx;

import com.ogpe.blockx.factory.BlockFactory;
import com.ogpe.blockx.factory.PrinterBlockFactory;
import com.ogpe.blockx.factory.RunningIndexBlockFactory;
import com.ogpe.blockx.factory.StopBlockFactory;
import com.ogpe.blockx.factory.bool.BooleanSelectorBlockFactory;
import com.ogpe.blockx.factory.bool.BooleanValueBlockFactory;
import com.ogpe.blockx.factory.number.NumberAdderBlockFactory;
import com.ogpe.blockx.factory.number.NumberEqualsBlockFactory;
import com.ogpe.blockx.factory.number.NumberGreaterThanBlockFactory;
import com.ogpe.blockx.factory.number.NumberInverterBlockFactory;
import com.ogpe.blockx.factory.number.NumberLessThanBlockFactory;
import com.ogpe.blockx.factory.number.NumberMultiplierBlockFactory;
import com.ogpe.blockx.factory.number.NumberNegatorBlockFactory;
import com.ogpe.blockx.factory.number.NumberSelectorBlockFactory;
import com.ogpe.blockx.factory.number.NumberValueBlockFactory;

public enum BlockType {

	// Number Blocks
	NUMBER_VALUE(new NumberValueBlockFactory(), "Number Value"),
	NUMBER_NEGATOR(new NumberNegatorBlockFactory(), "Number Negator"),
	NUMBER_INVERTER(new NumberInverterBlockFactory(), "Number Inverter"),
	NUMBER_ADDER(new NumberAdderBlockFactory(), "Number Adder"),
	NUMBER_MULTIPLIER(new NumberMultiplierBlockFactory(), "Number Multiplier"),
	NUMBER_SELECTOR(new NumberSelectorBlockFactory(), "Number Selector"),
	NUMBER_EQUALS(new NumberEqualsBlockFactory(), "Number Equals"),
	NUMBER_LESS_THAN(new NumberLessThanBlockFactory(), "Number Less Than"),
	NUMBER_GREATER_THAN(new NumberGreaterThanBlockFactory(), "Number Greater Than"),
	// Boolean Blocks
	BOOLEAN_VALUE(new BooleanValueBlockFactory(), "Boolean Value"),
	//BOOLEAN_NOT,
	//BOOLEAN_AND,
	//BOOLEAN_OR,
	//BOOLEAN_XOR,
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
