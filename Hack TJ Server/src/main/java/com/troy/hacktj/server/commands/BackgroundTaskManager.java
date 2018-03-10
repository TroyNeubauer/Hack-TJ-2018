package com.troy.hacktj.server.commands;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class BackgroundTaskManager {

	private static BackgroundTaskManager instance;
	private Thread thread;
	private AtomicBoolean running = new AtomicBoolean(true);

	private final PriorityQueue<ServerTask> tasks = new PriorityQueue<ServerTask>(new Comparator<ServerTask>() {

		@Override
		public int compare(ServerTask o1, ServerTask o2) {
			long t1 = o1.getTime(), t2 = o2.getTime();
			if (t1 < t2) {
				return -1;
			} else if (t1 == t2) {
				return 0;
			} else {
				return 1;
			}
		}
	});

	public BackgroundTaskManager() {
		thread = new Thread(() -> {
			while (running.get()) {
				if (tasks.isEmpty()) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				} else {
					ServerTask task = tasks.peek();
					if (task.getTime() < System.currentTimeMillis()) {
						tasks.remove().doTask();
					}
				}

			}
		}, "Background Task Thread");

	}

	public void start0() {
		thread.start();
	}

	public void addTask0(ServerTask task) {
		task.setManager(this);
		tasks.add(task);
	}

	public void stop() {
		running.set(false);
	}

	public static void start() {
		if (instance == null)
			init();
		instance.start0();
	}

	public static void addTask(ServerTask task) {
		if (instance == null)
			init();
		instance.addTask0(task);
	}

	public static BackgroundTaskManager getInstance() {
		if (instance == null)
			init();
		return instance;
	}

	public static void init() {
		if (instance == null)
			instance = new BackgroundTaskManager();
		else
			throw new RuntimeException("Already running");

	}

}
