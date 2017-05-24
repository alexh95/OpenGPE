package com.ogpe.block;

import com.ogpe.block.factory.BlockFactory;
import com.ogpe.block.factory.PrinterBlockFactory;
import com.ogpe.block.factory.RunningIndexBlockFactory;
import com.ogpe.block.factory.StopBlockFactory;
import com.ogpe.block.factory.bool.BooleanAndBlockFactory;
import com.ogpe.block.factory.bool.BooleanConnectorBlockFactory;
import com.ogpe.block.factory.bool.BooleanNotBlockFactory;
import com.ogpe.block.factory.bool.BooleanOrBlockFactory;
import com.ogpe.block.factory.bool.BooleanSelectorBlockFactory;
import com.ogpe.block.factory.bool.BooleanValueBlockFactory;
import com.ogpe.block.factory.bool.BooleanXorBlockFactory;
import com.ogpe.block.factory.number.NumberAdderBlockFactory;
import com.ogpe.block.factory.number.NumberConnectorBlockFactory;
import com.ogpe.block.factory.number.NumberEqualsBlockFactory;
import com.ogpe.block.factory.number.NumberGreaterThanBlockFactory;
import com.ogpe.block.factory.number.NumberInverterBlockFactory;
import com.ogpe.block.factory.number.NumberLessThanBlockFactory;
import com.ogpe.block.factory.number.NumberMemoryBlockFactory;
import com.ogpe.block.factory.number.NumberMultiplierBlockFactory;
import com.ogpe.block.factory.number.NumberNegatorBlockFactory;
import com.ogpe.block.factory.number.NumberSelectorBlockFactory;
import com.ogpe.block.factory.number.NumberValueBlockFactory;

public enum BlockType {

	// Number Blocks
	NUMBER_VALUE(new NumberValueBlockFactory(), "N (Number Value)"),
	NUMBER_NEGATOR(new NumberNegatorBlockFactory(), "-N (Number Negator)"),
	NUMBER_INVERTER(new NumberInverterBlockFactory(), "1/N (Number Inverter)"),
	NUMBER_ADDER(new NumberAdderBlockFactory(), "A + B (Number Adder)"),
	NUMBER_MULTIPLIER(new NumberMultiplierBlockFactory(), "A X B (Number Multiplier)"),
	NUMBER_EQUALS(new NumberEqualsBlockFactory(), "A = B (Number Equals)"),
	NUMBER_LESS_THAN(new NumberLessThanBlockFactory(), "A < B (Number Less Than)"),
	NUMBER_GREATER_THAN(new NumberGreaterThanBlockFactory(), "A > B (Number Greater Than)"),
	NUMBER_SELECTOR(new NumberSelectorBlockFactory(), "C ? A : B (Number Selector)"),
	NUMBER_MEMORY(new NumberMemoryBlockFactory(), "N Mem (Number Memory)"),
	NUMBER_CONNECTOR(new NumberConnectorBlockFactory(), "N C (Number Connector)"),
	// Boolean Blocks
	BOOLEAN_VALUE(new BooleanValueBlockFactory(), "B (Boolean Value)"),
	BOOLEAN_NOT(new BooleanNotBlockFactory(), "-B (Boolean Negation)"),
	BOOLEAN_AND(new BooleanAndBlockFactory(), "A & B (Boolean And)"),
	BOOLEAN_OR(new BooleanOrBlockFactory(), "A | B (Boolean Or)"),
	BOOLEAN_XOR(new BooleanXorBlockFactory(), "A ^ B (Boolean Xor)"),
	BOOLEAN_SELECTOR(new BooleanSelectorBlockFactory(), "C ? A : B (Boolean Selector)"),
	BOOLEAN_CONNECTOR(new BooleanConnectorBlockFactory(), "B C (Boolean Connector)"),
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
		return blockFactory.makeBlock(this, position);
	}
	
	public Block makeBlock() {
		return blockFactory.makeBlock(this);
	}
	
	@Override
	public String toString() {
		return displayName;
	}
	
}
