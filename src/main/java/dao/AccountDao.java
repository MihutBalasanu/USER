package dao;

import model.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class AccountDao extends GenericDao<Account> {

        public List<Account> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM model.Account ");

        List<Account> list = query.getResultList();

        transaction.commit();
        session.close();
        return list;
    }

}
