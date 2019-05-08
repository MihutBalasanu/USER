
package bankapp;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import utils.HibernateUtil;
import java.util.logging.Logger;

public class Main {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
        } catch (Throwable error) {
            LOGGER.severe("Failed to create sessionFactory object." + error);
            throw new ExceptionInInitializerError(error);
        } finally {
            sessionFactory.close();
        }

    }
}
