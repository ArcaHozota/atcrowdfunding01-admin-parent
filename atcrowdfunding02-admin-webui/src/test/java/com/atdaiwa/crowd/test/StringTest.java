package com.atdaiwa.crowd.test;

import org.junit.Test;

import com.atdaiwa.crowd.util.CrowdUtil;

public class StringTest {

	@Test
	public void testMD5() {
		String source = "123123";
		String encoded = CrowdUtil.toMD5(source);
		System.out.println(encoded);
	}
}
