package com.ms.mt.pc.semaphore;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCApp {

	public static final int CAPACITY = 50;

	public static void main(String[] args) {

		Deque<Integer> taskQueue = new ArrayDeque<>(CAPACITY);
		Semaphore fullSemaphore = new Semaphore(0);
		Semaphore emptySemaphore = new Semaphore(CAPACITY);
		Lock lock = new ReentrantLock();

		List<Thread> threadList = new ArrayList<>();
		int numOfProducers = 10;
		for (int i = 0; i < numOfProducers; i++) {
			threadList.add(new Producer(taskQueue, fullSemaphore, emptySemaphore, lock));
		}
		int numOfConsumers = 10;
		for (int i = 0; i < numOfConsumers; i++) {
			threadList.add(new Consumer(taskQueue, fullSemaphore, emptySemaphore, lock));
		}
		for (Thread thread: threadList){
			thread.start();
		}
		for (Thread thread: threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}
	}
}
