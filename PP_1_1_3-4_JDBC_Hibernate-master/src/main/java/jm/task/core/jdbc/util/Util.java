package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            System.err.println("Не получилось загрузить класс драйвера.");
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Не произошла работа коннекшена.");
        }
        return connection;
    }
    // реализуйте настройку соеденения с БД
}
