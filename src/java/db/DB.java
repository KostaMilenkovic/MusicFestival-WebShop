/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import model.User;
import java.util.List;
import model.Festival;
import model.FestivalComment;
import model.FestivalPerformer;
import model.FestivalRating;
import model.Performer;
import model.Reservation;
import model.SocialNetwork;
import model.Ticket;
import model.UserRole;
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
        
    static {
        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(User.class);
        cfg.addAnnotatedClass(UserRole.class);
        cfg.addAnnotatedClass(Festival.class);
        cfg.addAnnotatedClass(FestivalRating.class);
        cfg.addAnnotatedClass(FestivalComment.class);
        cfg.addAnnotatedClass(FestivalPerformer.class);
        cfg.addAnnotatedClass(Ticket.class);
        cfg.addAnnotatedClass(Performer.class);
        cfg.addAnnotatedClass(Reservation.class);
        cfg.addAnnotatedClass(SocialNetwork.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        factory = cfg.buildSessionFactory(serviceRegistry);
    }
    
    public static Boolean register(User user) {
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findByUsername").setString("username", user.getUsername());
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
        Query query = session.getNamedQuery("User.findByUsername").setString("username", username);
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
        Query query = session.getNamedQuery("User.findByUsername").setString("username", username);
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
    
    public static List<Festival> getTopFiveFestivalsByRating(){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findTopFiveByRating");
        List<Festival> festival = query.list();
        session.close();
        return festival;
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
    
    public static List<FestivalRating> getFestivalRatingsByFestival(Integer id){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("FestivalRating.findByFestival").setInteger("festival", id);
        List<FestivalRating> festivalRating = query.list();
        session.close();
        return festivalRating;
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
        
        return true;
    }
    
    
    public static Festival getFestivalById(Integer festivalId){
        Session session = factory.openSession();

        Query query = session.getNamedQuery("Festival.findById").setInteger("id", festivalId);
        Festival festival = (Festival)query.uniqueResult();
        
        session.close();
        
        return festival;
    }
    
    public static UserRole getUserRoleById(Integer userRoleId) {
        Session session = factory.openSession();

        Query query = session.getNamedQuery("UserRole.findById").setInteger("id", userRoleId);
        UserRole userRole = (UserRole)query.uniqueResult();
        
        session.close();
        
        return userRole;
    }
}
