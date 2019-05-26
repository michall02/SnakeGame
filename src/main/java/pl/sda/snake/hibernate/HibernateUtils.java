package pl.sda.snake.hibernate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import pl.sda.snake.model.User;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static <T> T getInTransaction(Function<Session, T> action) {

        SessionFactory sessionFactory = getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();

        Transaction transaction = currentSession.beginTransaction();
        T result = null;
        try {
            result = action.apply(currentSession);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            currentSession.close();
        }
        return result;
    }

    private static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
            Map<String, String> properties = new HashMap<>();
            properties.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/snake?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "michal1432");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            registryBuilder.applySettings(properties);
            StandardServiceRegistry serviceRegistry = registryBuilder.build();
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(User.class)
                    .getMetadataBuilder().build();

            sessionFactory =  metadata.getSessionFactoryBuilder().build();
        }
        return sessionFactory;
    }
}
