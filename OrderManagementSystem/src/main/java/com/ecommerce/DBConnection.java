package com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String username = "root";
        String password = "Utkarsh@18";

        return DriverManager.getConnection(url, username, password);
    }
}