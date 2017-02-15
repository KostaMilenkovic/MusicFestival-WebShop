/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import session.User;
import db.DB;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Festival;



/**
 *
 * @author milenkok
 */
@ViewScoped
@ManagedBean(name = "homeRegisteredController")
public class HomeRegisteredController implements Serializable{
    
    @ManagedProperty(value = "#{user}")
    private User user;
    
    private String name;
    private String place;
    
    private List<Festival> festivals = null;
    private List<Festival> top5 = null;
    private List<Festival> recent5 = null;
    
    /**
     * Creates a new instance of FestivalController
     */
    public HomeRegisteredController() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Festival> getFestivals() {
        if(festivals==null)festivals = DB.getFestivals();
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

    public List<Festival> getRecent5() {
        if(recent5==null)recent5 = DB.getRecentFiveFestivals();
        return recent5;
    }

    public void setRecent5(List<Festival> recent5) {
        this.recent5 = recent5;
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
    
    //==========================================================================
    

    //==========================================================================
    
    
      
    public void searchExtended(){
        if(!name.equals("")){
            festivals = DB.getFestivalsByName(name);
            return;
        }
        if(!place.equals("")){
            festivals = DB.getFestivalsByPlace(place);
            return;
        }
        festivals = DB.getFestivals();
        return;
    }
    
    public String showFestival(Integer festivalId){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("festivalId", festivalId);
        return "festival";
    }
    
    
}
