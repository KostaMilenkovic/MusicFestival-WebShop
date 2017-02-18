/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Festival;
import model.User;


@ManagedBean(name = "adminCreateFestival")
@ViewScoped
public class AdminCreateFestivalController implements Serializable {
    private String name;
    private String location;
    private Date dateStart;
    private Date dateEnd;
    private Integer priceOneDay;
    private Integer priceAllDays;
    private Integer numTicketsPerUser;
    private Integer numTicketsPerDay;
    
    public AdminCreateFestivalController() {
        
    }
    
    public String checkIfLoggedIn() {   
        String result = "login.xhtml";
        
        User user = DB.getCurrentUser();
        if(user != null) {
            if(user.getRole().getName().compareTo("administrator")==0)
                result = "";
        }
        
        return result;
    }
    
    public String newFestival() {
        String result = "admin_festivals.xhtml";
        Festival festival = new Festival(name, location, dateStart, dateEnd, priceOneDay, priceAllDays, numTicketsPerUser, numTicketsPerDay, "initialized", 0);
        DB.newFestival(festival);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getPriceOneDay() {
        return priceOneDay;
    }

    public void setPriceOneDay(Integer priceOneDay) {
        this.priceOneDay = priceOneDay;
    }

    public Integer getPriceAllDays() {
        return priceAllDays;
    }

    public void setPriceAllDays(Integer priceAllDays) {
        this.priceAllDays = priceAllDays;
    }

    public Integer getNumTicketsPerUser() {
        return numTicketsPerUser;
    }

    public void setNumTicketsPerUser(Integer numTicketsPerUser) {
        this.numTicketsPerUser = numTicketsPerUser;
    }

    public Integer getNumTicketsPerDay() {
        return numTicketsPerDay;
    }

    public void setNumTicketsPerDay(Integer numTicketsPerDay) {
        this.numTicketsPerDay = numTicketsPerDay;
    }
    
    
    
}