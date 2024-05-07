package org.example;

public class BusinessProcess extends Thread {

	private final CountDownLatch countDownLatch;

	public BusinessProcess(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.printf("%s completed part 1 of the task.\n", Thread.currentThread().getName());
		countDownLatch.countDown();
		countDownLatch.await();
		System.out.printf("%s completed part 2 of the task.\n", Thread.currentThread().getName());
	}
}
