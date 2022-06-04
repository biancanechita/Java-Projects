package com.company.dao;

import com.company.cities.Country;
import com.company.cities.Database;

import java.sql.*;

public class CountryDAO implements Dao<Country> {
    @Override
    public void create(Country country) throws SQLException {
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement("insert into countries (name, code, continent) values (?, ?, ?)")) {
            pstmt.setString(1, country.getName());
            pstmt.setString(2, country.getCode());
            pstmt.setString(3, country.getContinent());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Country findByName(String name) throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, code, continent from countries where name='" + name + "'")) {
            return rs.next() ? new Country(rs.getInt("id"), name, rs.getString("code"), rs.getString("continent")) : null;
        }
    }

    @Override
    public Country findById(int id) throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select name, europeId, continent from countries where id=" + id)) {
            return rs.next() ? new Country(id, rs.getString("name"), rs.getString("code"), rs.getString("continent")) : null;
        }
    }

    @Override
    public void findAll() throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select id, name, europeId, continent from countries")) {
            while (rs.next()) {
                System.out.println(new Country(rs.getInt("id"), rs.getString("name"), rs.getString("code"), rs.getString("continent")));
            }
        }
    }

    public void findByContinent(String continent) throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select id, name, code from countries where continent='" + continent + "'")) {
            while (rs.next()) {
                System.out.println(new Country(rs.getInt("id"), rs.getString("name"), rs.getString("code"), continent));
            }
        }

        System.out.println();
    }
}
