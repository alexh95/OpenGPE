package com.ogpe.test;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.model.InputPort;
import com.ogpe.model.Provider;

public class InputPortTest {

	private String providingValue;

	@Test
	public void testInputPort() throws NoSuchFieldException, IllegalAccessException {
		providingValue = "test";
		Provider<String> inputProvider = () -> providingValue;
		InputPort<String> inputPort = new InputPort<>(inputProvider);

		String requestedValue = inputPort.request();
		Assert.assertEquals(providingValue, requestedValue);

		String oldProvidingValue = providingValue;
		providingValue = "new test";
		requestedValue = inputPort.request();
		Assert.assertEquals(oldProvidingValue, requestedValue);

		inputPort.setCachedValueSet(false);

		requestedValue = inputPort.request();
		Assert.assertEquals(providingValue, requestedValue);
	}
}
