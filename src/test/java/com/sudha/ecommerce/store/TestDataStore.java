package com.sudha.ecommerce.store;

public class TestDataStore {

	private static ThreadLocal<String> userName = new ThreadLocal<>();
	private static ThreadLocal<String> password = new ThreadLocal<>();

	public static void setUserName(String value) {
		userName.set(value);
	}

	public static String getUserName() {
		return userName.get();
	}

	public static void setPassword(String value) {
		password.set(value);
	}

	public static String getPassword() {
		return password.get();
	}
}
