package com.ms.mt;

public class ThreadSafeStack<T> {

	private StackNode<T> head;

	private long counter;

	public synchronized void push(T value) {
		StackNode<T> newValue = new StackNode<>(value);
		newValue.next = head;
		head = newValue;
		counter++;
	}

	public synchronized T pop() {
		if (head == null) {
			counter++;
			return null;
		}
		T value = head.value;
		head = head.next;
		counter++;
		return value;
	}

	public long getCounter() {
		return counter;
	}

	private static class StackNode<T> {
		private final T value;
		private StackNode<T> next;

		public StackNode(T value) {
			this.value = value;
		}
	}
}
