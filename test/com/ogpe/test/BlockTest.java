package com.ogpe.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.ogpe.block.Block;
import com.ogpe.block.BlockType;
import com.ogpe.block.RunningContext;
import com.ogpe.block.factory.PrinterBlockFactory;
import com.ogpe.block.factory.number.NumberAdderBlockFactory;
import com.ogpe.block.factory.number.NumberValueBlockFactory;
import com.ogpe.observable.Observable;

public class BlockTest {

	@Test
	public void numberValueBlock() {
		BigDecimal value = BigDecimal.valueOf(11);
		Block valueBlock = BlockType.NUMBER_VALUE.makeBlock();
		valueBlock.getWireNodes().get(NumberValueBlockFactory.OUTPUT_KEY).setProvider(() -> value);

		BigDecimal result = (BigDecimal) valueBlock.getWireNodes().get("output").provide();
		assertEquals(value, result);
	}

	@Test
	public void numberAdderBlock() {
		BigDecimal value1 = BigDecimal.valueOf(11);
		Block valueBlock1 = BlockType.NUMBER_VALUE.makeBlock();
		valueBlock1.getWireNodes().get(NumberValueBlockFactory.OUTPUT_KEY).setProvider(() -> value1);

		BigDecimal value2 = BigDecimal.valueOf(11.5);
		Block valueBlock2 = BlockType.NUMBER_VALUE.makeBlock();
		valueBlock2.getWireNodes().get(NumberValueBlockFactory.OUTPUT_KEY).setProvider(() -> value2);

		Block adderBlock = BlockType.NUMBER_ADDER.makeBlock();
		adderBlock.getWireNodes().get(NumberAdderBlockFactory.INPUT_KEY_1)
				.setProvider(valueBlock1.getWireNodes().get(NumberValueBlockFactory.OUTPUT_KEY));
		adderBlock.getWireNodes().get(NumberAdderBlockFactory.INPUT_KEY_2)
				.setProvider(valueBlock2.getWireNodes().get(NumberValueBlockFactory.OUTPUT_KEY));

		BigDecimal expectedValue = value1.add(value2);
		BigDecimal result = (BigDecimal) adderBlock.getWireNodes().get(NumberAdderBlockFactory.OUTPUT_KEY).provide();
		assertEquals(expectedValue, result);
	}

	@Test
	public void numberPrinterBlock() {
		BigDecimal value = BigDecimal.valueOf(11);
		Block valueBlock = BlockType.NUMBER_VALUE.makeBlock();
		valueBlock.getWireNodes().get(NumberValueBlockFactory.OUTPUT_KEY).setProvider(() -> value);

		Block printerBlock = BlockType.PRINTER.makeBlock();
		printerBlock.getWireNodes().get(PrinterBlockFactory.INPUT_KEY)
				.setProvider(valueBlock.getWireNodes().get(NumberValueBlockFactory.OUTPUT_KEY));
		
		RunningContext context = new RunningContext(new Observable<String>(), 1);
		printerBlock.runBlock(context);
	}

}
