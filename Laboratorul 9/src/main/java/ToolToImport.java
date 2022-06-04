import entity.City;
import entity.Continent;
import entity.Country;
import repository.CityRepository;
import repository.ContinentRepository;
import repository.CountryRepository;
import repository.DataRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ToolToImport {
    public ToolToImport(String absolutePath) {
        DataRepository continents = new ContinentRepository();
        DataRepository countries = new CountryRepository();
        DataRepository cities = new CityRepository();

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

                Continent continent;

                if (continents.findByName(continentName).size() == 0) {
                    continent = new Continent(continentName);
                } else {
                    continent = (Continent) continents.findByName(continentName).get(0);
                }

                continents.save(continent);

                List<Country> countryList = countries.findByName(countryName);

                Country country = null;

                if (countryList.size() == 0) {
                    country = new Country(countryName, countryCode, continent);
                } else {
                    boolean exists = false;

                    for (Country country1 : countryList) {
                        if (country1.getContinent().equals(continent)) {
                            exists = true;
                            country = country1;
                        }
                    }

                    if (!exists) {
                        country = new Country(countryName, countryCode, continent);
                    }
                }

                countries.save(country);

                List<City> cityList = cities.findByName(capitalName);

                City city = null;

                if (cityList.size() == 0) {
                    city = new City(capitalName, country, true, capitalLatitude, capitalLongitude);
                } else {
                    boolean exists = false;

                    for (City city1 : cityList) {
                        if (city1.getCountry().equals(country)) {
                            exists = true;
                            city = city1;
                        }
                    }

                    if (!exists) {
                        city = new City(capitalName, country, true, capitalLatitude, capitalLongitude);
                    }
                }

                cities.save(city);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
