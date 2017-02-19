/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Festival;
import model.Reservation;
import model.User;

@ManagedBean(name="registeredUserFestivals")
@ViewScoped
public class RegisteredUserFestivalsController {
    private List<Festival> festivals = null;
    @ManagedProperty(value = "#{user}")
    private User user;
    
    public List<Festival> getFestivals() {
        if(festivals == null) {
            festivals = new ArrayList();
            List<Reservation> rs = (List<Reservation>) user.getReservationCollection();
            for(Reservation r: rs) {
                if(r.getStatus().compareTo("finished") == 0) {
                    boolean has = false;
                    for(Festival f: festivals) {
                        if(f.getId() == r.getTicket().getFestival().getId()) 
                            has=true;
                    }
                    if(!has)
                        festivals.add(r.getTicket().getFestival());
                }
            }
        }
        return festivals;
    }
    
    public String showFestival(Festival f) {
        ((HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true))).setAttribute("registered_festival", f);
        return "registered_single_festival.xhtml";
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
