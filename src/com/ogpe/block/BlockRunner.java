package com.ogpe.block;

@FunctionalInterface
public interface BlockRunner {
	
	void runBlock(Block block, RunningContext context);
	
}
