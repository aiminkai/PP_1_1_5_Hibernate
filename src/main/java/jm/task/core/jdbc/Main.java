package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userDao = new UserServiceImpl();

        try {
            userDao.createUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            userDao.saveUser("Name1", "LastName1", (byte) 20);
            userDao.saveUser("Name2", "LastName22", (byte) 25);
            userDao.saveUser("Name3", "LastName3", (byte) 31);
            userDao.saveUser("Name4", "LastName4", (byte) 38);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            userDao.removeUserById(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            userDao.cleanUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            userDao.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            userDao.dropUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
