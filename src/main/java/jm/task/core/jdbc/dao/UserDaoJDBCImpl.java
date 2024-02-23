package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String TABLE_NAME = "users.users2";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NULL, " +
                "last_name VARCHAR(45) NULL, " +
                "age INT(3) NULL, " +
                "PRIMARY KEY (id));";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO " + TABLE_NAME + " (name, last_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM " + TABLE_NAME + " where id = ?";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        String query = "SELECT id, name, last_name, age FROM " + TABLE_NAME;
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                listOfUsers.add(user);
            }
            return listOfUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE " + TABLE_NAME;
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
