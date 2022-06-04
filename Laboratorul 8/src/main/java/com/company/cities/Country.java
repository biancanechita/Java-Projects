package com.company.cities;

public class Country {
    private final String continent;
    private final String code;
    private final String name;
    private int id;

    public Country(String name, String code, String continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public Country(int id, String name, String code, String continent) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getContinent() {
        return continent;
    }

    @Override
    public String toString() {
        return name+ " {" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", continent='" + continent + '\'' +
                '}';
    }
}
