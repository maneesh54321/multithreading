package com.ms.mt;

public class BusinessProcess extends Thread {

	private final CyclicBarrier barrier;

	public BusinessProcess(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	@Override
	public void run() {
		System.out.printf("%s completed part 1 of the task.\n", Thread.currentThread().getName());
		barrier.await();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.printf("%s completed part 2 of the task.\n", Thread.currentThread().getName());
	}
}
