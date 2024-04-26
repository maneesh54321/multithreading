package com.ms.mt.pc.semaphore;

import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {

	private final Deque<Integer> taskQueue;
	private final Semaphore fullSemaphore;
	private final Semaphore emptySemaphore;
	private final Lock lock;

	public Consumer(Deque<Integer> taskQueue, Semaphore fullSemaphore, Semaphore emptySemaphore, Lock lock) {
		this.taskQueue = taskQueue;
		this.fullSemaphore = fullSemaphore;
		this.emptySemaphore = emptySemaphore;
		this.lock = lock;
	}

	@Override
	public void run() {
		while (true) {
			try {
				fullSemaphore.acquire();
				lock.lock();
				Integer val;
				try {
					val = taskQueue.poll();
				} finally {
					lock.unlock();
				}
				System.out.println("Consumed: " + val);
				emptySemaphore.release();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
