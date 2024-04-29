package com.ms.mt;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class LockLessThreadSafeStack<T> {

	private final AtomicReference<StackNode<T>> head = new AtomicReference<>();

	private final AtomicLong counter = new AtomicLong(0);

	public void push(T value) {
		StackNode<T> newNode = new StackNode<>(value);
		StackNode<T> currentHeadNode = head.get();
		newNode.next = currentHeadNode;
		while (!head.compareAndSet(currentHeadNode, newNode)) {
			LockSupport.parkNanos(1);
			currentHeadNode = head.get();
			newNode.next = currentHeadNode;
		}
		counter.incrementAndGet();
	}

	public T pop() {
		if (head.get() == null) {
			counter.incrementAndGet();
			return null;
		}
		StackNode<T> currentHead = head.get();
		StackNode<T> newHeadNode;
		while (currentHead != null) {
			T value = currentHead.value;
			newHeadNode = currentHead.next;
			if (head.compareAndSet(currentHead, newHeadNode)) {
				counter.incrementAndGet();
				return value;
			} else {
				LockSupport.parkNanos(1);
				currentHead = head.get();
			}
		}
		counter.incrementAndGet();
		return null;
	}

	public long getCounter() {
		return counter.get();
	}

	private static class StackNode<T> {
		private final T value;
		private StackNode<T> next;

		public StackNode(T value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			StackNode<?> stackNode = (StackNode<?>) o;
			return Objects.equals(value, stackNode.value) && Objects.equals(next, stackNode.next);
		}

		@Override
		public int hashCode() {
			return Objects.hash(value, next);
		}
	}
}
