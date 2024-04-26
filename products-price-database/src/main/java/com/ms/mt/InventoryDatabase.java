package com.ms.mt;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InventoryDatabase {
	private final TreeMap<Integer, Integer> treeMap;

	private final ReentrantLock lock = new ReentrantLock();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();

	public InventoryDatabase() {
		this.treeMap = new TreeMap<>();
	}

	public Integer getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
		readLock.lock();
		try {
			Integer upperKey = treeMap.ceilingKey(upperBound);
			Integer lowerKey = treeMap.floorKey(lowerBound);
			if(lowerKey == null || upperKey == null){
				return 0;
			}
			SortedMap<Integer, Integer> subset = treeMap.subMap(lowerKey, upperKey);
			Integer sum = 0;
			for (Integer value : subset.values()) {
				sum += value;
			}
			return sum;
		} finally {
			readLock.unlock();
		}
	}

	public void addItem(int price) {
		writeLock.lock();
		try {
			if(!treeMap.containsKey(price)) {
				treeMap.put(price, 0);
			}
			treeMap.put(price, treeMap.get(price) + 1);
		} finally {
			writeLock.unlock();
		}
	}

	public void removeItem(int price) {
		writeLock.lock();
		try {
			Integer num = treeMap.get(price);
			if(num == null || num == 1) {
				treeMap.remove(price);
			} else {
				treeMap.put(price, num - 1);
			}
		} finally {
			writeLock.unlock();
		}
	}
}
