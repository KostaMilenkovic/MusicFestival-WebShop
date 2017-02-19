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
import model.FestivalRating;

@ManagedBean(name = "homeAdminController")
@ViewScoped
public class HomeAdminController implements Serializable{
    
    public List<Festival> festivalsMostVisited;
    public List<Festival> festivalsMostTicketsBought;
    private List<User> recentTenUsers = null;
    
    public HomeAdminController() {
    
    }

    public List<Festival> getFestivalsMostVisited() {
        return DB.getTopFiveFestivalsVisited();
    }

    public void setFestivalsMostVisited(List<Festival> festivalsMostVisited) {
        this.festivalsMostVisited = festivalsMostVisited;
    }

    public List<Festival> getFestivalsMostTicketsBought() {
        return DB.getTopFiveFestivalsTicketsBought();
//        return DB.getTopFiveFestivals();
    }

    public void setFestivalsMostTicketsBought(List<Festival> festivalsMostTicketsBought) {
        this.festivalsMostTicketsBought = festivalsMostTicketsBought;
    }

    public List<User> getRecentTenUsers() {
        if(recentTenUsers == null) {
            recentTenUsers = DB.getRecentTenUsers();
        }
        return recentTenUsers;
    }

    public void setRecentTenUsers(List<User> recentTenUsers) {
        this.recentTenUsers = recentTenUsers;
    }
}
