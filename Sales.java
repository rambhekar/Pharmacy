package com.pharmacy;
import java.sql.*;
import java.sql.Connection;
import java.util.Scanner;

public class Sales {
	
public static void main(String[] args) throws ClassNotFoundException, SQLException{
	
	        // Database connection details
	        String url = "jdbc:mysql://localhost:3306/Pharmacy";
	        String username = "root";
	        String password = "harsh2002";
	        
	        // Customer and medicine details
	        Scanner sc = new Scanner(System.in);
	        System.out.println("Enter Coustemer Name");
	        String customerName = sc.next();
	        System.out.println(" Enter medicine name");
	        String medicineName = sc.next();
	        System.out.println("Enter quantity required");
	        int quantity = sc.nextInt();
	        
	        // JDBC objects
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            // Connect to the database
	            conn = DriverManager.getConnection(url, username, password);
	            
	            // Insert customer and medicine into sale table
	            String insertSaleQuery = "INSERT INTO sales (customer_name, medicine_name) VALUES (?, ?)";
	            PreparedStatement pstmt = conn.prepareStatement(insertSaleQuery);
	            pstmt.setString(1, customerName);
	            pstmt.setString(2, medicineName);
	            pstmt.executeUpdate();
	            
	            // Retrieve medicine details from medicine table
	            String selectMedicineQuery = "SELECT * FROM medicine WHERE name=?";//medicine_name
	            pstmt = conn.prepareStatement(selectMedicineQuery);
	            pstmt.setString(1, medicineName);
	            rs = pstmt.executeQuery();
	            
	            // Check if medicine is available and update quantity and billing
	            if (rs.next()) {
	                int availableQuantity = rs.getInt("quantity");
	                if (quantity > availableQuantity) {
	                    System.out.println("Medicine is not available in required quantity.");
	                } else {
	                    int newQuantity = availableQuantity - quantity;
	                    double rate = rs.getDouble("rate");
	                    double billingAmount = quantity * rate;
	                    String updateMedicineQuery = "UPDATE medicine SET quantity=? WHERE name=?";
	                    pstmt = conn.prepareStatement(updateMedicineQuery);
	                    pstmt.setInt(1, newQuantity);
	                    pstmt.setString(2, medicineName);
	                    pstmt.executeUpdate();
	                    
	                    // Insert billing amount into sale table
	                    String updateSaleQuery = "UPDATE sales SET quantity_required=?, rate=?, amount=? WHERE customer_name=? AND medicine_name=?";
	                    pstmt = conn.prepareStatement(updateSaleQuery);
	                    pstmt.setInt(1, quantity);
	                    pstmt.setDouble(2, rate);
	                    pstmt.setDouble(3, billingAmount);
	                    pstmt.setString(4, customerName);
	                    pstmt.setString(5, medicineName);
	                    pstmt.executeUpdate();
	                    
	                    System.out.println("Medicine purchased successfully!");
	                    System.out.println("Thank's.....");
	                }
	            } else {
	                System.out.println("Medicine not found in the database.");
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        } finally {
	            // Close JDBC objects
	            try { rs.close(); } catch (Exception e) {}
	            try { stmt.close(); } catch (Exception e) {}
	            try { conn.close(); } catch (Exception e) {}
	        }
	    }
	

	
}

