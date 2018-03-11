package com.troy.hacjtj.base;

public class Course extends MyObjectImpl {
	private String name;
	private static long count = 0;
	
	public Course(String name) {
		super(count++);
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

    public String getName() {
        return name;
    }
}
