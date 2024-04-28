package com.ms.mt;

public class ThreadPinningTask implements Runnable{

	private final int taskId;

	public ThreadPinningTask(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		System.out.printf("Starting task {%d} in thread: %s\n", taskId, Thread.currentThread());
		synchronized (this) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.printf("Completed task {%d} in thread: %s\n", taskId, Thread.currentThread());
	}
}
