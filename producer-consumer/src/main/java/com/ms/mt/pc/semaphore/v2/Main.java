package com.ms.mt.pc.semaphore.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		int numOfProducers = 50;

		DataIngestionMachine<Integer> dim = new DataIngestionMachine<>(numOfProducers);

		Random random = new Random();

		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < numOfProducers; i++) {
			threadList.add(Thread.ofPlatform().unstarted(() -> dim.createProducer(() -> random.nextInt(1000)).produce()));
		}
		for (int i = 0; i < numOfProducers; i++) {
			threadList.add(Thread.ofPlatform().unstarted(() -> dim.createConsumer().consume()));
		}

		for (Thread thread: threadList) {
			thread.setDaemon(true);
			thread.start();
		}
		try {
			Thread.sleep(10_000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
