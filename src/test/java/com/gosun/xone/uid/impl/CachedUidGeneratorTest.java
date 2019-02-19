package com.gosun.xone.uid.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gosun.xone.SpringBootBaseTest;
import com.gosun.xone.uid.impl.CachedUidGenerator;

public class CachedUidGeneratorTest extends SpringBootBaseTest{

	private static final int SIZE = 70000; // 700w
    private static final boolean VERBOSE = true;
    private static final int THREADS = Runtime.getRuntime().availableProcessors() << 1;
	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Test
	public void testGetUID() {
		try {
			CachedUidGenerator  cachedUidGenerator = context.getBean(CachedUidGenerator.class);
			for(int index = 0; index < SIZE; ++index) {
				Long uid = cachedUidGenerator.getUID();
		        String parsedInfo = cachedUidGenerator.parseUID(uid);
		        System.out.println(Thread.currentThread().getName() + " No." + index + " >>> " + parsedInfo);
			}

		}catch(Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
		
	}

}
