package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "login_info";
        String databaseUser = "grayson";
        String databasePassword = "Mommy101!";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }

        catch (Exception e) {
            System.out.println("Underlying exception: " + e.getCause());
        }

        return databaseLink;
    }
}