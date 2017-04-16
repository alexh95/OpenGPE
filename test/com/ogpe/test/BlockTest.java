package com.ogpe.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.implementation.ConstantBooleanBlock;
import com.ogpe.block.implementation.ConstantNumberBlock;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.model.implementation.ConstantBooleanBlockModel;
import com.ogpe.block.model.implementation.ConstantNumberBlockModel;
import com.ogpe.block.model.implementation.ConstantStringBlockModel;

public class BlockTest {

	@Test
	public void testConstantNumberBlock() {
		BigDecimal constantNumberValue = BigDecimal.valueOf(5);
		ConstantNumberBlockModel constantNumberBlockModel = new ConstantNumberBlockModel(constantNumberValue);
		ConstantNumberBlock constantNumberBlock = new ConstantNumberBlock(constantNumberBlockModel, 0, 0);
		BigDecimal returnedValue = constantNumberBlock.getBlockBehavior().getOutputRequester().request();
		Assert.assertEquals(constantNumberValue, returnedValue);
	}

	@Test
	public void testConstantBooleanBlock() {
		Boolean constantBooleanValue = true;
		ConstantBooleanBlockModel constantBooleanBlockModel = new ConstantBooleanBlockModel(constantBooleanValue);
		ConstantBooleanBlock constantBooleanBlock = new ConstantBooleanBlock(constantBooleanBlockModel, 0, 0);
		Boolean returnedValue = constantBooleanBlock.getBlockBehavior().getOutputRequester().request();
		Assert.assertEquals(constantBooleanValue, returnedValue);
	}

	@Test
	public void testConstantStringBlock() {
		String constantStringValue = "test string";
		ConstantStringBlockModel constantStringBlockModel = new ConstantStringBlockModel(constantStringValue);
		ConstantStringBlock constantStringBlock = new ConstantStringBlock(constantStringBlockModel, 0, 0);
		String returnedValue = constantStringBlock.getBlockBehavior().getOutputRequester().request();
		Assert.assertEquals(constantStringValue, returnedValue);
	}
}
