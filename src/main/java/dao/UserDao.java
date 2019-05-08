package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import javax.persistence.Query;


public class UserDao extends GenericDao<User>{

    public User findByUsernameAndPassword(String username, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User where username = :username AND password = :password ");
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) ((org.hibernate.query.Query) query).uniqueResult();

        transaction.commit();
        session.close();
        return user;
    }
}
