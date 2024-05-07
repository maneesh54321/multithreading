package com.ms.mt.pc.semaphore.v2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class DataIngestionMachine<T> {

	private final Semaphore empty;

	private final Semaphore full = new Semaphore(0);

	private final Deque<T> taskQueue;

	private final Lock lock;


	public DataIngestionMachine(int sizeOfBuffer) {
		this.taskQueue = new ArrayDeque<>(sizeOfBuffer);
		empty = new Semaphore(sizeOfBuffer);
		lock = new ReentrantLock();
	}

	public static class Producer<T> {
		private final Deque<T> taskQueue;
		private final Supplier<T> supplier;
		private final Semaphore empty;
		private final Semaphore full;
		private final Lock lock;

		public Producer(Deque<T> taskQueue, Supplier<T> supplier, Semaphore empty, Semaphore full,
				Lock lock) {
			this.taskQueue = taskQueue;
			this.supplier = supplier;
			this.empty = empty;
			this.full = full;
			this.lock = lock;
		}

		public void produce() {
			while (true) {
				try {
					empty.acquire();
					lock.lock();
					taskQueue.add(supplier.get());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					lock.unlock();
				}
				full.release();
			}
		}
	}

	public static class Consumer<T> {
		private final Deque<T> taskQueue;
		private final Semaphore empty;
		private final Semaphore full;
		private final Lock lock;

		public Consumer(Deque<T> taskQueue, Semaphore empty, Semaphore full, Lock lock) {
			this.empty = empty;
			this.full = full;
			this.taskQueue = taskQueue;
			this.lock = lock;
		}

		public void consume() {
			while (true) {
				try {
					full.acquire();
					lock.lock();
					var value = taskQueue.poll();
					System.out.println(value);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					lock.unlock();
				}
				empty.release();
			}
		}
	}

	public Producer<T> createProducer(Supplier<T> supplier) {
		return new Producer<>(taskQueue, supplier, empty, full, lock);
	}

	public Consumer<T> createConsumer() {
		return new Consumer<>(taskQueue, empty, full, lock);
	}

}
