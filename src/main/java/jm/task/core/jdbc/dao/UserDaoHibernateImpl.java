package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String TABLE_NAME = "users.users2";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        String query = """
                CREATE TABLE IF NOT EXISTS %s
                (id INT NOT NULL AUTO_INCREMENT,
                name VARCHAR(45) NULL,
                last_name VARCHAR(45) NULL,
                age INT(3) NULL,
                PRIMARY KEY (id));
                """.formatted(TABLE_NAME);

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropUsersTable() {
        String query = """
                DROP TABLE IF EXISTS %s;
                """.formatted(TABLE_NAME);

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        String preQuery = """
                INSERT INTO %s (name, last_name, age) VALUES (?, ?, ?)
                """.formatted(TABLE_NAME);

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(preQuery)
                    .setParameter(1, name)
                    .setParameter(2, lastName)
                    .setParameter(3, age)
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        List<User> listOfUsers;
        String query = """
                SELECT * FROM %s""".formatted(TABLE_NAME);
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            listOfUsers = session.createNativeQuery(query, User.class).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return listOfUsers;
    }

    @Override
    public void cleanUsersTable() {
        String query = """
                TRUNCATE TABLE %s""".formatted(TABLE_NAME);
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
