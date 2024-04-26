package com.ms.mt.pc.waitnotify;

public class Consumer extends Thread {

	private final TaskQueue taskQueue;

	public Consumer(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	@Override
	public void run() {
		int count = 1;
		while (count < 10_000) {
			synchronized (taskQueue) {
				try {
					if (taskQueue.isEmpty()) {
						taskQueue.wait();
					}
					taskQueue.removeTask();
					taskQueue.notify();
					count++;
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		System.out.printf("%s : Tasks consumed = %d\n", Thread.currentThread().getName(), count);
	}
}
