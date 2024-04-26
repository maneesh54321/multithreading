package com.ms.mt;

public class BusinessProcess extends Thread {

	private final Barrier barrier;

	public BusinessProcess(Barrier barrier) {
		this.barrier = barrier;
	}

	@Override
	public void run() {
		System.out.printf("%s completed part 1 of the task.\n", Thread.currentThread().getName());
		barrier.waitForOthers();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.printf("%s completed part 2 of the task.\n", Thread.currentThread().getName());
	}
}
