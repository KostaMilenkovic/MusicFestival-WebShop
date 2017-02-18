/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Festival;


@ManagedBean(name="adminFestivals")
@ViewScoped
public class AdminFestivalsController {
    private List<Festival> festivals = null;
    
    public List<Festival> getInitializedFestivals() {
        List<Festival> result = null;
        result = DB.getInitializedFestivals();
        return result;
    }

    public List<Festival> getFestivals() {
        if(festivals == null) {
            festivals = getInitializedFestivals();
        } 
        return festivals;
    }

    public void setFestivals(List<Festival> festivals) {
        this.festivals = festivals;
    }
    
    public String editFestival(Festival festival) {
        String result = "festival_edit.xhtml";
        ((HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true))).setAttribute("festival_to_edit", festival);
        return result;
    }
    
    
    public String showPerformers(Festival festival) {
        String result = "festival_performers.xhtml";
        ((HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true))).setAttribute("festival_performers", festival);
        return result;
    }
}
