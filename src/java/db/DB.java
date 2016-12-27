/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author milenkok
 */
public class DB {
    private static Configuration cfg;
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory factory;
        
    static{
        
    }
    
    public static void persistUser(User user){
        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(User.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        factory = cfg.buildSessionFactory(serviceRegistry);
        
        Session session = factory.openSession();
        session.getTransaction().begin();
        session.save(user);
        if(!session.getTransaction().wasCommitted())session.getTransaction().commit();
        session.close();
    }
        
}
