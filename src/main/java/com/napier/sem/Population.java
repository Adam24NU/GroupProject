package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Population {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/world"; // Connecting to the database
        String user = "root";
        String password = "";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            // Call method to display countries sorted by population
            getCountriesByPopulation(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all countries sorted by population
    public static void getCountriesByPopulation(Connection con) {
        String sql = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM world.country " +
                "ORDER BY Population DESC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Display column headers
            System.out.printf("%-5s %-45s %-15s %-25s %-15s %-10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");

            // Loop through results and print each country’s details
            while (rs.next()) {
                System.out.printf("%-5s %-45s %-15s %-25s %-15d %-10d%n",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getInt("Capital"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}