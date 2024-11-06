package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner; // Import the Scanner class for user input

public class Population {
    // jdbc:mysql://localhost:3306
    public static void main(String[] args) {
        Population app = new Population();


        // Establish database connection
        app.connect(args);

        // Generate report for all countries ordered by population
        app.getCountriesByPopulation();

        // Generate report for all countries by continent
       // app.getCountriesByContinentPopulation();

        // Disconnect from the database
        app.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String[] args)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(5000);
                // Connect to database
                if (args.length!=0 && args[0].equals("debug")){
                    System.out.println("Connected to localhost");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?useSSL=false", "root", "example");
            } else {
                    System.out.println("Connected inside database");
                    con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");

                }

                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }



    public void getCountriesByPopulation() {
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



    // Generates and prints a report of all countries in a specific continent ordered by largest to smallest population.

    /**
     *
     */
   /* public void getCountriesByContinentPopulation() {
        /*Scanner scanner = new Scanner(System.in);
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
    } */
}
