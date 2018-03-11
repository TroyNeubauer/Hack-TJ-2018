package com.troy.hacjtj.base.account;

public class School extends MyObjectImpl {
	private String name;
	private String countryCode;
	private String state;

	public School() {
	    this.name = "Test School";
	    this.countryCode = "US";
	    this.state = "Virginia";
    }

    @Override
    public String toString() {
        return name;
    }
}
