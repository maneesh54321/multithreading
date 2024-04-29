package com.ms.mt;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class LockLessThreadSafeStackTest {

	public static final Random RANDOM = new Random();

	@Test
	public void testPushPop() throws InterruptedException {
		LockLessThreadSafeStack<Integer> stack = new LockLessThreadSafeStack<>();

		int initialNoOfElements = 100_000;

		for (int i = 0; i < initialNoOfElements; i++) {
			stack.push(RANDOM.nextInt(1000));
		}

		List<Thread> threads = new ArrayList<>();

		int pushingThreadsCount = 2;
		for (int i = 0; i < pushingThreadsCount; i++) {
			threads.add(Thread.ofPlatform().name("Pusher-" + i).daemon().unstarted(() -> {
				while (true) {
					stack.push(RANDOM.nextInt(1000));
				}
			}));
		}

		int poppingThreadsCount = 2;
		for (int i = 0; i < poppingThreadsCount; i++) {
			threads.add(Thread.ofPlatform().name("Popper-" + i).daemon().unstarted(() -> {
				while (true) {
					stack.pop();
				}
			}));
		}

		for (Thread thread: threads) thread.start();
		Thread.sleep(10000);
		System.out.println("Number of stack operations in 10 sec is: " + stack.getCounter());
	}

}