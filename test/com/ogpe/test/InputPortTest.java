package com.ogpe.test;
import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.model.InputPort;
import com.ogpe.model.Provider;

public class InputPortTest {

	@Test
	public void testInputPort() throws NoSuchFieldException, IllegalAccessException {
		String providingValue = "test";
		Provider<String> inputProvider = () -> providingValue;
		InputPort<String> inputPort = new InputPort<>(inputProvider);

		{
			Field inputValueField = inputPort.getClass().getDeclaredField("inputValue");
			inputValueField.setAccessible(true);
			String inputValue = (String) inputValueField.get(inputPort);
			Assert.assertEquals(null, inputValue);

			Field inputValueSetField = inputPort.getClass().getDeclaredField("inputValueSet");
			inputValueSetField.setAccessible(true);
			Boolean inputValueSet = inputValueSetField.getBoolean(inputPort);
			Assert.assertFalse(inputValueSet);
		}

		String requestedValue = inputPort.request();
		Assert.assertEquals(providingValue, requestedValue);

		{
			Field inputValueField = inputPort.getClass().getDeclaredField("inputValue");
			inputValueField.setAccessible(true);
			String inputValue = (String) inputValueField.get(inputPort);
			Assert.assertEquals(providingValue, inputValue);

			Field inputValueSetField = inputPort.getClass().getDeclaredField("inputValueSet");
			inputValueSetField.setAccessible(true);
			Boolean inputValueSet = inputValueSetField.getBoolean(inputPort);
			Assert.assertTrue(inputValueSet);
		}
	}
}
