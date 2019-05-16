package dao;

import model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.HibernateUtil;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class AccountDao extends GenericDao<Account> {

        @Autowired
        private SessionFactory sessionFactory;

        public Session getCurrentSession() {
                return sessionFactory.getCurrentSession();
        }


        public Account findByAccountNumber(String accountNumber){
                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction transaction = session.beginTransaction();

                Query query = session.createQuery("from Account where accountNumber = :accountNumber");
                query.setParameter("accountNumber", accountNumber);
                Account account = (Account) ((org.hibernate.query.Query) query).uniqueResult();

                transaction.commit();
                return account;
        }

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
