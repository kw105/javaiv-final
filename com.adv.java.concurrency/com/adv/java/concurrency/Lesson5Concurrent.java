/**
 * Demonstrate Java Scrpting skills learned through the UCSD Java IV course
 * @author Kevin
 */

package com.adv.java.concurrency;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Lesson5Concurrent {

	public static Counter myCounterWithoutLock = new Counter();

	public static int myCountWithLock;
	public static Lock countLock = new ReentrantLock();

	public static AtomicLong atomCount = new AtomicLong();

	public static void demonstrateConcurrency (String filename, int lockingMechanism, int numThreads) throws IOException, InterruptedException {

		if (lockingMechanism == 0) {


			myCounterWithoutLock = new Counter();
		}
		else if (lockingMechanism == 1) {

			myCountWithLock = 0;

			countLock = new ReentrantLock();

		}
		else if (lockingMechanism == 2) {

			atomCount = new AtomicLong();
		}

		// Read in in the first file to lines and then add to an ArrayList of byte[]

		String pathname = System.getProperty("user.dir") + File.separator + filename;
		Path path = Paths.get(pathname);
		List<String> lines = Files.readAllLines(path);
		ArrayList<byte[]> byteArrayList = new ArrayList<byte[]>(lines.size()+1);
		for (int j = 0; j < lines.size(); j++) {
			String str = lines.get(j);
			//if (j < (lines.size() - 1)) {
			str = str + "\n";
			//}
			byteArrayList.add(str.getBytes());
		}

		ExecutorService executor = Executors.newFixedThreadPool(numThreads);


		// ReentrantLock
		if (lockingMechanism == 1) {
			for (int i = 0; i < byteArrayList.size(); i++) {
				byte[] bytes = byteArrayList.get(i);
				Runnable task = () -> {
					for (byte b : bytes) {
						//atomCount.incrementAndGet();
						countLock.lock();
						try {
							myCountWithLock++; // Critical section
						} finally {
							countLock.unlock(); // Make sure the lock is unlocked
						}
					}
				};
				executor.execute(task);
			}
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.MINUTES);

			//System.out.println("Final value: " + myCountWithLock);
			System.out.println("Using ReentrantLock and " + numThreads + " threads");
			System.out.println("The total number of characters that are in " + filename + " is: " + myCountWithLock);
		}

		// AtomicLong
		else if (lockingMechanism == 2) {
			for (int i = 0; i < byteArrayList.size(); i++) {
				byte[] bytes = byteArrayList.get(i);
				Runnable task = () -> {
					for (byte b : bytes) {
						atomCount.incrementAndGet();
					}
				};
				executor.execute(task);
			}
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.MINUTES);
			//System.out.println("Final value: " + atomCount);

			System.out.println("Using AtomicLong and " + numThreads + " threads");
			System.out.println("The total number of characters that are in " + filename + " is: " + atomCount);
		}

		// No locking
		else {
			for (int i = 0; i < byteArrayList.size(); i++) {
				byte[] bytes = byteArrayList.get(i);
				Runnable task = () -> {
					for (byte b : bytes) {
						myCounterWithoutLock.increment();
					}
				};
				executor.execute(task);
			}
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.MINUTES);

			System.out.println("Using no locking and " + numThreads + " threads");
			System.out.println("The total number of characters that are in " + filename + " is: " + (myCounterWithoutLock.increment() - 1));
		}

	}

	private static void displayHelpMessage() {
		System.out.println("This is a Java console application that does the following:\n\n" +
				"- Accepts --num-threads NUMBER-OF-THREADS where you can specify how many threads to create. Default is 8.\n" +
				"- Accepts --ReentrantLock if you want to use locking. By default you do not use any locking.\n" +
				"- Accepts --AtomicLong if you want to use AtomicLong. By default you do not use any locking.\n" +
				"- For any entered flag that does not exactly match the above accepted parameter flags, the default parameter value will be used.\n" +
				"- Accepts --help to display this help message.\n\n" +
				"If no argument is supplied, this help message will be displayed.");
	}

	private static int indexOfFlagInArguments (String[] arguments, String parameter) {

		for (int k = 0; k < arguments.length; k++) {
			//if ( parameter.equals(arguments[k]) ) {
			if ( (arguments[k]).equals(parameter) ) {
				return k;
			}
		}
		return -1;
	}

}


class Counter {
	private int value;
	public synchronized int increment() {
		value++;
		return value;
	}
}
