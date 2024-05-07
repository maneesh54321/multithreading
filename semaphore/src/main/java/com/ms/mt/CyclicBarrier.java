package com.ms.mt;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrier {

	private final Lock lock;
	private final int numOfWorkers;
	private int count;
	private final Semaphore semaphore;

	public CyclicBarrier(int numOfWorkers) {
		this.numOfWorkers = numOfWorkers;
		this.lock = new ReentrantLock();
		this.count = 0;
		semaphore = new Semaphore(0);
	}

	public void await() {
		try {
			lock.lock();
			count++;
		} finally {
			lock.unlock();
		}
		if(numOfWorkers == count) {
			semaphore.release(numOfWorkers - 1);
		} else {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
