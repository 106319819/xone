package com.gosun.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class DESUtilTest {
	String key = "allinpay;yn";

	@Test
	public void testEncrypt() {
		try {
			String content = "{\"accountId\":382832601418695169,\"accountCode\":\"webmaster\",\"fullName\":\"webmaster\",\"personId\":\"382832601418695170\"}";
			String encode = DESUtil.encrypt(content, key);
			System.out.println(encode);

			String decode = DESUtil.decrypt(encode, key);
			System.out.println(decode);
		} catch (RuntimeException e) {
			// TODO: handle exception
			fail("Not yet implemented");
		}

	}

	@Test
	public void testDecrypt() {
		try {
			String data = "BZTScq7uEYkVX1rgUOZJdLFq00oZIXl1i1at5c4ruX83hBV7ue4rp3rvjvA0207SldTLO/Lr6v0VPvebSmLjUthj0r5UE0elQ5Z49HUkAQ3OgGPIYySFaDhIbd5GQl5qsWrTShkheXWNFCJgR9DfcoLLKijUk30E";
			String decode = DESUtil.decrypt(data, key);
			System.out.println(decode);
		} catch (RuntimeException e) {
			// TODO: handle exception
			fail("Not yet implemented");
		}
	}

}
