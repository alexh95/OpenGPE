package com.ogpe.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.behaviour.implementation.ConstantNumberBlockBehavior;
import com.ogpe.block.model.implementation.ConstantNumberBlockModel;

public class BlockTest {

	@Test
	public void testConstantBlock() {
		BigDecimal constantValue = BigDecimal.valueOf(5);
		ConstantNumberBlockModel constantBlockModel = new ConstantNumberBlockModel(constantValue);
		ConstantBlockBehavior<BigDecimal> constantBlock = new ConstantNumberBlockBehavior();
		constantBlock.setBlockModelRequester(() -> constantBlockModel);
		BigDecimal returnedValue = constantBlock.getOutputRequester().request();
		Assert.assertEquals(constantValue, returnedValue);
	}
}
