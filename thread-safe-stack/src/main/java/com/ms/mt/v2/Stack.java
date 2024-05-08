package com.ms.mt.v2;

public interface Stack<T> {
	void push(T value);
	T pop();
	long getCount();
}
