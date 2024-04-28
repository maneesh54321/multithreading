package com.ms.mt;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class ThreadPinningTest {

	public static final Logger log = LoggerFactory.getLogger(ThreadPinningTest.class);

	public static final Random RANDOM = new Random();

	@Test
	public void testConcurrentHashMap() throws InterruptedException {
		var map = new ConcurrentHashMap<String, Integer>();

		var cdl = new CountDownLatch(2);

		Thread.ofVirtual().name("vt-1").start(() -> {
			String key = "Ram";
			log.info("{} | Inserting {} in concurrentHashMap", Thread.currentThread(), key);
			map.computeIfAbsent(key, k -> {
				log.info("{} | calculating value corresponding to {}", Thread.currentThread(), k);
				return RANDOM.nextInt(100);
			});
//			TestUtils.sleep(1000);
			log.info("{} | Inserted {} in concurrentHashMap", Thread.currentThread(), key);
			cdl.countDown();
		});

		Thread.ofVirtual().name("vt-2").start(() -> {
			String key = "Mohan";
			log.info("{} | Inserting {} in concurrentHashMap", Thread.currentThread(), key);
			map.computeIfAbsent(key, k -> {
				log.info("{} | calculating value corresponding to {}", Thread.currentThread(), k);
				return RANDOM.nextInt(100);
			});
//			TestUtils.sleep(1000);
			log.info("{} | Inserted {} in concurrentHashMap", Thread.currentThread(), key);
			cdl.countDown();
		});

		cdl.await();
	}
}
