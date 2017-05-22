package com.ogpe.blockx;

import com.ogpe.observable.Observable;

@FunctionalInterface
public interface BlockRunner {
	
	void runBlock(Block block, Observable<String> console);
	
}
