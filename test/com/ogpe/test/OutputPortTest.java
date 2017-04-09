package com.ogpe.test;
import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.model.OutputPort;
import com.ogpe.model.Provider;

public class OutputPortTest {
	
	@Test
	public void testInputPort() throws NoSuchFieldException, IllegalAccessException {
		String providingValue = "test";
		Provider<String> valueProvider = () -> providingValue;
		OutputPort<String> outputPort = new OutputPort<>(valueProvider);

		{
			Field outputValueField = outputPort.getClass().getDeclaredField("outputValue");
			outputValueField.setAccessible(true);
			String outputValue = (String) outputValueField.get(outputPort);
			Assert.assertEquals(null, outputValue);

			Field outputValueSetField = outputPort.getClass().getDeclaredField("outputValueSet");
			outputValueSetField.setAccessible(true);
			Boolean outputValueSet = outputValueSetField.getBoolean(outputPort);
			Assert.assertFalse(outputValueSet);
		}

		String requestedValue = outputPort.getOutputProvider().provide();
		Assert.assertEquals(providingValue, requestedValue);

		{
			Field outputValueField = outputPort.getClass().getDeclaredField("outputValue");
			outputValueField.setAccessible(true);
			String outputValue = (String) outputValueField.get(outputPort);
			Assert.assertEquals(providingValue, outputValue);

			Field outputValueSetField = outputPort.getClass().getDeclaredField("outputValueSet");
			outputValueSetField.setAccessible(true);
			Boolean outputValueSet = outputValueSetField.getBoolean(outputPort);
			Assert.assertTrue(outputValueSet);
		}
	}
}
