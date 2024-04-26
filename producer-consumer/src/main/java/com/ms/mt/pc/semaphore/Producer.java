package com.ms.mt.pc.semaphore;

import java.util.Deque;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Producer extends Thread {
	private final Deque<Integer> taskQueue;
	private final Semaphore fullSemaphore;
	private final Semaphore emptySemaphore;
	public static final Random RANDOM = new Random();
	private final Lock lock;

	public Producer(Deque<Integer> taskQueue, Semaphore fullSemaphore, Semaphore emptySemaphore, Lock lock) {
		this.taskQueue = taskQueue;
		this.fullSemaphore = fullSemaphore;
		this.emptySemaphore = emptySemaphore;
		this.lock = lock;
	}

	@Override
	public void run() {
		while (true) {
			try {
				emptySemaphore.acquire();
				int e = RANDOM.nextInt(100);
				lock.lock();
				try {
					taskQueue.offer(e);
				} finally {
					lock.unlock();
				}
				fullSemaphore.release();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
