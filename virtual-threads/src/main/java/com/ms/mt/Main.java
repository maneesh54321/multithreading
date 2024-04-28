package com.ms.mt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
	public static void main(String[] args) {
		int numOfTasks = 10_000;
		List<Future<?>> futureList = new ArrayList<>();
		try (ExecutorService executorService = Executors.newCachedThreadPool()) {
			long begin = System.currentTimeMillis();
			for (int i = 0; i < numOfTasks; i++) {
				futureList.add(executorService.submit(new SleeperAgent(i)));
			}
			for (Future<?> future: futureList) {
				future.get();
			}
			long end = System.currentTimeMillis();
			System.out.printf("Time taken to complete %d tasks: %d\n", numOfTasks, end - begin);
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}