package com.company.dao;

import com.company.cities.Continent;
import com.company.cities.Database;

import java.sql.*;

public class ContinentDAO implements Dao<Continent> {
    @Override
    public void create(Continent continent) throws SQLException {
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement("insert into continents (name) values (?)")) {
            pstmt.setString(1, continent.getName());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Continent findByName(String name) throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select id from continents where name='" + name + "'")) {
            return rs.next() ? new Continent(rs.getInt("id"), name) : null;
        }
    }

    @Override
    public Continent findById(int id) throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select name from continents where id=" + id)) {
            return rs.next() ? new Continent(id, rs.getString("name")) : null;
        }
    }

    @Override
    public void findAll() throws SQLException {
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select id, name from continents")) {
            while (rs.next()) {
                System.out.println(new Continent(rs.getInt("id"), rs.getString("name")));
            }
        }
    }
}
