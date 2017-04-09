package com.ogpe.test;
import org.junit.Assert;
import org.junit.Test;

import com.ogpe.model.Provider;

public class ProviderTest {

	@Test
	public void testProvider() {
		String providingValue = "test";
		Provider<String> provider = () -> providingValue;
		String providedValue = provider.provide();
		Assert.assertEquals(providingValue, providedValue);
	}
}
