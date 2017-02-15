/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import session.User;
import java.util.List;
import model.Festival;
import model.Performer;
import model.Socialnetwork;
import model.Ticket;
import org.hibernate.Query;
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
        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(User.class);
        cfg.addAnnotatedClass(Festival.class);
        cfg.addAnnotatedClass(Ticket.class);
        cfg.addAnnotatedClass(Performer.class);
        cfg.addAnnotatedClass(Socialnetwork.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        factory = cfg.buildSessionFactory(serviceRegistry);
    }
    
    public static Boolean register(User user){
        
        
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findByUserName").setString("userName", user.getUserName());
        User resultUser = (User)query.uniqueResult();
        if(resultUser!=null){
            session.close();
            return false;
        }
        session.getTransaction().begin();
        session.save(user);
        if(!session.getTransaction().wasCommitted())session.getTransaction().commit();
        session.close();
        return true;
    }
    
    public static User login(String username, String password){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findByUserName").setString("userName", username);
        User resultUser = (User)query.uniqueResult();
        
        if(resultUser == null)
            return null;
        if(!password.equals(resultUser.getPassword()))
            return null;
        
        session.close();
        return resultUser;
        
    }
    
    public static Boolean resetPassword(String username, String oldPassword, String newPassword){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findByUserName").setString("userName", username);
        User resultUser = (User)query.uniqueResult();
        
        if(resultUser == null)
            return false;
        if(!oldPassword.equals(resultUser.getPassword()))
            return false;
       
        resultUser.setPassword(newPassword);
        
        
        
        session.getTransaction().begin();
        session.save(resultUser);
        if(!session.getTransaction().wasCommitted())session.getTransaction().commit();
        session.close();
        
        return true;
    }
    
    public static List<Festival> getFestivals(){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findAll");
        List<Festival> festivals = query.list();
        session.close();
        return festivals;
    }
    
    
    public static List<Festival> getTopFiveFestivals(){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findAllDesc");
        query.setMaxResults(5);
        List<Festival> festivals = query.list();
        session.close();
        return festivals;
    }
    
    public static List<Festival> getRecentFiveFestivals(){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findRecent");
        query.setMaxResults(5);
        List<Festival> festivals = query.list();
        session.close();
        return festivals;
    }
    
    public static List<Festival> getFestivalsByPlace(String place){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findByPlace").setString("place", place);
        List<Festival> festivals = query.list();
        session.close();
        return festivals;
    }
    
    public static List<Festival> getFestivalsByName(String name){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findByName").setString("name", name);
        List<Festival> festivals = query.list();
        session.close();
        return festivals;
    }
    
    public static List<Ticket> getMyTickets(Integer id){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Ticket.findByOwnerId").setInteger("ownerId", id);
        List<Ticket> tickets = query.list();
        session.close();
        return tickets;
    }
    
    public static Boolean reserveTicketForFestival(Integer festivalId,String festivalName,Integer ownerId){
        Session session = factory.openSession();

        Query query = session.getNamedQuery("Festival.findById").setInteger("id", festivalId);
        Festival festival = (Festival)query.uniqueResult();
        
        Ticket ticket = new Ticket();
        ticket.setFestivalName(festivalName);
        ticket.setFestivalId(festivalId);
        ticket.setStartDate(festival.getStartDate());
        ticket.setEndDate(festival.getEndDate());
        ticket.setOwnerId(ownerId);
        ticket.setStatus("reserved");
        
        festival.setTicketsDay(festival.getTicketsTotal()-1); //not safe
        
        session.getTransaction().begin();
        session.save(ticket);
        session.save(festival);
        if(!session.getTransaction().wasCommitted())session.getTransaction().commit();
        session.close();
        
        return true;
    }
    
    
    public static Festival getFestivalById(Integer festivalId){
        Session session = factory.openSession();

        Query query = session.getNamedQuery("Festival.findById").setInteger("id", festivalId);
        Festival festival = (Festival)query.uniqueResult();
        
        session.close();
        
        return festival;
    }
        
}
