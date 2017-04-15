package com.ogpe.test;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.behaviour.OutputPort;
import com.ogpe.block.behaviour.Provider;

public class OutputPortTest {

	private String providingValue;
	
	@Test
	public void testInputPort() throws NoSuchFieldException, IllegalAccessException {
		providingValue = "test";
		Provider<String> valueProvider = () -> providingValue;
		OutputPort<String> outputPort = new OutputPort<>(valueProvider);

		String requestedValue = outputPort.getOutputProvider().provide();
		Assert.assertEquals(providingValue, requestedValue);
		
		String oldProvidingValue = providingValue;
		providingValue = "new test";
		requestedValue = outputPort.getOutputProvider().provide();
		Assert.assertEquals(oldProvidingValue, requestedValue);
		
		outputPort.setCachedValueSet(false);

		requestedValue = outputPort.getOutputProvider().provide();
		Assert.assertEquals(providingValue, requestedValue);
	}
}
