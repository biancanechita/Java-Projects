package com.company.cities;

public class City {
    private final String name;
    private final String country;
    private final double latitude;
    private final double longitude;
    private int id;
    private boolean isCapital = true;

    public City(String name, String country, Boolean isCapital, double latitude, double longitude) {
        this.name = name;
        this.country = country;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City(int id, String name, String country, Boolean isCapital, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
