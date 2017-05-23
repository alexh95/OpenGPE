package com.ogpe.blockx;

@FunctionalInterface
public interface BlockRunner {
	
	void runBlock(Block block, RunningContext context);
	
}
