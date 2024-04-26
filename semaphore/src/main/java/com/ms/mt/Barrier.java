package com.ms.mt;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {

	private final int numberOfWorkers;
	private final Semaphore semaphore;
	private int count;
	private final Lock lock;

	public Barrier(int numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
		semaphore = new Semaphore(0);
		count = 0;
		lock = new ReentrantLock();
	}

	public void waitForOthers(){
		try {
			lock.lock();
			count++;
		} finally {
			lock.unlock();
		}
		try {
			if(count == numberOfWorkers) {
				semaphore.release(numberOfWorkers - 1);
			} else {
				semaphore.acquire();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
