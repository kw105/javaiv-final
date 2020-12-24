/**
 * Final assignment for the UCSD Java IV course. It creates Java Modules that demonstrate some of the key skills learned in each lesson during this course.
 * @author Kevin
 */

package com.adv.java.concluding.application;

import com.adv.java.iostream.Lesson1IOStream;
import com.adv.java.xml.Lesson4XML;
import com.adv.java.regex.Lesson2RegEx;
import com.adv.java.streams.Lesson3Streams;
import com.adv.java.concurrency.Lesson5Concurrent;
import com.adv.java.scripting.Lesson9ScriptingDemo;
import com.adv.java.database.Lesson7Database;

import java.io.*;

public class FinalApp {

	public static void main (String[] args) {
		String filename = "final.xml";
		String filename2 = "neighbor-dump.txt";
		String filename3 = "JobResult_124432.txt";
		String filename4 = "Lessons.sql";
		try {
			System.out.println("Using com.adv.java.iostream to read in the following file:\n" + filename);
			Lesson1IOStream.readTextFile(filename);

			System.out.println("\nUsing com.adv.java.xml to parse the XML.\n" + "The results are as follows:");
			System.out.println("Results of XML Parsing using SAX Parser:");
			Lesson4XML.displayResultsBySAXParser(filename);

			System.out.println("\nUsing com.adv.java.regex to parse the file " + filename2 + " using regular expression.\n" + "The results are as follows:");
			Lesson2RegEx.demonstrateRegEx(filename2);

			System.out.println("\nUsing com.adv.java.streams to read the file " + filename3 + " to a string.\nFirst display the string size.\n" +
								"Then measure the difference when counting the number of times the pattern 00000000nnnnnnnn occurs, using Java Streams.\n" +
								"First with a (non-parallel) stream, then with a parallelStream.\n" +
								"The results are as follows:");
			Lesson3Streams.demonstrateStreams(filename3);

			System.out.println("\nUsing com.adv.java.concurrency to demonstrate Java concurrency using the file " + filename2 +
								".\nCount the number of characters in the file using different locking methods and numbers of threads.\nThe results are as follows:");
			//Lesson5Concurrent.demonstrateConcurrency(filename2, 0, 8);
			//Lesson5Concurrent.demonstrateConcurrency(filename2, 0, 16);
			Lesson5Concurrent.demonstrateConcurrency(filename2, 1, 8);
			Lesson5Concurrent.demonstrateConcurrency(filename2, 1, 16);
			Lesson5Concurrent.demonstrateConcurrency(filename2, 2, 8);
			Lesson5Concurrent.demonstrateConcurrency(filename2, 2, 16);

			System.out.println("\nUsing com.adv.java.scripting to demonstrate Java scripting.\nThe results are as follows:");
			Lesson9ScriptingDemo.demonstrateScripting();

			System.out.println("\nUsing com.adv.java.database to demonstrate Java database using the file " + filename4 +
								".\nRead in the file and use JDBC to populate the database.\n" +
								"After populating the database, query the Lessons table metadata to read the column names.\n" +
								"Use these column names as the headers for output.\n" +
								"Then query the contents of the Lessons table, displaying each lesson number and title on a separate line.\n" +
								"The results are as follows:");
			Lesson7Database.demonstrateDatabase(filename4);

			System.out.println("\nAll done!\n");

			System.out.println("Please run the following at console to shut down the derby database server from the above demonstration:");
			System.out.println("java -jar lib/derbyrun.jar server shutdown");


		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
