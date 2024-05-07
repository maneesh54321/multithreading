package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownLatch {

	private final Lock lock;
	private final Condition condition;
	private int count;

	public CountDownLatch(int numOfWorkers) {
		lock = new ReentrantLock();
		condition = lock.newCondition();
		count = numOfWorkers;
	}

	public void await() {
		try {
			lock.lock();
			if(count > 0){
				condition.await();
				Thread.sleep(40000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

	public void countDown() {
		try {
			lock.lock();
			count--;
			if(count == 0){
				Thread.sleep(40000);
				condition.signalAll();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}
}
