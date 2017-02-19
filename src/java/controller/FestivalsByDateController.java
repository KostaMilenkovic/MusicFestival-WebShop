/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.Festival;
import model.User;

@ManagedBean(name="festivalsByDate")
@ViewScoped
public class FestivalsByDateController {
    @ManagedProperty(value = "#{user}")
    private User user;
    private static Date date;
    private Date searchDate;
    private List<Festival> festivals;


    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        date = searchDate;
        this.searchDate = searchDate;
    }

    public List<Festival> getFestivals() {
        if(date == null)return null;
        if(festivals == null)festivals = DB.getFestivalsByDate(date);
        return festivals;
    }

    public void setFestivals(List<Festival> festivals) {
        this.festivals = festivals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
