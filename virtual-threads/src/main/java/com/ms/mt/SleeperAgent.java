package com.ms.mt;

public class SleeperAgent implements Runnable {

	private final int taskId;

	public SleeperAgent(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		System.out.printf("Starting task {%d} in thread: %s\n", taskId, Thread.currentThread());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.printf("Completed task {%d} in thread: %s\n", taskId, Thread.currentThread());
	}
}
