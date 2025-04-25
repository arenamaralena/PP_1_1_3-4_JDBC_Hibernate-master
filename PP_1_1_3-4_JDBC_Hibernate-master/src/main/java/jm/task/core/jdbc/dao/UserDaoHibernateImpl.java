package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    public boolean doQuery(String task) {
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(task).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void createUsersTable() {
        String createTablesSQL = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastName VARCHAR(100), age TINYINT)";
        doQuery(createTablesSQL);
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        doQuery(dropTableSQL);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserInTableSQL = "INSERT INTO users(name, lastName, age) VALUES (:name, :lastName, :age)";
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(saveUserInTableSQL);
            query.setParameter("name", name);
            query.setParameter("lastName", lastName);
            query.setParameter("age", age);
            query.executeUpdate();
            transaction.commit();
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String removeUserFromTableSQL = "DELETE FROM users WHERE id = :id";
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(removeUserFromTableSQL);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        String cleanTableSQL = "DELETE FROM users";
        doQuery(cleanTableSQL);
    }
}
