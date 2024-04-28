package org.example;

public class BlockingTask implements Runnable {

	@Override
	public void run() {
		try {
			System.out.printf("Executing in Thread: %s\n", Thread.currentThread());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}
