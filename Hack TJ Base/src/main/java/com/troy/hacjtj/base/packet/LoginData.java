package com.troy.hacjtj.base.packet;

import java.util.Arrays;

public class LoginData extends PacketData {
	private char[] username, password;

	public LoginData() {

	}

	public LoginData(char[] username, char[] password) {
		this.username = username;
		this.password = password;
	}

	public char[] getUsername() {
		return username;
	}

	public char[] getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "LoginData [username=" + Arrays.toString(username) + ", password=" + Arrays.toString(password) + "]";
	}

}