package com.ms.mt;

public class MyRunnable implements Runnable {
	@Override
	public synchronized void run() {
		// Synchronized block to ensure thread-safe execution
		System.out.println("Starting synchronized run() method.");

		// Your synchronized code goes here
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + ": " + i);
			try {
				Thread.sleep(100); // Simulate some work
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Exiting synchronized run() method.");
	}

	public static void main(String[] args) {
		// Create multiple instances of MyRunnable
		MyRunnable myRunnable1 = new MyRunnable();
		MyRunnable myRunnable2 = new MyRunnable();

		// Create multiple threads and start them
//		Thread thread1 = new Thread(myRunnable1, "Thread-1");
//		Thread thread2 = new Thread(myRunnable2, "Thread-2");
		Thread thread1 = Thread.ofVirtual().unstarted(myRunnable1);
		Thread thread2 = Thread.ofVirtual().unstarted(myRunnable2);

		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
