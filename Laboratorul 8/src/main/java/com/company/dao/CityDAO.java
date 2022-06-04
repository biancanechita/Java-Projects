package com.company.dao;

import com.company.cities.City;
import com.company.cities.Database;

import java.sql.*;

public class CityDAO implements Dao<City> {
    @Override
    public void create(City city) throws SQLException {
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement("insert into cities (name, country, capital, latitude, longitude) values (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, city.getName());
            pstmt.setString(2, city.getCountry());
            pstmt.setBoolean(3, city.isCapital());
            pstmt.setDouble(4, city.getLatitude());
            pstmt.setDouble(5, city.getLongitude());
            pstmt.executeUpdate();
        }
    }

    @Override
    public City findByName(String name) throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, country, capital, latitude, longitude from cities where name='" + name + "'")) {
            return rs.next() ? new City(rs.getInt("id"), name, rs.getString("country"), rs.getBoolean("capital"), rs.getDouble("latitude"), rs.getDouble("longitude")) : null;
        }
    }

    @Override
    public City findById(int id) throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, country, capital, latitude, longitude from cities where id=" + id)) {

            return rs.next() ? new City(id, rs.getString("name"), rs.getString("country"), rs.getBoolean("capital"), rs.getDouble("latitude"), rs.getDouble("longitude")) : null;
        }
    }

    @Override
    public void findAll() throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select id, name, country, capital, latitude, longitude from cities")) {
            while (rs.next()) {
                System.out.println(new City(rs.getInt("id"), rs.getString("name"), rs.getString("country"), rs.getBoolean("capital"), rs.getDouble("latitude"), rs.getDouble("longitude")));
            }
        }
    }
}
