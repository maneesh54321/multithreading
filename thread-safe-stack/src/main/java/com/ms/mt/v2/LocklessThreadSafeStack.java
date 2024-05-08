package com.ms.mt.v2;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class LocklessThreadSafeStack<T> implements Stack<T> {
	private final AtomicReference<ListNode<T>> head = new AtomicReference<>();
	private final AtomicLong count = new AtomicLong(0);

	@Override
	public void push(T value) {
		ListNode<T> oldHead;
		ListNode<T> newHead = new ListNode<>(value);
		do {
			oldHead = head.get();
			newHead.setNext(oldHead);
		} while (!head.compareAndSet(oldHead, newHead));
		count.incrementAndGet();
	}

	@Override
	public T pop(){
		ListNode<T> oldHead;
		ListNode<T> newHead;
		do {
			oldHead = head.get();
			if(oldHead == null) return null;
			newHead = oldHead.getNext();
		} while (!head.compareAndSet(oldHead, newHead));
		count.incrementAndGet();
		return oldHead.getValue();
	}

	@Override
	public long getCount() {
		return count.get();
	}
}
