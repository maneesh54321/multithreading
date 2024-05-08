package com.ms.mt.v2;

import java.util.Random;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Stack<Integer> stack = new ThreadSafeStack<>();
//		Stack<Integer> stack = new LocklessThreadSafeStack<>();
		Random random = new Random();

		for (int i = 0; i < 10_000; i++){
			stack.push(random.nextInt(1000));
		}

		Thread.ofPlatform().daemon().start(() -> {
			while (true) {
				stack.push(random.nextInt(1000));
			}
		});
		Thread.ofPlatform().daemon().start(() -> {
			while (true) {
				stack.pop();
			}
		});
		Thread.sleep(10_000);
		System.out.println("No of operations:" + stack.getCount());
	}
}
