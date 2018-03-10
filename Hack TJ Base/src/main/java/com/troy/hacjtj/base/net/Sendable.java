package com.troy.hacjtj.base.net;

import com.troy.hacjtj.base.account.Account;

public interface Sendable<T> {
	
	/**
	 * Sends some response back to the sender using Kryo serialization
	 * @param responce The response
	 */
	public void sendResponse(Object responce);

	/**
	 * Disconnects the sender from this side if there is an open connection
	 */
	public void disconnect();
	
	/**
	 * Sets some data stored about this sender (used for storing accounts on the server side)
	 * @param data The data
	 */
	public void setData(T data);
	
	/**
	 * Returns the account of the sender or null if the sender is the server
	 * @return The account of the sender
	 */
	public Account getAccount();
}