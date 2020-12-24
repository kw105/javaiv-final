/**
 * Demonstrate Java Stream skills learned through the UCSD Java IV course
 * A Java console application demonstrating Java Streams
 * @author Kevin
 */

package com.adv.java.streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

public class Lesson3Streams {

	public static void demonstrateStreams (String filename) {

		//String filename = args[0];

		// Read the contents of the attached file into a string

		String contents = "";
		try {
			contents = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Display the size of the String
		System.out.println("Try 1:\n" + "String Size: " + contents.length());

		// Measure the difference when counting the number of times the pattern 00000000nnnnnnnn occurs using a non-parallel stream
		long nonParallelStreamTime = findPatternByNonParallelStream(contents);

		// Measure the difference when counting the number of times the pattern 00000000nnnnnnnn occurs using a parallel stream
		long parallelStreamTime = findPatternByParallelStream(contents);

		// Report results
		reportRunTimeResults(nonParallelStreamTime, parallelStreamTime);

		// Save the contents to another string to support the run time testing on multiple copies of the string
		String origContents = new String(contents);

		// Make 200 copies of the string and re-do the test
		for (int m=1;m<200;m++) {
			contents += origContents;
		}

		System.out.println("\nIncreasing the string size by 200 times and trying again.\nTry 2:\n" + "String Size: " + contents.length());

		// Measure the difference when counting the number of times the pattern 00000000nnnnnnnn occurs using a non-parallel stream
		nonParallelStreamTime = findPatternByNonParallelStream(contents);

		// Measure the difference when counting the number of times the pattern 00000000nnnnnnnn occurs using a parallel stream
		parallelStreamTime = findPatternByParallelStream(contents);

		// Report results
		reportRunTimeResults(nonParallelStreamTime, parallelStreamTime);

		//System.out.println("\nAll done!");


	}

	/**
	 * Count the number of times the pattern 00000000nnnnnnnn occurs in a string using Java Non-Parallel Stream
	 * Measure and return the time in milliseconds
	 * @param str -- the string to search for the pattern
	 * @return number of milliseconds it takes to count the number of times the pattern occurs
	 */
	private static long findPatternByNonParallelStream (String str) {
		Scanner in = new Scanner(str);
		Stream<String> words = null;

		long begin = 0;
		long end = 0;
		long count = 0;
		long result = 0;

		// Run 1 time		
		for (int k=0;k<1;k++)  {
			begin = System.currentTimeMillis();

			words = in.findAll("00000000[0-9A-Fa-f]{8}").map(MatchResult::group);
			count = words.count();

			end = System.currentTimeMillis();

			//System.out.println("Number of times the pattern 00000000nnnnnnnn occurs: " + count);
			result = end -begin;
			System.out.println("Millisecs using stream: " + result);
		}
		in.close();
		return result;
	}

	/**
	 * Count the number of times the pattern 00000000nnnnnnnn occurs in a string using Java Parallel Stream
	 * Measure and return the time in milliseconds
	 * @param str -- the string to search for the pattern
	 * @return number of milliseconds it takes to count the number of times the pattern occurs
	 */
	private static long findPatternByParallelStream (String str) {
		Scanner in = new Scanner(str);
		Stream<String> wordsParallel = null;

		long begin = 0;
		long end = 0;
		long count = 0;
		long result = 0;

		// Run 1 time		
		for (int k=0;k<1;k++)  {
			begin = System.currentTimeMillis();

			wordsParallel = in.findAll("00000000[0-9A-Fa-f]{8}").map(MatchResult::group).parallel();

			count = wordsParallel.count();

			end = System.currentTimeMillis();

			//System.out.println("Number of times the pattern 00000000nnnnnnnn occurs: " + count);
			result = end -begin;
			System.out.println("Millisecs using parallelStream: " + result);
		}
		in.close();
		return result;
	}

	/**
	 * Report whether Non-Parallel or Parallel Stream is faster and by how much
	 * @param nonParallelTime -- number of milliseconds it takes to count the number of times the pattern occurs using Non-Parallel Stream
	 * @param parallelTime -- number of milliseconds it takes to count the number of times the pattern occurs using Parallel Stream
	 */
	private static void reportRunTimeResults(long nonParallelTime, long parallelTime) {
		double fasterPercent = 0;

		if (nonParallelTime < parallelTime) {
			fasterPercent = 100 * (1 - (nonParallelTime*1.0/parallelTime));
			System.out.println("Results: stream was " + String.format("%.2f", fasterPercent) + "% faster than parallelStream");
		}
		else {
			fasterPercent = 100 * (1 - (parallelTime*1.0/nonParallelTime));
			System.out.println("Results: parallelStream was " + String.format("%.2f", fasterPercent) + "% faster than stream");
		}
	}


}
