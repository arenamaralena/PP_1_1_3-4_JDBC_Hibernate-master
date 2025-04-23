package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public boolean doQuery(String task) {
        Util util = new Util();
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(task);
            return true;
        } catch (SQLException e) {
            System.out.println("Соединение потеряно...");
            return false;
        }
    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastName VARCHAR(100), age TINYINT)";
        doQuery(createTableSQL);
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        doQuery(dropTableSQL);
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserInTableSQL = "INSERT users(name, lastName, age) VALUES ('%s', '%s', %d)".formatted(name, lastName, age);
        if (doQuery(saveUserInTableSQL)) {
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        }
    }

    public void removeUserById(long id) {
        String removeUserFromTableSQL = "DELETE FROM users WHERE Id = %d".formatted(id);
        doQuery(removeUserFromTableSQL);
    }

    public List<User> getAllUsers() {
        Util util = new Util();
        List<User> usersList = new ArrayList<>();
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = (byte) resultSet.getInt(4);
                usersList.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            System.out.println("Соединение потеряно...");
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String cleanTableSQL = "DELETE FROM users";
        doQuery(cleanTableSQL);
    }
}
