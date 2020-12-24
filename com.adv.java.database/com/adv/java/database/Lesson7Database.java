/**
 * Demonstrate Java Database skills learned through the UCSD Java IV course
 * A Java console application that reads in the attached file (Lessons.sql) and uses JDBC to populate a Apache Derby database.
 * This java program is implemented based on example codes from the course lecture/textbook. Parts of this program are copied/slightly
 * modified from the lecture/textbook.
 * @author Kevin
 */

package com.adv.java.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Lesson7Database {

	public static void demonstrateDatabase(String filename) {

		try (
				Scanner in = new Scanner(Paths.get(filename), StandardCharsets.UTF_8);
				Connection conn = getConnection();
				Statement stat = conn.createStatement()
				)

		{
			System.out.println("Connecting to the database: Done.");

			try {
				if (tableExistsInDB(conn, "Lessons")) {
					stat.executeUpdate("DROP TABLE Lessons");
					//System.out.println("A table named Lessons already exists in the database. It is now dropped and will be re-created and populated.");
				}
			}
			catch (SQLException e) {
				System.out.println("A SQLException occurred when checking whether the Lessons table exists in the database " +
						"or when trying to drop the Lessons table if it exists.");
				for (Throwable t : e)
					t.printStackTrace();
			}

			//while (true)
			while(in.hasNextLine())
			{
				//if (args.length == 0) System.out.println("Enter command or EXIT to exit:");
				//if (!in.hasNextLine()) return;

				String line = in.nextLine().trim();
				//if (line.equalsIgnoreCase("EXIT")) return;
				if (line.endsWith(";")) { // remove trailing semicolon
					line = line.substring(0, line.length() - 1);
				}
				if (line.length() >0) {
					try {
						boolean isResult = stat.execute(line);
						if (isResult) {
							try (ResultSet rs = stat.getResultSet()) {
								showResultSet(rs);
							}
						}
						else {
							int updateCount = stat.getUpdateCount();
							// System.out.println(updateCount + " rows updated");
						}                   
					}
					catch (SQLException e)
					{
						for (Throwable t : e)
							t.printStackTrace();
					}
				}
			}

			System.out.println("Populating the database: Done.");

			try
			{
				boolean isResult = stat.execute("select * from Lessons");
				if (isResult)
				{
					try (ResultSet rs = stat.getResultSet())
					{
						System.out.println("Query of Lessons table results:\n");
						showResultSet(rs);
					}
				}
			}
			catch (SQLException e)
			{
				for (Throwable t : e)
					t.printStackTrace();
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			for (Throwable t : e)
				t.printStackTrace();
		}




	}

	/**
	 * Gets a connection from the properties specified in the file database.properties
	 * @return the database connection
	 */
	public static Connection getConnection() throws SQLException, IOException
	{
		var props = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get("database.properties")))
		{
			props.load(in);
		}
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null) System.setProperty("jdbc.drivers", drivers);

		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		System.out.println("Database Connection info:");
		System.out.println("Drivers: " + drivers + " | " +
				"URL: " + url + " | " +
				"Username: " + username +
				" | " + "Password: " + password);

		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Prints a result set.
	 * @param result the result set to be printed
	 */
	public static void showResultSet(ResultSet result) throws SQLException
	{
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();

		for (int i = 1; i <= columnCount; i++)
		{
			if (i > 1) System.out.print(", ");
			System.out.print(metaData.getColumnLabel(i));
		}
		System.out.println();

		while (result.next())
		{
			for (int i = 1; i <= columnCount; i++)
			{
				if (i > 1) System.out.print(", ");
				System.out.print(result.getString(i));
			}
			System.out.println();
		}
	}


	/**
	 * Check if the table exists in the database.
	 * @param tableName table name
	 * @return true if the table already exists, false otherwise.
	 * @throws SQLException
	 */
	public static boolean tableExistsInDB (Connection connection, String tableName) throws SQLException {
		if (connection != null) {
			DatabaseMetaData dM = connection.getMetaData();
			ResultSet rS = dM.getTables(null, null, tableName.toUpperCase(), null);
			if (rS.next()) {
				//System.out.println(tableName + " already exists in the database.");
				return true;
			}
			else {
				//System.out.println(tableName + " does not exist in the database.");
				return false;
			}
		}
		return false;
	}	

}
