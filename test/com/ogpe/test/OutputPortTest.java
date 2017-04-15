package com.ogpe.test;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.block.behaviour.OutputPort;
import com.ogpe.requester.Requester;

public class OutputPortTest {

	private String providingValue;

	@Test
	public void testInputPort() throws NoSuchFieldException, IllegalAccessException {
		providingValue = "test";
		Requester<String> valueRequester = () -> providingValue;
		OutputPort<String> outputPort = new OutputPort<>(valueRequester);

		String requestedValue = outputPort.getOutputRequester().request();
		Assert.assertEquals(providingValue, requestedValue);

		String oldProvidingValue = providingValue;
		providingValue = "new test";
		requestedValue = outputPort.getOutputRequester().request();
		Assert.assertEquals(oldProvidingValue, requestedValue);

		outputPort.setCachedValueSet(false);

		requestedValue = outputPort.getOutputRequester().request();
		Assert.assertEquals(providingValue, requestedValue);
	}
}
