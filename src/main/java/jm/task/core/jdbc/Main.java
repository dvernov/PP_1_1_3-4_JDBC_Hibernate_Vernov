package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
//import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();
        userDao.saveUser("Vasya1", "Petrov1", (byte) 1);
        userDao.saveUser("Vasya2", "Petrov2", (byte) 2);
        userDao.saveUser("Vasya3", "Petrov3", (byte) 3);
        userDao.saveUser("Vasya3", "Petrov3", (byte) 3); // testing
        userDao.saveUser("Vasya4", "Petrov4", (byte) 4);
        //testing hql
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            session.createQuery("from User where name like '%sya%' and age between 2 and 4")
                    .getResultList()
                    .forEach(System.out::println);
            session.getTransaction().commit();
        }
        //testing hql

//        List<User> usersList = userDao.getAllUsers();
//        for (User user : usersList) {
//            System.out.println(user.toString());
//        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}

