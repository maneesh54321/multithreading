package com.ms.mt.v2;

public class ThreadSafeStack<T> implements Stack<T> {

	private ListNode<T> head;

	private long count;

	@Override
	public synchronized void push(T value){
		ListNode<T> newHead = new ListNode<>(value);
		newHead.setNext(head);
		head = newHead;
		count++;
	}

	@Override
	public synchronized T pop(){
		if(head == null) return null;
		ListNode<T> poppedEle = head;
		head = head.getNext();
		count++;
		return poppedEle.getValue();
	}

	@Override
	public long getCount(){
		return count;
	}
}
