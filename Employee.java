package com.pharmacy;

import java.sql.*;
import java.util.Scanner;

public class Employee {

	    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	    static final String DB_URL = "jdbc:mysql://localhost/Pharmacy";

	    static final String USER = "root";
	    static final String PASS = "harsh2002";

	    public static void main(String[] args) {
	        Connection conn = null;
	        Statement stmt = null;

	        try {
	            Class.forName(JDBC_DRIVER);

	            System.out.println("Connecting to database...");
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            while (true) {
	                Scanner sc = new Scanner(System.in);
	                System.out.println("Enter the employee ID: ");
	                int id = sc.nextInt();
	                sc.nextLine();
	                System.out.println("Enter the employee name: ");
	                String name = sc.nextLine();
	                System.out.println("Enter the employee department: ");
	                String dept = sc.nextLine();
	                System.out.println("Enter the employee salary: ");
	                int salary = sc.nextInt();
	                sc.nextLine();

	                // Insert data into the table
	                System.out.println("Inserting data into table...");
	                stmt = conn.createStatement();
	                String sql = "INSERT INTO Employee " +
	                        "VALUES (" + id + ", '" + name + "', '" + dept + "', " + salary + ")";
	                stmt.executeUpdate(sql);

	                System.out.println("Data inserted successfully!");

	                System.out.println("Do you want to enter new data? (Y/N)");
	                String choice = sc.nextLine();
	                if (choice.equalsIgnoreCase("N")) {
	                    break;
	                }
	            }

	            // Retrieve data from the table
	            System.out.println("Retrieving data from the table...");
	            stmt = conn.createStatement();
	            String sql = "SELECT * FROM Employee";
	            ResultSet rs = stmt.executeQuery(sql);

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String dept = rs.getString("dept");
	                int salary = rs.getInt("salary");

	                System.out.print("ID: " + id);
	                System.out.print(", Name: " + name);
	                System.out.print(", Department: " + dept);
	                System.out.println(", Salary: " + salary);
	            }
	            rs.close();

	            // Count the number of rows in the table
	            stmt = conn.createStatement();
	            sql = "SELECT COUNT(*) AS total FROM Employee";
	            rs = stmt.executeQuery(sql);
	            rs.next();
	            int totalRows = rs.getInt("total");

	            System.out.println("Total rows in the table: " + totalRows);

	            stmt.close();
	            conn.close();
	        } catch (SQLException se) {
	            se.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) stmt.close();
	            } catch (SQLException se2) {
	            }
	            try {
	                if (conn != null) conn.close();
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }
	        }
	    }
	

}
