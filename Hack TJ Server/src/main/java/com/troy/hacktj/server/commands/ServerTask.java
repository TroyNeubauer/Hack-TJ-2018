package com.troy.hacktj.server.commands;

public interface ServerTask {
	
	
	public long getTime();
	
	public void doTask();
	
	public void setManager(BackgroundTaskManager manager);
}
