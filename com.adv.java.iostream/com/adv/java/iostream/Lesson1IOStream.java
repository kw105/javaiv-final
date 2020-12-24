/**
 * Demonstrate Java IO Stream skills learned through the UCSD Java IV course
 * Demonstration of reading text files
 * 
 * This Java program is completed based on example codes
 * from course lectures and the textbook: Core Java, Volume II--
 * Advanced Features (11th Edition) By: Cay S. Horstmann
 * 
 * @author Kevin
 */

package com.adv.java.iostream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Lesson1IOStream {

	/**
	 * Read in a text file
	 * @param filename -- name of the text file
	 * @throws IOException
	 */
	public static void readTextFile(String filename) throws IOException, InterruptedException {

		String working_dir = System.getProperty("user.dir");
		//System.out.println("Current working dir: " + working_dir + "\n");


		String pathname = System.getProperty("user.dir") + File.separator + filename;
		//System.out.println(pathname);
		Path path = Paths.get(pathname);

		List<String> lines = Files.readAllLines(path);

		System.out.println("The contents of the " + filename + " are:\n");

		for (String sl : lines) {
			System.out.println(sl);
		}
	}


}
