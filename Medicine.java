package com.pharmacy;

import java.sql.*;
import java.util.Scanner;

public class Medicine {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Pharmacy";

    static final String USER = "root";
    static final String PASS = "harsh2002";

    @SuppressWarnings("resource")
	public static void main(String[] args)throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter the medicine name: ");
                String name = sc.nextLine();
                System.out.println("Enter the medicine company: ");
                String company = sc.nextLine();
                System.out.println("Enter the medicine manufacturing date (YYYY-MM-DD): ");
                String mfgDate = sc.nextLine();
                System.out.println("Enter the medicine expiry date (YYYY-MM-DD): ");
                String expDate = sc.nextLine();
                System.out.println("Enter the quantity");
                int quantity = sc.nextInt();
                System.out.println("Enter rate of medicine");
                int rate = sc.nextInt();

                // Insert data into the table
                System.out.println("Inserting data into table...");
                stmt = conn.createStatement();
                String sql = "INSERT INTO medicine (name, company, mfg_date, exp_date,quantity,rate) " +
                        "VALUES ('" + name + "', '" + company + "', '" + mfgDate + "', '" + expDate + "', '" + quantity + "','" + rate + "' )";
                stmt.executeUpdate(sql);

                System.out.println("Data inserted successfully!");

                System.out.println("Do you want to enter new data? (Y/N)");
                sc.nextLine();
                
                String choice = sc.nextLine();
                if (choice.equalsIgnoreCase("N")){
                    
                	break;
                }
            }

            // Retrieve data from the table
            System.out.println("Retrieving data from the table...");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM medicine";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String company = rs.getString("company");
                String mfgDate = rs.getString("mfg_date");
                String expDate = rs.getString("exp_date");
                int quantity   = rs.getInt("quantity");
                int rate       = rs.getInt("rate");

                System.out.print("Name: " + name);
                System.out.print(", Company: " + company);
                System.out.print(", Manufacturing Date: " + mfgDate);
                System.out.println(", Expiry Date: " + expDate);
                System.out.println("Quantity: "+ quantity);
                System.out.println("Rate: "+ rate);
            }
            rs.close();

            // Count the number of rows in the table
            System.out.println("Count the number of rows in the table");
            stmt = conn.createStatement();
            sql = "SELECT COUNT(*) AS total FROM medicine";
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
