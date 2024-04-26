package com.ms.mt;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Barrier barrier = new Barrier(5);
		int numOfBusinessProcess = 5;
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < numOfBusinessProcess; i++) {
			threadList.add(new BusinessProcess(barrier));
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