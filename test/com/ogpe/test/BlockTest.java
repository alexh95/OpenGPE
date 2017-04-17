package com.ogpe.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.implementation.ConstantBooleanBlock;
import com.ogpe.block.implementation.ConstantNumberBlock;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.model.implementation.ConstantBlockModel;

public class BlockTest {

	@Test
	public void testConstantNumberBlock() {
		BigDecimal constantNumberValue = BigDecimal.valueOf(5);
		ConstantBlockModel<BigDecimal> constantNumberBlockModel = new ConstantBlockModel<BigDecimal>(
				constantNumberValue);
		ConstantNumberBlock constantNumberBlock = new ConstantNumberBlock(constantNumberBlockModel, 0, 0);
		BigDecimal returnedValue = constantNumberBlock.makeBlockBehavior().getOutputRequester().request();
		Assert.assertEquals(constantNumberValue, returnedValue);
	}

	@Test
	public void testConstantBooleanBlock() {
		Boolean constantBooleanValue = true;
		ConstantBlockModel<Boolean> constantBooleanBlockModel = new ConstantBlockModel<Boolean>(constantBooleanValue);
		ConstantBooleanBlock constantBooleanBlock = new ConstantBooleanBlock(constantBooleanBlockModel, 0, 0);
		Boolean returnedValue = constantBooleanBlock.makeBlockBehavior().getOutputRequester().request();
		Assert.assertEquals(constantBooleanValue, returnedValue);
	}

	@Test
	public void testConstantStringBlock() {
		String constantStringValue = "test string";
		ConstantBlockModel<String> constantStringBlockModel = new ConstantBlockModel<String>(constantStringValue);
		ConstantStringBlock constantStringBlock = new ConstantStringBlock(constantStringBlockModel, 0, 0);
		String returnedValue = constantStringBlock.makeBlockBehavior().getOutputRequester().request();
		Assert.assertEquals(constantStringValue, returnedValue);
	}
}
