package com.company.cities;

import com.company.dao.CityDAO;
import com.company.dao.CountryDAO;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a tool to import data from a real dataset
            new ToolToImport("C:\\Users\\nechi\\Documents\\GitHub\\Programare-Avansata\\Laboratorul 8\\src\\main\\resources\\concap.csv");

            //TODO: print all the countries in Europe
            var countries = new CountryDAO();

            countries.findByContinent("Europe");

            var cities = new CityDAO();

            // Display the distances between various cities in the world
            DistanceCalculator distanceCalculator = new DistanceCalculator();

            City city1 = cities.findByName("Bucharest");
            City city2 = cities.findByName("Paris");

            System.out.println("Display the distances between various cities in the world:");
            System.out.println("Distance from " + city1.getName() + " to " + city2.getName() + " is: " + distanceCalculator.displayDistances(city1, city2));

            Database.getConnection().close();
        } catch (SQLException e) {
            System.err.println(e);
//            com.company.myWorld.Database.rollback();
        }
    }
}
