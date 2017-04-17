package com.ogpe.observable;

import java.util.ArrayList;
import java.util.Collection;

public class Observable {

	private Collection<Observer> observers;

	public Observable() {
		observers = new ArrayList<>();
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	public void updateObservers() {
		observers.forEach(observer -> observer.update());
	}
}
