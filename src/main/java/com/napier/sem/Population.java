package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class Population {

    public static void main(String[] args) {
        Population app = new Population();

        // Establish database connection
        app.connect();

        // Generate report for all countries ordered by population
        app.getCountriesByPopulation();

        // Disconnect from the database
        app.disconnect();
    }

    private Connection con = null;

    /**
     * Connects to the MySQL database.
     */
    public void connect() {
        try {
            // Initialize MySQL database connection using JDBC
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
            System.out.println("Connected to the database.");
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    /**
     * Disconnects from the MySQL database.
     */
    public void disconnect() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates and prints a report of all countries ordered by largest to smallest population.
     */
    public void getCountriesByPopulation() {
        try {
            // Define SQL query to retrieve all countries sorted by population in descending order
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM world.country ORDER BY Population DESC";

            // Create a statement object to send SQL query to the database
            Statement stmt = con.createStatement();

            // Execute SQL query and obtain result set
            ResultSet rs = stmt.executeQuery(query);

            // Print column headers for clarity
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Loop through the result set and print each record
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String continent = rs.getString("Continent");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                // Print each country's details in a formatted table
                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
    }
}
