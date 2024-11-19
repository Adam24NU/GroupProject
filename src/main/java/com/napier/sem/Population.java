package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Population {

    // JDBC connection to the database
    private Connection con = null;

    public static void main(String[] args) {
        Population app = new Population();

        // Connect to the database
        app.connect(args);

        // Generate report for all countries ordered by population
        app.getCountriesByPopulation();

        // Generate report for all countries in a specific continent
        app.getCountriesByContinentPopulation("Asia");

        // Generate report for all countries in a specific region
        app.getCountriesByRegionPopulation("Western Europe");

        // Generate report for the top N populated countries in the world where N is provided by the user.
        int N = 10; // Replace with user input if needed
        app.getTopNPopulatedCountries(N);

        // Retrieve the top N populated countries in a specified continent
        int X = 5; // Replace with user input if needed
        String continent = "Asia"; // Replace with user input if needed
        app.getTopNPopulatedCountriesByContinent(continent, X);

        // Retrieve the top N populated countries in a specified region
        int Y = 5; // Replace with user input if needed
        String region = "Eastern Europe"; // Replace with user input if needed
        app.getTopNPopulatedCountriesByRegion(region, Y);

        // Retrieve and display all cities in the world, sorted by population
        app.getAllCitiesByPopulation();

        // Retrieve and display all cities in a continent sorted by population
       // app.getCitiesByContinentPopulation("Asia");


        // Disconnect from the database
        app.disconnect();
    }

    /**
     * Establishes a connection to the MySQL database.
     * This method tries multiple times in case the database is starting up.
     */
    public void connect(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Attempt to connect to the database multiple times
        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");

            try {
                // Wait briefly to give the database time to start
                Thread.sleep(5000);

                // Database connection URL based on environment
                if (args.length != 0 && args[0].equals("debug")) {
                    System.out.println("Connected to localhost");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?useSSL=false", "root", "example");
                } else {
                    System.out.println("Connected inside database");
                    con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                }

                System.out.println("Successfully connected");
                break;
            } catch (SQLException | InterruptedException e) {
                System.out.println("Connection attempt failed: " + i);
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the connection to the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }

    /**
     * Retrieves and displays a list of all countries ordered by population from highest to lowest.
     */
    public void getCountriesByPopulation() {
        System.out.println("All the countries in the world organised by largest population to smallest");
        try {
            // SQL query to fetch country data sorted by population
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM world.country ORDER BY Population DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Print table header
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Print each country in the result
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String continent = rs.getString("Continent");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (SQLException e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    /**
     * Retrieves and displays a list of all countries within a specific continent, ordered by population from highest to lowest.
     * @param continent The name of the continent to filter by.
     */
    public void getCountriesByContinentPopulation(String continent) {
        System.out.println("All the countries in a continent organised by largest population to smallest.");
        try {
            // SQL query to fetch country data within a specified continent sorted by population
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM world.country WHERE Continent = ? ORDER BY Population DESC";

            // Prepare statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent);

            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Print each country in the result
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (SQLException e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    /**
     * Retrieves and displays a list of all countries within a specific region, ordered by population from highest to lowest.
     * @param region The name of the region to filter by.
     */
    public void getCountriesByRegionPopulation(String region) {
        System.out.println("All the countries in a region organised by largest population to smallest");
        try {
            // SQL query to fetch country data within a specified region sorted by population
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM world.country WHERE Region = ? ORDER BY Population DESC";

            // Prepare statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region);

            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Print each country in the result
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String continent = rs.getString("Continent");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (SQLException e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    /**
     * Retrieves and displays the top N populated countries in the world.
     * @param N The number of top populated countries to retrieve.
     */
    public void getTopNPopulatedCountries(int N) {
        System.out.println("The top N populated countries in the world where N is provided by the user.");
        try {
            // SQL query to fetch the top N countries by population
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM world.country ORDER BY Population DESC LIMIT ?";

            // Prepare statement and set the limit parameter to N
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, N);

            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Print each country in the result
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String continent = rs.getString("Continent");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (SQLException e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    /**
     * Retrieves and displays the top N populated countries in a specified continent.
     * @param continent The continent for which the report is generated.
     * @param X The number of top populated countries to retrieve.
     */
    public void getTopNPopulatedCountriesByContinent(String continent, int X) {
        System.out.println("The top N populated countries in a continent where N is provided by the user.");
        try {
            // SQL query to fetch the top N countries by population in the specified continent
            String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                    "FROM world.country " +
                    "WHERE Continent = ? " +
                    "ORDER BY Population DESC " +
                    "LIMIT ?";

            // Prepare statement to prevent SQL injection and set the parameters
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent); // Set the continent
            pstmt.setInt(2, X);           // Set the limit

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Print each country in the result
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (SQLException e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    /**
     * Retrieves and displays the top N populated countries in a specified region.
     * @param region The region for which the report is generated.
     * @param Y The number of top populated countries to retrieve.
     */
    public void getTopNPopulatedCountriesByRegion(String region, int Y) {
        System.out.println("The top N populated countries in a region where N is provided by the user.");
        try {
            // SQL query to fetch the top N countries by population in the specified region
            String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                    "FROM world.country " +
                    "WHERE Region = ? " +
                    "ORDER BY Population DESC " +
                    "LIMIT ?";

            // Prepare statement to prevent SQL injection and set the parameters
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region); // Set the region
            pstmt.setInt(2, Y);        // Set the limit

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-10s %-45s %-25s %-30s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Print each country in the result
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String continent = rs.getString("Continent");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                System.out.printf("%-10s %-45s %-25s %-30s %-15d %-10d%n", code, name, continent, region, population, capital);
            }
        } catch (SQLException e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    /**
     * Retrieves and displays all cities in the world, sorted by population in descending order.
     */
    public void getAllCitiesByPopulation() {
        System.out.println("All the cities in the world organised by largest population to smallest.");
        try {
            // SQL query to fetch all cities sorted by population
            String query = "SELECT ID, Name, CountryCode, District, Population " +
                    "FROM world.city " +
                    "ORDER BY Population DESC";

            // Create a statement to execute the query
            PreparedStatement pstmt = con.prepareStatement(query);

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-10s %-45s %-15s %-25s %-15s%n", "ID", "Name", "Country Code", "District", "Population");

            // Print each city in the result
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String district = rs.getString("District");
                int population = rs.getInt("Population");

                System.out.printf("%-10d %-45s %-15s %-25s %-15d%n", id, name, countryCode, district, population);
            }
        } catch (SQLException e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }





}
