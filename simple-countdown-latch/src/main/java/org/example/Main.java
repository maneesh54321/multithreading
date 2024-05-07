package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		int numOfBusinessProcess = 5;
		CountDownLatch countDownLatch = new CountDownLatch(numOfBusinessProcess);
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < numOfBusinessProcess; i++) {
			threadList.add(new BusinessProcess(countDownLatch));
		}
		for (Thread thread: threadList) {
			thread.start();
		}
		try {
			for (Thread thread: threadList){
				thread.join();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}