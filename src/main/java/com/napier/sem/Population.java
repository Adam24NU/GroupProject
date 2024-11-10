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
        app.start();

        // Disconnect from the database
        app.disconnect();
    }

    private static Connection con = null;

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


    public static void start()
    {
        Scanner keyboard = new Scanner(System.in);//scanner
        //display of the menu options to user 1/2/3/4/5
        System.out.println();
        System.out.println("Choose one of the following options : " );
        System.out.println();
        System.out.println("1 : View top N populated countries");
        System.out.println("2 : View Cities by Population in a Region");
        System.out.println("3 : View All Capital Cities by Population in a Region");
        System.out.println("4 : View Population Living in Cities vs. Not Living in Cities in Each Region");
        System.out.println("5 : View Population Living in Cities vs. Not Living in Cities in Each Continent");
        System.out.println();
        //entry window for user to choose from options
        int entry = keyboard.nextInt();

// 3 switches
        switch(entry){
            case 1:
                getNpopulatedCountries();
                break;
            case 2:
                getPopulationInRegion();
                break;
            case 3:
                getRegionCapitalByPoplution();
                break;
            case 4:
                System.out.println("this is OPTION 3" + entry);//parameter passing
                break;
            case 5:
                System.out.println("this is OPTION 3" + entry);//parameter passing
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }




    public static void getNpopulatedCountries() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many most populated countries in the world would you like to see? : ");
        String N = scanner.nextLine();


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
            }
        } catch (Exception e) {
            System.out.println("Query failed!");
            e.printStackTrace();
        }
    }

    public static void getPopulationInRegion() {
        Scanner scanner = new Scanner(System.in);
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


            String countryRegion = scanner.nextLine();
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
    }

/////////////////////////opcja 3 skonczona ///////////////////////////////////
    public static void getRegionCapitalByPoplution() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("View All Capital Cities by Population in a Region : ");
        String region = scanner.nextLine();


        try {
            // Define SQL query to retrieve countries in the specified continent sorted by population in descending order
            String query = "SELECT city.Name, country.Name AS CountryName, country.Continent, country.Region, city.Population, country.Capital FROM `city` "+
            "INNER JOIN country ON " +
            "country.Capital = city.ID " +
            "WHERE country.Region = 'Western Europe' " +
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
    }

}
