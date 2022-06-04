package com.company.cities;

import com.company.dao.CityDAO;
import com.company.dao.ContinentDAO;
import com.company.dao.CountryDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Create a tool to import data from a real dataset.
 */
public class ToolToImport {
    public ToolToImport(String absolutePath) {
        var continents = new ContinentDAO();
        var countries = new CountryDAO();
        var cities = new CityDAO();

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(absolutePath));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] tempArr = line.split(",");

                String countryName = tempArr[0];
                String capitalName = tempArr[1];
                double capitalLatitude = Double.parseDouble(tempArr[2]);
                double capitalLongitude = Double.parseDouble(tempArr[3]);
                String countryCode = tempArr[4];
                String continentName = tempArr[5];

                Continent continent = new Continent(continentName);

                if (continents.findByName(continentName) == null) {
                    continents.create(continent);
                }

                Country country = new Country(countryName, countryCode, continentName);

                if (countries.findByName(countryName) == null) {
                    countries.create(country);
                }

                City city = new City(capitalName, countryName, true, capitalLatitude, capitalLongitude);

                if (cities.findByName(capitalName) == null) {
                    cities.create(city);
                }

                Database.getConnection().commit();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
