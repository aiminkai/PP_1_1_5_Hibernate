package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Statement staitment = null;
    private Connection connection = null;
    private String newTable = "CREATE TABLE IF NOT EXISTS `myjdbc_test`.`users` (\n" +
            "  `ID` BIGINT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(255) NOT NULL,\n" +
            "  `lastName` VARCHAR(255) NOT NULL,\n" +
            "  `age` INT NOT NULL,\n" +
            "               PRIMARY KEY (`ID`))ENGINE = InnoDB DEFAULT CHARACTER SET = utf8";
    private static String deleteTable = "DROP TABLE IF EXISTS users";
    private static String addUser = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    private static String remUserById = "DELETE FROM users WHERE id = ?";
    private static String getAll = "SELECT * FROM users";
    private static String cleanTable = "TRUNCATE TABLE users";

    {
        try {
            connection = Util.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(newTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(deleteTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pstm = connection.prepareStatement(addUser);) {
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement pstm = connection.prepareStatement(remUserById)) {
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery(getAll)) {
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
