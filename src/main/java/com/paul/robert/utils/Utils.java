package com.paul.robert.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

	private static Long MIN = 500L;
	private static Long MAX = 1000L * 10L;
	
	private Utils() {}
	
	public static Long generateRandomNumber() {
		return ThreadLocalRandom.current().nextLong(MIN, MAX);
	}
	
	public static void waitOnMe() {
		try {
			Thread.sleep(generateRandomNumber());
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
