package com.ms.mt.pc.waitnotify;

public class TaskQueue {
	private static final int SIZE = 50;
	private int count;
	private final int[] taskIds;

	public TaskQueue() {
		this.count = 0;
		this.taskIds = new int[SIZE];
	}

	public synchronized void addTask(int taskId) {
		if (isNotFull()) {
			taskIds[count++] = taskId;
		} else {
			throw new RuntimeException("Task queue is full!!");
		}
	}

	public synchronized int removeTask() {
		if (isNotEmpty()) {
			return taskIds[count--];
		} else {
			throw new RuntimeException("Task queue is empty!!");
		}
	}

	public boolean isNotFull() {
		return count + 1 != SIZE;
	}

	public boolean isFull() {
		return count + 1 == SIZE;
	}

	public boolean isNotEmpty() {
		return count != 0;
	}

	public boolean isEmpty() {
		return count == 0;
	}
}
