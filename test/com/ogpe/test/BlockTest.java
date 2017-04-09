package com.ogpe.test;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.model.ConstantBlock;

public class BlockTest {

	@Test
	public void testConstantBlock() {
		Integer constantValue = 5;
		ConstantBlock constantBlock = new ConstantBlock(constantValue);
		Integer returnedValue = constantBlock.getOutputProvider().provide();
		Assert.assertEquals(constantValue, returnedValue);
	}
}
