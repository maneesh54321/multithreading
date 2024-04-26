package com.ms.mt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	private static final int HIGHEST_PRICE = 2000;

	private static final Random random = new Random();

	public static void main(String[] args) {
		InventoryDatabase inventoryDatabase = new InventoryDatabase();

		for (int i = 0; i < 100000; i++) {
			inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
		}

		Thread writer = new Thread(() -> {
			while (true) {
				inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
				inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		});
		writer.setDaemon(true);
		writer.start();

		int numOfReaderThreads = 10;
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < numOfReaderThreads; i++) {
			Thread reader = new Thread(() -> {
				for (int j = 0; j < 100000; j++) {
					int upperBound = random.nextInt(HIGHEST_PRICE);
					int lowerBound = upperBound > 0 ? random.nextInt(upperBound) : 0;
					inventoryDatabase.getNumberOfItemsInPriceRange(lowerBound, upperBound);
				}
			});
			reader.setDaemon(true);
			threadList.add(reader);
		}

		long startTime = System.currentTimeMillis();
		threadList.forEach(Thread::start);
		threadList.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		});
		long endTime = System.currentTimeMillis();

		System.out.printf("Time taken: %d\n", (endTime - startTime));
	}
}