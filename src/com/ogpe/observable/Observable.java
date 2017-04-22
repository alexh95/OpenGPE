package com.ogpe.observable;

import java.util.ArrayList;
import java.util.Collection;

public class Observable<T> {

	private Collection<Observer<T>> observers;

	public Observable() {
		observers = new ArrayList<>();
	}

	public void addObserver(Observer<T> observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer<T> observer) {
		observers.remove(observer);
	}

	public void updateObservers(T value) {
		observers.forEach(observer -> observer.update(value));
	}
}
