package com.ms.mt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
		Thread thread1 = new Thread(reentrantLockTest::method1);
		Thread thread2 = new Thread(reentrantLockTest::method2);

		try {
			thread1.start();
			Thread.sleep(1000);
			thread2.start();
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
		}
	}


	public void method1(){
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " Doing method1 stuff part1");
			Thread.sleep(10000);
			System.out.println(Thread.currentThread().getName() + " Calling method2 in lock!!");
			method2();
			System.out.println(Thread.currentThread().getName() + " Doing method1 stuff part2");
			System.out.println(Thread.currentThread().getName() + " Question: is lock locked?");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}

	}

	public void method2() {
		try {
			System.out.println(Thread.currentThread().getName() + " Trying to acquire lock!!");
			lock.lock();
			System.out.println(Thread.currentThread().getName() + " Doing method 2 stuff");
		} finally {
			lock.unlock();
		}
	}
}
