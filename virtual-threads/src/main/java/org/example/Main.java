package org.example;

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
//		try (ExecutorService executorService = Executors.newFixedThreadPool(8)) {
			long begin = System.currentTimeMillis();
			for (int i = 0; i < numOfTasks; i++) {
				futureList.add(executorService.submit(new BlockingTask()));
			}
			for (Future<?> future : futureList) {
				future.get();
			}
			long end = System.currentTimeMillis();
			System.out.printf("Time taken to complete all tasks is %d ms", end - begin);
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}