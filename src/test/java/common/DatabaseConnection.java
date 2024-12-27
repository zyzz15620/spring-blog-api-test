package common;

import model.AccountEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class DatabaseConnection {
    private static SessionFactory sessionFactory =null;

    protected static void setup() {
        // Load settings from hibernate.properties automatically
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(AccountEntity.class) // Add entity class
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            setup();
        }
        return sessionFactory;
    }

//    public static void main(String[] args) {
//        try (SessionFactory factory = getSessionFactory()) {
//            factory.inTransaction(session -> {
//                session.createSelectionQuery("from AccountEntity", AccountEntity.class)
//                        .getResultList()
//                        .forEach(account ->
//                                System.out.println("Account (" + account.getUsername() + ") : " + account.getEmail()));
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
