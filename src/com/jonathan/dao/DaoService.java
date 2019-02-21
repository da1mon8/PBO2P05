package com.jonathan.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 1772004 Jonathan Bernad
 */
public interface DaoService<T> {

    int addData(T object) throws SQLException;

    int updateData(T object) throws SQLException;

    int deleteData(T object) throws SQLException;

    List<T> getAllData() throws SQLException;
}
