package com.napier.sem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Population {

    // JDBC connection to the database
    private Connection con = null;

    public static void main(String[] args) throws IOException {
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


        app.getNpopulatedCountries();

        app.getPopulationInRegion();

        app.getRegionCapitalByPoplution();

        app.getLivingInCitiesVsNot();

        app.getCitiesVsNotLivingInCitiesInEachContinent();


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

                    /*
                    This Version of connection works for me locally - Bart
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");

                     */
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
    }

    /*
    Barts code added below
     */

    public void getNpopulatedCountries() throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("How many most populated countries in the world would you like to see? : ");
        // Reading data using readLine
        String N = reader.readLine();





        try {
            // Define SQL query to retrieve countries in the specified continent sorted by population in descending order
            String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                    "FROM world.country " +
                    "ORDER BY Population DESC "+
                    "LIMIT "+ N ;

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);
            // pstmt.setString(1, ); // Set the continent parameter

            // Execute SQL query and obtain result set
            ResultSet rs = pstmt.executeQuery();

            // Print column headers for clarity
            System.out.printf("%-10s %-45s %-25s %-15s %-10s%n", "Code", "Name", "Region", "Population", "Capital");

            // Loop through the result set and print each record
            while (rs.next()) {
                String code = rs.getString("Code");
                String name = rs.getString("Name");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                // Print each country's details in a formatted table
                System.out.printf("%-10s %-45s %-25s %-15d %-10d%n", code, name, region, population, capital);

                System.out.println();
                System.out.println("*************************************");
            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
    }

    public void getPopulationInRegion() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));


        System.out.print("Which Region from the list would you like to display : ");

        try {

            String baseQuery = "SELECT DISTINCT Region FROM `country`\n" +
                    "ORDER BY Region;";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(baseQuery);
            // pstmt.setString(1, ); // Set the continent parameter

            // Execute SQL query and obtain result set
            ResultSet rs = pstmt.executeQuery();

            // Print column headers for clarity
            System.out.printf("%-15s%n", "");

            // Loop through the result set and print each record
            while (rs.next()) {
                String region = rs.getString("Region");

                // Print each region details in a formatted table
                System.out.printf("%-15s%n", region);
            }

            // Reading data using readLine
            String countryRegion = reader.readLine();

            // Define SQL query to retrieve countries in the specified continent sorted by population in descending order
            String query = "SELECT city.Name, country.Name AS CountryName, country.Continent, country.Region, city.Population, country.Capital FROM `city`" +
                    "INNER JOIN country ON city.CountryCode = country.Code " +
                    "WHERE Country.Region = '"+ countryRegion + "' " +
                    "ORDER BY city.Population DESC LIMIT 100;"
                    ;



            // Prepare the statement to prevent SQL injection
            pstmt = con.prepareStatement(query);
            // pstmt.setString(1, ); // Set the continent parameter

            // Execute SQL query and obtain result set
            rs = pstmt.executeQuery();

            // Print column headers for clarity
            System.out.printf("%-50s %-30s %15s %-10s%n", "Name","Country", "Region", "Population", "Capital");


            // Loop through the result set and print each record
            while (rs.next()) {


                String countryName = rs.getString("CountryName");
                String name = rs.getString("Name");
                String region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                // Print each country's details in a formatted table
                System.out.printf("%-45s %-45s %-25s %-15d %-10d%n" , name, countryName, region, population, capital);

            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();

        }
        System.out.println();
        System.out.println("*************************************");
    }

    /////////////////////////opcja 3 skonczona ///////////////////////////////////
    public void getRegionCapitalByPoplution() throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("View All Capital Cities by Population in a Region : ");

        // Reading data using readLine
        String region = reader.readLine();


        try {
            // Define SQL query to retrieve countries in the specified continent sorted by population in descending order
            String query = "SELECT city.Name, country.Name AS CountryName, country.Continent, country.Region, city.Population, country.Capital FROM `city` "+
                    "INNER JOIN country ON " +
                    "country.Capital = city.ID " +
                    "WHERE country.Region = '"+ region +"' " +
                    "ORDER BY city.Population DESC LIMIT 100; ";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);
            // pstmt.setString(1, ); // Set the continent parameter

            // Execute SQL query and obtain result set
            ResultSet rs = pstmt.executeQuery();

            // Print column headers for clarity
            System.out.printf("%-50s %-30s %15s %-10s%n", "Name","Country", "Region", "Population", "Capital");

            // Loop through the result set and print each record
            while (rs.next()) {
                String countryName = rs.getString("CountryName");
                String name = rs.getString("Name");
                region = rs.getString("Region");
                int population = rs.getInt("Population");
                int capital = rs.getInt("Capital");

                // Print each country's details in a formatted table
                System.out.printf("%-45s %-45s %-25s %-15d %-10d%n" , name, countryName, region, population, capital);

            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("*************************************");
    }


//// 4 TASK View Population Living in Cities vs. Not Living in Cities in Each Region


    public void getLivingInCitiesVsNot() {


        try {

            String query ="SELECT "+
                    "co.Region, "+
                    "SUM(co.Population) AS TotalPopulation, "+
                    "SUM(IFNULL(ci.Population, 0)) AS PopulationInCities, "+
                    "ROUND((SUM(IFNULL(ci.Population, 0)) / SUM(co.Population)) * 100, 2) AS PercentInCities, "+
                    "(SUM(co.Population) - SUM(IFNULL(ci.Population, 0))) AS PopulationNotInCities, "+
                    "ROUND(((SUM(co.Population) - SUM(IFNULL(ci.Population, 0))) / SUM(co.Population)) * 100, 2) AS PercentNotInCities "+
                    "FROM "+
                    "Country co "+
                    "LEFT JOIN "+
                    "City ci ON co.Code = ci.CountryCode "+
                    "GROUP BY "+
                    "co.Region "+
                    "ORDER BY "+
                    "co.Region; ";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);


            // Execute SQL query and obtain result set
            ResultSet rs = pstmt.executeQuery();

            // Print column headers for clarity
            System.out.printf("%-30s %-15s %-20s %-15s %-25s %-10s%n", "Region", "TotalPopulation", "PopulationInCities", "PercentInCities", "PopulationNotInCities" , "PercentNotInCities");

            // Loop through the result set and print each record
            while (rs.next()) {
                String region = rs.getString("Region");
                String totalPopulation = rs.getString("TotalPopulation");
                String populationInCities = rs.getString("PopulationInCities");
                String percentInCities = rs.getString("PercentInCities");
                String populationNotInCities = rs.getString("PopulationNotInCities");
                String percentNotInCities = rs.getString("percentNotInCities");



                // Print each country's details in a formatted table
                System.out.printf("%-30s %-15s %-20s %-15s %-25s %-10s%n", region, totalPopulation, populationInCities, percentInCities, populationNotInCities ,percentNotInCities);


            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("*************************************");
    }

// 5 task Population Living in Cities vs. Not Living in Cities in Each Continent


    public void getCitiesVsNotLivingInCitiesInEachContinent() {



        try {

            String query =
                    "SELECT " +
                            "c.Continent, " +
                            "SUM(c.Population) AS Total_Population, " +
                            "COALESCE(city_pop.Population_In_Cities, 0) AS Population_In_Cities, " +
                            "ROUND( " +
                            "(COALESCE(city_pop.Population_In_Cities, 0) / SUM(c.Population)) * 100, 2 " +
                            ") AS Percentage_In_Cities, (SUM(c.Population) - COALESCE(city_pop.Population_In_Cities, 0)) AS Population_Not_In_Cities,  " +
                            "ROUND(((SUM(c.Population) - COALESCE(city_pop.Population_In_Cities, 0)) / " +
                            "SUM(c.Population)) * 100, 2 ) AS " + " Percentage_Not_In_Cities " +
                            "FROM " +
                            "Country c " +
                            "LEFT JOIN ( " +
                            "SELECT " +
                            "co.Continent, " +
                            "SUM(ci.Population) AS Population_In_Cities " +
                            "FROM " +
                            "City ci " +
                            "INNER JOIN " +
                            "Country co ON ci.CountryCode = co.Code " +
                            "GROUP BY " +
                            "co.Continent " +
                            ") city_pop ON c.Continent = city_pop.Continent " +
                            "GROUP BY " +
                            "c.Continent " +
                            "ORDER BY `Total_Population` DESC;";


            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);


            // Execute SQL query and obtain result set
            ResultSet rs = pstmt.executeQuery();

            // Print column headers for clarity
            System.out.printf("%-30s %-15s %-20s %-15s %-25s %-10s%n", "Continent", "TotalPopulation", "PopulationInCities", "PercentInCities", "PopulationNotInCities" , "PercentNotInCities");

            // Loop through the result set and print each record
            while (rs.next()) {
                String continent = rs.getString("Continent");
                String totalPopulation = rs.getString("Total_Population");
                String PopulationInCities = rs.getString("Population_In_Cities");
                String percentInCities = rs.getString("Percentage_In_Cities");
                String populationNotInCities = rs.getString("Population_Not_In_Cities");
                String percentNotInCities = rs.getString("Percentage_Not_In_Cities");



                // Print each country's details in a formatted table
                System.out.printf("%-30s %-15s %-20s %-15s %-25s %-10s%n", continent, totalPopulation, PopulationInCities, percentInCities, populationNotInCities ,percentNotInCities);
            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
    }


}
