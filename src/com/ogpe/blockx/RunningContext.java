package com.ogpe.blockx;

import com.ogpe.observable.Observable;

public class RunningContext {
	
	public final Observable<String> console;
	public final int runningIndex;
	
	public RunningContext(Observable<String> console, int runningIndex) {
		this.console = console;
		this.runningIndex = runningIndex;
	}

}
