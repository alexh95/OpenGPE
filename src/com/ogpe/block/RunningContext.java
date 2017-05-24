package com.ogpe.block;

import com.ogpe.observable.Observable;

public class RunningContext {
	
	public final Observable<String> console;
	public final int runningIndex;
	private boolean stopped;
	
	public RunningContext(Observable<String> console, int runningIndex) {
		this.console = console;
		this.runningIndex = runningIndex;
		stopped = false;
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public void stop() {
		stopped = true;
	}

}
