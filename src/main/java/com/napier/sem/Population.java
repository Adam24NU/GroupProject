package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.concurrent.Callable;

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
        app.getCitiesByContinentPopulation("Asia");

        // Retrieve and display all cities in a region sorted by population
        app.getCitiesByRegionPopulation("Eastern Europe");

        // Retrieve and display all cities in a country sorted by population
        app.getCitiesByCountryPopulation("Ukraine");

        // Retrieve and display all cities in a district sorted by population
        app.getCitiesByDistrictPopulation("California");

        // Retrieve and display the top N populated cities in the world
        int L = 5;
        app.getTopNCitiesByPopulation(L);

        // Retrieve and display the top N populated cities in the continent
        int M = 5; // Replace with user input if needed
        String continent1 = "Asia"; // Replace with user input if needed
        app.getTopNCitiesByContinentPopulation(M, continent1);

        // Retrieve and display the top N populated cities in the region
        int K = 5; // Replace with user input if needed
        String region1 = "Eastern Europe"; // Replace with user input if needed
        app.getTopNCitiesByRegionPopulation(K, region1);

        // Retrieve and display the top N populated cities in the country
        int P = 5;
        String country = "Ukraine";
        app.getTopNCitiesByCountryPopulation(P, country);

        // Retrieve and display the top N populated cities in a district
        int O = 3; // Replace with user input if needed
        String district = "California"; // Replace with user input if needed
        app.getTopNCitiesByDistrictPopulation(O, district);

        // Retrieve and display the capital cities in the world organised by largest population to smallest.
        app.getAllCapitalCitiesByPopulation();

        // Retrieve and display the capital cities in a continent organised by largest population to smallest.
        app.getCapitalCitiesByContinentPopulation(continent);

        // Retrieve and display the capital cities in a region, organized by population from largest to smallest.
        app.getCapitalCitiesByRegionPopulation(region);

        // Retrieve and display the top N populated capital cities in the world where N is provided by the user.
        int G = 5; // You can change this value as needed
        app.getTopNPopulatedCapitalCities(G); // Call the method to get the top N populated capital cities

        // Retrieve and display the top N populated capital cities in a continent where N is provided by the user.
        int T = 10; // You can change this value as needed
        String continent2 = "Asia"; // You can change the continent as needed
        app.getTopNPopulatedCapitalCitiesInContinent(T, continent2); // Call the method to get the top N populated capital cities in the specified continent

        // Retrieve and display the top N populated capital cities in a region where N is provided by the user.
        int I = 10; // You can change this value as needed
        String region2 = "Eastern Europe"; // You can change the continent as needed
        app.getTopNPopulatedCapitalCitiesInRegion(I, region2);



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
            * Getter method for the database connection.
     * This method is added specifically to facilitate unit testing by allowing the connection to be mocked.
            * It returns the current connection to the database.
            *
            * Note to collaborators: This modification was made for testing purposes to mock the database connection.
     */
    public Connection getDatabaseConnection() {
        return con; // Return the current database connection
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
            // SQL query to fetch the top X countries by population in the specified continent
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
            // SQL query to fetch the top Y countries by population in the specified region
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

    /**
     * Retrieves and displays all cities in a specified continent, sorted by population in descending order.
     * @param continent The name of the continent to filter by.
     */
    public void getCitiesByContinentPopulation(String continent) {
        System.out.println("All the cities in a continent organised by largest population to smallest.");
        try {
            // SQL query to fetch cities in the specified continent sorted by population
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.CountryCode = co.Code " +
                    "WHERE co.Continent = ? " +
                    "ORDER BY c.Population DESC";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the continent parameter
            pstmt.setString(1, continent);

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

    /**
     * Retrieves and displays all cities in a specified region, sorted by population in descending order.
     * @param region The name of the region to filter by.
     */
    public void getCitiesByRegionPopulation(String region) {
        System.out.println("All the cities in a region organised by largest population to smallest.");
        try {
            // SQL query to fetch cities in the specified region sorted by population
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.CountryCode = co.Code " +
                    "WHERE co.Region = ? " +
                    "ORDER BY c.Population DESC";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the region parameter
            pstmt.setString(1, region);

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

    /**
     * Retrieves and displays all cities in a specified country, sorted by population in descending order.
     * @param country The name of the country to filter by.
     */
    public void getCitiesByCountryPopulation(String country) {
        System.out.println("All the cities in a country organised by largest population to smallest.");
        try {
            // SQL query to fetch cities in the specified country sorted by population
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.CountryCode = co.Code " +
                    "WHERE co.Name = ? " +
                    "ORDER BY c.Population DESC";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the country parameter
            pstmt.setString(1, country);

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

    /**
     * Retrieves and displays all cities in a specified district, sorted by population in descending order.
     * @param district The name of the district to filter by.
     */
    public void getCitiesByDistrictPopulation(String district) {
        System.out.println("All the cities in a district organised by largest population to smallest.");
        try {
            // SQL query to fetch cities in the specified district sorted by population
            String query = "SELECT ID, Name, CountryCode, District, Population " +
                    "FROM world.city " +
                    "WHERE District = ? " +
                    "ORDER BY Population DESC";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the district parameter
            pstmt.setString(1, district);

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-10s %-45s %-15s %-25s %-15s%n", "ID", "Name", "Country Code", "District", "Population");

            // Print each city in the result
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String districtName = rs.getString("District");
                int population = rs.getInt("Population");

                System.out.printf("%-10d %-45s %-15s %-25s %-15d%n", id, name, countryCode, districtName, population);
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
     * Retrieves and displays the top N populated cities in the world, where N is provided as a command-line argument.
     * @param L The number of top populated cities to retrieve.
     */
    public void getTopNCitiesByPopulation(int L) {
        System.out.println("The top N populated cities in the world where N is provided by the user.");
        try {
            // SQL query to fetch the top L cities by population
            String query = "SELECT ID, Name, CountryCode, District, Population " +
                    "FROM world.city " +
                    "ORDER BY Population DESC " +
                    "LIMIT ?";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the L parameter
            pstmt.setInt(1, L);

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

    /**
     * Retrieves and displays the top N populated cities in a specified continent, where N is provided by the user.
     * @param M The number of top populated cities to retrieve.
     * @param continent1 The name of the continent to filter cities.
     */
    public void getTopNCitiesByContinentPopulation(int M, String continent1) {
        System.out.println("The top N populated cities in a continent where N is provided by the user.");
        try {
            // SQL query to fetch the top N cities in a specific continent by population
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.CountryCode = co.Code " +
                    "WHERE co.Continent = ? " +
                    "ORDER BY c.Population DESC " +
                    "LIMIT ?";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the parameters for the query
            pstmt.setString(1, continent1);
            pstmt.setInt(2, M);

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
    /**
     * Retrieves and displays the top K populated cities in a specified region, where N is provided by the user.
     * @param K The number of top cities to retrieve.
     * @param region1 The name of the region to filter cities.
     */
    public void getTopNCitiesByRegionPopulation(int K, String region1) {
        System.out.println("The top N populated cities in a region where N is provided by the user.");
        try {
            // SQL query to fetch the top K cities in a specific region by population
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.CountryCode = co.Code " +
                    "WHERE co.Region = ? " +
                    "ORDER BY c.Population DESC " +
                    "LIMIT ?";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the parameters for the query
            pstmt.setString(1, region1);
            pstmt.setInt(2, K);

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

    /**
     * Retrieves and displays the top N populated cities in a specific country, where N is provided by the user.
     * @param P The number of top cities to retrieve.
     * @param country The name of the country to filter cities.
     */
    public void getTopNCitiesByCountryPopulation(int P, String country) {
        System.out.println("The top N populated cities in a country where N is provided by the user.");
        try {
            // SQL query to fetch the top P cities in a specific country by population
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.CountryCode = co.Code " +
                    "WHERE co.Name = ? " +
                    "ORDER BY c.Population DESC " +
                    "LIMIT ?";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the parameters for the query
            pstmt.setString(1, country);
            pstmt.setInt(2, P);

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

    /**
     * Retrieves and displays the top N populated cities in a specific district, where N is provided by the user.
     * @param O The number of top cities to retrieve.
     * @param district The name of the district to filter cities.
     */
    public void getTopNCitiesByDistrictPopulation(int O, String district) {
        System.out.println("The top N populated cities in a district where N is provided by the user.");
        try {
            // SQL query to fetch the top O cities in a specific district by population
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "WHERE c.District = ? " +
                    "ORDER BY c.Population DESC " +
                    "LIMIT ?";

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the parameters for the query
            pstmt.setString(1, district);
            pstmt.setInt(2, O);

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-10s %-45s %-15s %-25s %-15s%n", "ID", "Name", "Country Code", "District", "Population");

            // Print each city in the result
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String cityDistrict = rs.getString("District");
                int population = rs.getInt("Population");

                System.out.printf("%-10d %-45s %-15s %-25s %-15d%n", id, name, countryCode, cityDistrict, population);
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
     * Retrieves and displays all the capital cities in the world organized by population from largest to smallest.
     */
    public void getAllCapitalCitiesByPopulation() {
        System.out.println("All the capital cities in the world organized by largest population to smallest.");
        try {
            // SQL query to fetch all capital cities ordered by population descending
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.ID = co.Capital " +
                    "ORDER BY c.Population DESC";

            // Prepare the statement
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


    /**
     * This method retrieves and displays all the capital cities in a specified continent.
     * @param continent The name of the continent to filter capital cities by.
     */

    public void getCapitalCitiesByContinentPopulation(String continent) {
        System.out.println("All the capital cities in a continent organised by largest population to smallest.");
        try {
            // SQL query to fetch capital cities in a specified continent ordered by population in descending order
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.ID = co.Capital " + // Join to get the capital city
                    "WHERE co.Continent = ? " + // Filter by continent
                    "ORDER BY c.Population DESC"; // Order by population in descending order

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the continent parameter
            pstmt.setString(1, continent);

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-10s %-45s %-15s %-25s %-15s%n", "ID", "Name", "Country Code", "District", "Population");

            // Print each capital city in the result
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

    /**
     * Retrieves and displays all the capital cities in a specified region, organized by largest population to smallest.
     * @param region The name of the region to filter capital cities.
     */
    public void getCapitalCitiesByRegionPopulation(String region) {
        System.out.println("All the capital cities in a region, organized by population from largest to smallest.");
        try {
            // SQL query to fetch capital cities in a specific region, ordered by population descending
            String query = "SELECT c.ID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.ID = co.Capital " +  // Join on Capital field to get capital cities
                    "WHERE co.Region = ? " +  // Filter by region
                    "ORDER BY c.Population DESC";  // Order by population in descending order

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the region parameter
            pstmt.setString(1, region);

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-10s %-45s %-15s %-25s %-15s%n", "ID", "Name", "Country Code", "District", "Population");

            // Print each capital city in the result
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


    /**
     * Retrieves and displays the top N populated capital cities in the world.
     * @param G The number of top capital cities to retrieve.
     */
    public void getTopNPopulatedCapitalCities(int G) {
        System.out.println("The top N populated capital cities in the world where N is provided by the user.");

        try {
            // SQL query to fetch the top N capital cities ordered by population
            String query = "SELECT c.Name, c.CountryCode, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.ID = co.Capital " +  // Join on the Capital field in country
                    "ORDER BY c.Population DESC " +  // Order by population in descending order
                    "LIMIT ?";  // Limit the result to N cities

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the limit for N capital cities
            pstmt.setInt(1, G);

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-45s %-15s %-15s%n", "Name", "Country Code", "Population");

            // Print each capital city in the result
            while (rs.next()) {
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                int population = rs.getInt("Population");

                System.out.printf("%-45s %-15s %-15d%n", name, countryCode, population);
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
     * Retrieves and displays the top N populated capital cities in a continent.
     * @param T The number of top capital cities to retrieve.
     * @param continent2 The continent for which to retrieve capital cities.
     */
    public void getTopNPopulatedCapitalCitiesInContinent(int T, String continent2) {
        System.out.println("The top " + T + " populated capital cities in the continent of " + continent2 + ".");

        try {
            // SQL query to fetch the top N capital cities in a specific continent ordered by population
            String query = "SELECT c.Name, c.CountryCode, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.ID = co.Capital " +  // Join on the Capital field in country
                    "WHERE co.Continent = ? " +  // Filter by continent
                    "ORDER BY c.Population DESC " +  // Order by population in descending order
                    "LIMIT ?";  // Limit the result to N cities

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the parameters for the query
            pstmt.setString(1, continent2); // Set the continent
            pstmt.setInt(2, T); // Set the number of top capital cities to retrieve

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-45s %-15s %-15s%n", "Name", "Country Code", "Population");

            // Print each capital city in the result
            while (rs.next()) {
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                int population = rs.getInt("Population");

                System.out.printf("%-45s %-15s %-15d%n", name, countryCode, population);
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
     *
     * @param I The number of top capital cities to retrieve based on population.
     * @param region2 The region in which to search for capital cities.
     */
    public void getTopNPopulatedCapitalCitiesInRegion(int I, String region2) {
        System.out.println("The top " + I + " populated capital cities in the region of " + region2 + ".");

        try {
            // SQL query to fetch the top N capital cities in a specific region ordered by population
            String query = "SELECT c.Name, c.CountryCode, c.Population " +
                    "FROM world.city c " +
                    "JOIN world.country co ON c.ID = co.Capital " +  // Join on the Capital field in country
                    "WHERE co.Region = ? " +  // Filter by region
                    "ORDER BY c.Population DESC " +  // Order by population in descending order
                    "LIMIT ?";  // Limit the result to N cities

            // Prepare the statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set the parameters for the query
            pstmt.setString(1, region2); // Set the region
            pstmt.setInt(2, I); // Set the number of top capital cities to retrieve

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Print table headers
            System.out.printf("%-45s %-15s %-15s%n", "Name", "Country Code", "Population");

            // Print each capital city in the result
            while (rs.next()) {
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                int population = rs.getInt("Population");

                System.out.printf("%-45s %-15s %-15d%n", name, countryCode, population);
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

