package com.ogpe.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ogpe.block.behaviour.BlockBehavior;
import com.ogpe.block.implementation.ConstantStringBlock;
import com.ogpe.block.implementation.PrintBlock;
import com.ogpe.block.wire.Wire;
import com.ogpe.block.wire.WireNode;
import com.ogpe.block.wire.WireNodeTarget;

public class WireTest {

	@Test
	public void testConstantStringPrint() {
		ConstantStringBlock constantStringBlock = new ConstantStringBlock(0, 0);
		WireNodeTarget<String> constantStringBlockWireNodeTarget = constantStringBlock.getBlockModel().getConstantValueNodeTarget();
		PrintBlock printBlock = new PrintBlock(0, 0);
		WireNodeTarget<? extends Object> printBlockWireNodeTarget = printBlock.getBlockModel().getPrintValueWireNodeTarget();
		Wire<String> wire = new Wire<>();
		
		wire.getWireModel().addWireNode(constantStringBlockWireNodeTarget.makeWireNode());
		wire.getWireModel().addWireNode((WireNode<String>) printBlockWireNodeTarget.makeWireNode());
		
		List<BlockBehavior<?>> blockBehaviors = new ArrayList<>();
		blockBehaviors.add(constantStringBlock.makeBlockBehavior());
		blockBehaviors.add(printBlock.makeBlockBehavior());
		blockBehaviors.forEach(blockBehavior -> blockBehavior.run());
	}
}
