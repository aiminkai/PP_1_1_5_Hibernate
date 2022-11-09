package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.NonRegisteringDriver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/myjdbc_test";
    private static final String NAME = "root";
    private static final String PASSWORD = "zaq1!QAZ";
    private static Connection connection = null;

    public static Connection getConnection() throws IOException {
        //Properties props = getProps();
        try {
            Driver driver = new NonRegisteringDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            //
        } catch (SQLException e) {
            System.err.println("не удалось соединиться");
        }
        return connection;
    }


}
