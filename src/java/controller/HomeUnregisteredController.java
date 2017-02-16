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
import model.Festival;

/**
 *
 * @author milenkok
 */
@ManagedBean(name = "homeUnregisteredController")
@ViewScoped
public class HomeUnregisteredController implements Serializable{
    @ManagedProperty(value = "#{user}")
    private User user;
    
    private String name;
    private String place;
    private Date startDate;
    private Date endDate;
    
    
    private List<Festival> festivals;
    private List<Festival> top5;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    
        
    
    public void setPlace(String place) {
        this.place = place;
    }

    public List<Festival> getFestivals() {
        if(festivals == null) festivals = DB.getFestivals();
        return festivals;
    }

    public void setFestivals(List<Festival> festivals) {
        this.festivals = festivals;
    }

    public List<Festival> getTop5() {
        if(top5==null)top5 = DB.getTopFiveFestivals();
        return top5;
    }

    public void setTop5(List<Festival> top5) {
        this.top5 = top5;
    }
        
    
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
        
        return;
        
    }
    
    
}
