package com.ogpe.model;

public abstract class ValueCacher<T> {

	private T cachedValue;
	private boolean cachedValueSet;

	public T getCachedValue() {
		return cachedValue;
	}

	protected void setCachedValue(T value) {
		this.cachedValue = value;
	}

	public boolean isCachedValueSet() {
		return cachedValueSet;
	}

	public void setCachedValueSet(boolean valueSet) {
		this.cachedValueSet = valueSet;
	}
}
