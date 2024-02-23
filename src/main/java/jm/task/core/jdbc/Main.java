package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();

        try (Connection connection = Util.getConnection()) {
            userDao.createUsersTable();
            userDao.saveUser("Vasya1", "Petrov1", (byte) 1);
            userDao.saveUser("Vasya2", "Petrov2", (byte) 2);
            userDao.saveUser("Vasya3", "Petrov3", (byte) 3);
            userDao.saveUser("Vasya4", "Petrov4", (byte) 4);

            List<User> usersList = userDao.getAllUsers();
            for (User user : usersList) {
                System.out.println(user.toString());
            }
            userDao.cleanUsersTable();
            userDao.dropUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
