package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner; // Import the Scanner class for user input

public class Population {

    public static void main(String[] args) {
        Population app = new Population();

        // Establish database connection
        app.connect();

        // Generate report for all countries ordered by population
        // app.getCountriesByPopulation();

        // Generate report for all countries by continent
        app.getCountriesByContinentPopulation();

        // Disconnect from the database
        app.disconnect();
    }

    private Connection con = null;

    public void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
            System.out.println("Connected to the database.");
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

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

  /*  public void getCountriesByPopulation() {
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM world.country ORDER BY Population DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String continent = rs.getString("Continent");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
    }
  */


    // Generates and prints a report of all countries in a specific continent ordered by largest to smallest population.


    public void getCountriesByContinentPopulation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the continent name: ");
        String continent = scanner.nextLine();

        try {
            // Define SQL query to retrieve countries in the specified continent sorted by population in descending order
            String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                    "FROM world.country " +
                    "WHERE Continent = ? " +
                    "ORDER BY Population DESC";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent); // Set the continent parameter

            // Execute SQL query and obtain result set
            ResultSet rs = pstmt.executeQuery();

            // Print column headers for clarity
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Loop through the result set and print each record
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
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
