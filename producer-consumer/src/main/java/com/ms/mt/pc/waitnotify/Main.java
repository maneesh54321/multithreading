package com.ms.mt.pc.waitnotify;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		TaskQueue taskQueue = new TaskQueue();

		int numOfProducers = 1;
		List<Thread> producers = new ArrayList<>();
		for (int i = 0; i < numOfProducers; i++) {
			Thread producer = new Producer(taskQueue, 10_000);
			producers.add(producer);
		}

		int numOfConsumers = 1;
		List<Thread> consumers = new ArrayList<>();
		for (int i = 0; i < numOfConsumers; i++) {
			Thread consumer = new Consumer(taskQueue);
			consumers.add(consumer);
		}

		List<Thread> allThreads = new ArrayList<>();
		allThreads.addAll(consumers);
		allThreads.addAll(producers);

		for (Thread thread: allThreads){
			thread.start();
		}
		for (Thread thread: allThreads){
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}
	}
}