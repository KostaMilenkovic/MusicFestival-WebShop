/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.User;
import db.DB;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Festival;

@ViewScoped
@ManagedBean(name = "singleFestivalController")
public class SingleFestivalController implements Serializable{
    @ManagedProperty(value = "#{user}")
    private User user;
    private Festival festival;

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public Festival getFestival() {
        Integer festivalId = (Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("festivalId");
        festival = DB.getFestivalById(festivalId);
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
    
  
    
    
}
