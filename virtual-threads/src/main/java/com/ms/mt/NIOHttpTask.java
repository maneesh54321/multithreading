package com.ms.mt;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NIOHttpTask implements Runnable {

	private final int taskId;

	public NIOHttpTask(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		System.out.printf("Starting task {%d} in thread: %s\n", taskId, Thread.currentThread());
		String url = "http://localhost:8080/hello";
		try(HttpClient httpClient = HttpClient.newHttpClient()) {
			httpClient.send(HttpRequest.newBuilder().GET().uri(new URI(url)).build(), HttpResponse.BodyHandlers.ofString());
			System.out.printf("Completed task {%d} in thread: %s\n", taskId, Thread.currentThread());
		} catch (InterruptedException | IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
