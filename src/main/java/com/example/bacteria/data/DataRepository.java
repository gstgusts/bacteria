package com.example.bacteria.data;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class DataRepository {
    private static SessionFactory factory;

    public DataRepository() {
        try {
            factory = new Configuration().
                    configure().
                    addAnnotatedClass(Bacteria.class).
                    addAnnotatedClass(Category.class).
                    addAnnotatedClass(Limitation.class).
                    addAnnotatedClass(Product.class).
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Iterable<Product> getProducts() {
        var session = factory.openSession();

        try {
            return session.createQuery("FROM Product").list();
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }

    public Product getProduct(int id) {
        var session = factory.openSession();

        try {
            return session.get(Product.class, id);
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return null;
    }

    public Iterable<Limitation> getCategoryLimitations(int categoryId) {
        var session = factory.openSession();

        try {
            var sql = "FROM Limitation where category_id = :catId";
            var query = session.createQuery(sql);
            query.setParameter("catId", categoryId);
            var result = query.list();
            return result;
        } catch (HibernateException exception) {
            System.err.println(exception);
        } finally {
            session.close();
        }

        return new ArrayList<>();
    }
}
