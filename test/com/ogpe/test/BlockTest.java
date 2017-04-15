package com.ogpe.test;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.behaviour.implementation.ConstantBlockBehavior;
import com.ogpe.block.model.implementation.ConstantBlockModel;

public class BlockTest {

	@Test
	public void testConstantBlock() {
		Integer constantValue = 5;
		ConstantBlockModel constantBlockModel = new ConstantBlockModel(constantValue);
		ConstantBlockBehavior constantBlock = new ConstantBlockBehavior(constantBlockModel);
		Integer returnedValue = constantBlock.getOutputProvider().provide();
		Assert.assertEquals(constantValue, returnedValue);
	}
}
