package com.ms.mt;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static com.ms.mt.TestUtils.log;
import static com.ms.mt.TestUtils.sleep;


public class VirtualThreadsTest {

	@Test
	void logsFromWithinSynchronizedBlock() throws InterruptedException {
		var cdl = new CountDownLatch(2);
		int iterations = 3;
		var obj = new Object(); // something to lock

		Thread.ofVirtual().name("vt1").start(() -> {
			for (int i = 0; i < iterations; i++) {
				synchronized (obj) {
					log("Iteration " + i);
				}
				sleep(100);
			}
			cdl.countDown();
		});

		Thread.ofVirtual().name("vt2").start(() -> {
			for (int i = 0; i < iterations; i++) {
				synchronized (obj) {
					log("Iteration " + i);
				}
				sleep(70);
			}
			cdl.countDown();
		});

		cdl.await();
	}

	@Test
	public void testMorningRoutine() throws InterruptedException {
		var cdl = new CountDownLatch(2);
		var bathTime = Thread.ofPlatform().name("Bath").start(() -> {
			log("I am going to take a bath.");
			sleep(500);
			log("I am done with bath");
			cdl.countDown();
		});

		var boilingWater = Thread.ofPlatform().name("Boil").start(() -> {
			log("I am going to boil some water");
			sleep(500);
			log("I am done boiling water.");
			cdl.countDown();
		});

		cdl.await();
	}

	@Test
	public void testMorningRoutineWithVirtual() throws InterruptedException {
		var cdl = new CountDownLatch(2);
		var bathTime = Thread.ofVirtual().name("Bath").start(() -> {
			log("I am going to take a bath.");
			sleep(500);
			log("I am done with bath");
			cdl.countDown();
		});

		var boilingWater = Thread.ofVirtual().name("Boil").start(() -> {
			log("I am going to boil some water");
			sleep(500);
			log("I am done boiling water.");
			cdl.countDown();
		});
		cdl.await();
	}


	@RepeatedTest(10)
	public void testMorningRoutineWithExecutorServiceVirtual() throws InterruptedException {
		var cdl = new CountDownLatch(2);
		var threadFactory = Thread.ofVirtual().name("routine-", 0).factory();
		try (var executorService = Executors.newThreadPerTaskExecutor(threadFactory)) {
			executorService.submit(() -> {
				log("I am going to take a bath.");
				sleep(500);
				log("I am done with bath");
				cdl.countDown();
			});

			executorService.submit(() -> {
				log("I am going to boil some water");
				sleep(500);
				log("I am done boiling water.");
				cdl.countDown();
			});
			cdl.await();
		}
	}
}
