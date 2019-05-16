package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDao extends GenericDao<User>{

    @Autowired
    private SessionFactory sessionFactory;


    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public User findByUsernameAndPassword(String username, String password){
//        Session session = HibernateUtil.getSessionFactory().openSession();
        Session session = sessionFactory.openSession();
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
