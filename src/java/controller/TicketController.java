/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.User;
import db.DB;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.Ticket;

/**
 *
 * @author milenkok
 */
@ViewScoped
@ManagedBean(name="ticketController")
public class TicketController implements Serializable{
    @ManagedProperty(value="#{user}")
    private User user;
    
    private List<Ticket> tickets;
   
  
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ticket> getTickets() {
        tickets = DB.getMyTickets(user.getId());
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    
    
    
    
    
}
