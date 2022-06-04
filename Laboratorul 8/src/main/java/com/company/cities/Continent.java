package com.company.cities;

public class Continent {
    private final String name;
    private int id;

    public Continent(String name) {
        this.name = name;
    }

    public Continent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
