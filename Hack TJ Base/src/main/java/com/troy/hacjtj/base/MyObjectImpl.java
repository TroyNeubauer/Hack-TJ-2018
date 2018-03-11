package com.troy.hacjtj.base;

public class MyObjectImpl implements MyObject {
	
	protected long id;

	public MyObjectImpl(long id) {
		this.id = id;
	}



	@Override
	public long getId() {
		return id;
	}

}
