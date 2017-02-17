/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import model.User;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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



public class DB {
    private static Configuration cfg;
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory factory;
    public static User user = null;
    
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
        session.save(resultUser);
        if(!session.getTransaction().wasCommitted())session.getTransaction().commit();
        session.close();
        return true;
    }
    
    public static User login(String username, String password){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findByUsername").setString("username", username);
        User resultUser = (User)query.uniqueResult();
        
        if(resultUser == null){
            session.close();
            return null;
        }
            
        if(!password.equals(resultUser.getPassword()))
            return null;
        session.close();
        user = resultUser;
        setCurrentUser(user);
        return resultUser;
    }
    
    public static void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        user = null;
    }
    
    public static Boolean resetPassword(String username, String oldPassword, String newPassword){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findByUsername").setString("username", username);
        User resultUser = (User)query.uniqueResult();
        
        if(resultUser == null){
            session.close();
            return false;
        }
            
        if(!oldPassword.equals(resultUser.getPassword())){
            session.close();
            return false;
        }
        
        resultUser.setPassword(newPassword);
        
        
        
        session.getTransaction().begin();
        session.save(resultUser);
        if(!session.getTransaction().wasCommitted())session.getTransaction().commit();
        session.close();
        
        return true;
    }
    
    public static Boolean approveUser (Integer id) {
        Session session = factory.openSession();
        User user = getUserById(id);
        user.setApproved(1);
        
        session.getTransaction().begin();
        session.update(user);
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
    
    public static List<Festival> getTopFiveFestivalsTicketsBought(){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findMostTicketsBought");
        query.setMaxResults(5);
        List<Object[]> list = query.list();
        session.close();
        List<Festival> festivals = new ArrayList<>();
        for (Object[] obj : list) {
            Festival festival = new Festival();
            festival.setId((Integer) obj[0]);
            festival.setName((String) obj[1]);
            festival.setPlace((String) obj[2]);
            festival.setStartDate((Date) obj[3]);
            festival.setEndDate((Date) obj[4]);
            festivals.add(festival);
        }
        
        return festivals;
    }
            
    public static List<Festival> getTopFiveFestivalsVisited(){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("Festival.findMostVisited");
        query.setMaxResults(5);
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
        query.setMaxResults(5);
        List<Object[]> list = query.list();
        session.close();
        List<Festival> festivals = new ArrayList<>();
        for (Object[] obj : list) {
            Festival festival = new Festival();
            festival.setId((Integer) obj[0]);
            festival.setName((String) obj[1]);
            festival.setPlace((String) obj[2]);
            festival.setStartDate((Date) obj[3]);
            festival.setEndDate((Date) obj[4]);
            festivals.add(festival);
        }
        
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
    
    public static List<User> getUsers(){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findAll");
        List<User> users = query.list();
        session.close();
        return users;
    }
    
    public static List<User> getUsersByApproved(Integer approved){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findByApproved").setInteger("approved", approved);
        List<User> users = query.list();
        session.close();
        return users;
    }
    
    public static User getUserById(Integer id){
        Session session = factory.openSession();
        Query query = session.getNamedQuery("User.findById").setInteger("id", id);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }
    
    public static UserRole getUserRoleById(Integer userRoleId) {
        Session session = factory.openSession();

        Query query = session.getNamedQuery("UserRole.findById").setInteger("id", userRoleId);
        UserRole userRole = (UserRole)query.uniqueResult();
        
        session.close();
        
        return userRole;
    }
    
    public static void setCurrentUser(User user) {
        ((HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true))).setAttribute("user", user);
    }
    
    public static User getCurrentUser() {
        return (User)((HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(false))).getAttribute("user");
    }

    public User getUser() {
        return user;
    }

    public static void setUser(User user1) {
        user = user1;
    }
}
