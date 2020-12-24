/**
 * Demonstrate Java Regular Expression skills learned through the UCSD Java IV course
 * This Java console application contains methods that read in a given file (for example: neighbor-dump.txt)
 * and use Regular Expressions to generate required output showing the
 * lists of PAN IDs, MAC Addresses and RF_RSSI_M values from the file.
 * 
 * @author Kevin
 */

package com.adv.java.regex;

import java.util.ArrayList;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class Lesson2RegEx {

	public static void demonstrateRegEx (String filename) {
		//String filename = args[0];

		// Read file and store contents in a string
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(Paths.get(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String s = new String(bytes, Charset.forName("UTF-8"));

		// Find PAN IDs using regular expression and save to an ArrayList
		ArrayList<String> panIdList = new ArrayList<String>();

		String regex = "^\\s*(?<item1>PANID)\\s+(?<item2>=)\\s+(?<item3>\\p{Alnum}+)\\s*$";
		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(s);

		while (matcher.find()) {
			panIdList.add(new String(matcher.group("item3")));
		}

		// Output PAN IDs to console
		//System.out.println("Processed the following input file:\n" + filename + "\n" + "Results are as follows:\n"
		//		   + "- List of PAN IDs (Total of " + panIdList.size() + ")");
		System.out.println("- List of PAN IDs (Total of " + panIdList.size() + ")");

		for (int i = 0; i < panIdList.size(); i++) {
			System.out.println("PANID = " + panIdList.get(i));
		}

		// Find MAC Addresses and RF_RSSI_M values for each MAC address using regular expression and save each set of values to an ArrayList
		ArrayList<String> macAddressList = new ArrayList<String>();
		ArrayList<String> rfRssiMValList = new ArrayList<String>();

		regex = "^\\s*(?<item1>[a-zA-Z0-9_]+)\\s+(?<item2>\\p{Alnum}+)\\s+(?<item3>\\p{Alnum}+)\\s+(?<item4>\\p{Alnum}+)\\s+(?<item5>[0-9]+)\\s+(?<item6>[a-zA-Z0-9_]+)\\s+(?<item7>[a-zA-Z0-9_]+)\\s+(?<item8>[+-]?[0-9\\.]+)\\s+(?<item9>[+-]?[0-9\\.]+)\\s+(?<item10>[a-zA-Z0-9-]+)\\s+(?<item11>[a-zA-Z0-9-]+)\\s+(?<item12>[a-zA-Z0-9-]+)\\s+(?<item13>[a-zA-Z0-9-]+)\\s+(?<item14>[a-zA-Z0-9-]+)\\s+(?<item15>[0-9]+)\\s+(?<item16>\\p{Alnum}+)\\s*$";

		pattern = Pattern.compile(regex, Pattern.MULTILINE);
		matcher = pattern.matcher(s);
		while (matcher.find()) {
			macAddressList.add(new String(matcher.group("item2")));
			rfRssiMValList.add(new String(matcher.group("item8")));
		}

		// Output MAC addresses to console
		System.out.println("- List of MAC Addresses (Total of " + macAddressList.size() + ")\n");

		for (int i = 0; i < macAddressList.size(); i++) {
			System.out.println(macAddressList.get(i));
		}

		// Output the RF_RSSI_M value for each MAC address to console
		System.out.println("\n- List of RF_RSSI_M values for each MAC address\n");

		for (int i = 0; i < macAddressList.size(); i++) {
			System.out.println(macAddressList.get(i) + " " + rfRssiMValList.get(i));
		}


	}
}
