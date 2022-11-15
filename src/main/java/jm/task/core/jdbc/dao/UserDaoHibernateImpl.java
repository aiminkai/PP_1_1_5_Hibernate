package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
//import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserDaoHibernateImpl implements UserDao {

    private String newTable = "CREATE TABLE IF NOT EXISTS `myjdbc_test`.`users` (\n" +
            "  `ID` BIGINT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(255) NOT NULL,\n" +
            "  `lastName` VARCHAR(255) NOT NULL,\n" +
            "  `age` INT NOT NULL,\n" +
            "               PRIMARY KEY (`ID`))ENGINE = InnoDB DEFAULT CHARACTER SET = utf8";
    private static String deleteTable = "DROP TABLE IF EXISTS users";
    private static String remUserById = "DELETE FROM users WHERE id = :paramName";
    private static String cleanTable = "TRUNCATE TABLE users";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(newTable);
            query.executeUpdate();
            transaction.commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(deleteTable).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User student = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(remUserById);
            query.setParameter("paramName", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(cleanTable).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
