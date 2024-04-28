package com.ms.mt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestUtils {
	private static final Logger log = LoggerFactory.getLogger(TestUtils.class);

	public static void sleep(long i) {
		try {
			TimeUnit.MILLISECONDS.sleep(i);
		} catch (InterruptedException ignored) {
		}
	}

	public static void log(String s) {
		log.info(s);
	}
}
