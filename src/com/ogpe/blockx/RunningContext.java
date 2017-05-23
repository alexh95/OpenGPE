package com.ogpe.blockx;

import com.ogpe.observable.Observable;

public class RunningContext {
	
	public final Observable<String> console;
	private boolean stopped;
	
	public RunningContext(Observable<String> console) {
		this.console = console;
		stopped = false;
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public void stop() {
		stopped = true;
	}

}
