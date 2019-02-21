package com.jonathan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1772004 Jonathan Bernad
 */
public class ConnUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/pbo1772004";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
