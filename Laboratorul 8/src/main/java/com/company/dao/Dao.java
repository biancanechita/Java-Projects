package com.company.dao;

import java.sql.SQLException;

public interface Dao<T> {
    void create(T t) throws SQLException;

    <T> T findByName(String name) throws SQLException;

    <T> T findById(int id) throws SQLException;

    void findAll() throws SQLException;
}
