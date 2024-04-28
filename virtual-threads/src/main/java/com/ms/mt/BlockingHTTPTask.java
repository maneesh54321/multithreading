package com.ms.mt;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BlockingHTTPTask implements Runnable {

	private final int taskId;

	public BlockingHTTPTask(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		System.out.printf("Starting task {%d} in thread: %s\n", taskId, Thread.currentThread());
		String uri = "http://localhost:8080/hello";
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			con.setDoOutput(false);

			int status = con.getResponseCode();

			System.out.println(status);

			System.out.printf("Completed task {%d} in thread: %s\n", taskId, Thread.currentThread());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
