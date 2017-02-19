/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.User;
import db.DB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Festival;
import model.Reservation;
import model.UserReport;



@ViewScoped
@ManagedBean(name = "homeRegisteredController")
public class HomeRegisteredController implements Serializable{
    
    @ManagedProperty(value = "#{user}")
    private User user;
    private List<UserReport> userReports = null;
    private String name;
    private String place;
    private Date startDate;
    private Date endDate;
    private String message;
    
    private List<Festival> festivals = null;
    
    
    public HomeRegisteredController() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Festival> getFestivals() {
        if(festivals==null)festivals = DB.getRecentFiveFestivals();
        return festivals;
    }

    public void setFestivals(List<Festival> festivals) {
        this.festivals = festivals;
    }

    public void getTop5() {
        festivals = DB.getTopFiveFestivalsByRating();
    }

    public void getRecent5() {
        festivals =  DB.getRecentFiveFestivals();
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    
    
    //==========================================================================
    

    //==========================================================================
    
    
      
    public void search(){
        festivals = DB.getFestivals();
        List<Festival> removedFestivals = new ArrayList<Festival>();
        
        for (Festival festival : festivals) {
            if (!"".equals(name) && !festival.getName().equals(name) ||
                !"".equals(place) && !festival.getPlace().equals(place) ||
                startDate != null && endDate != null && !( festival.getStartDate().after(startDate) && festival.getEndDate().before(endDate))) 
                
                removedFestivals.add(festival);
        }
        
        festivals.removeAll(removedFestivals);
    }
    
    public String showFestival(Integer festivalId){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("festivalId", festivalId);
        return "festival";
    }
    
    public String cancelReservation(Reservation reservation){
        DB.cancelReservation(reservation, user);
        return "home_registered";
    }

    public List<UserReport> getUserReports() {
        if(userReports == null) {
            userReports = new ArrayList(user.getUserReportCollection());
        }
        return userReports;
    }

    public void setUserReports(List<UserReport> userReports) {
        this.userReports = userReports;
    }
    
    
}
