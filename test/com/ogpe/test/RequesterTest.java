package com.ogpe.test;

import org.junit.Assert;
import org.junit.Test;

import com.ogpe.requester.Requester;

public class RequesterTest {

	@Test
	public void testRequester() {
		String requestingValue = "test";
		Requester<String> Requester = () -> requestingValue;
		String requestedValue = Requester.request();
		Assert.assertEquals(requestingValue, requestedValue);
	}
}
