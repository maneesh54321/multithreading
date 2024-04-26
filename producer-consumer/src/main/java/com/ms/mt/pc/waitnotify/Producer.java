package com.ms.mt.pc.waitnotify;

public class Producer extends Thread {

	private final TaskQueue taskQueue;

	private final int totalTasks;

	public Producer(TaskQueue taskQueue, int totalTasks) {
		this.taskQueue = taskQueue;
		this.totalTasks = totalTasks;
	}

	@Override
	public void run() {
		int count = 1;
		while (count < totalTasks) {
			synchronized (taskQueue) {
				try {
					if (taskQueue.isFull()) {
						taskQueue.wait();
					}
					taskQueue.addTask(count++);
					taskQueue.notify();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		System.out.printf("%s : Tasks produced = %d\n", Thread.currentThread().getName(), count);
	}
}
