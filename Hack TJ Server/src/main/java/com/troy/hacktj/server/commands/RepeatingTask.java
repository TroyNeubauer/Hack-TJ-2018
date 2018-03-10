package com.troy.hacktj.server.commands;

public class RepeatingTask extends SimpleTask {

	private long duration;

	public RepeatingTask(long duration, Runnable task) {
		this(0, duration, task);
	}

	public RepeatingTask(long offset, long duration, Runnable task) {
		super(System.currentTimeMillis() + offset, task);
		this.duration = duration;
	}

	public void update() {
		setTime(System.currentTimeMillis() + duration);
	}

	@Override
	public void doTask() {
		super.doTask();
		this.update();// Re schedule for later
		getManager().addTask0(this);// Add this back!
	}

}