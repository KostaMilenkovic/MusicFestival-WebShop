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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Festival;
import model.Reservation;
import model.Ticket;

@ManagedBean(name="festivalReservations")
@ViewScoped
public class FestivalReservationsController {
    private Festival festival;
    private List<Reservation> reservations = null;

    public FestivalReservationsController() {
        festival = (Festival)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("festival_reservations");
    }
    
    public void approveReservation(Reservation r) {
        DB.approveReservation(r);
    }
    
    public void cancelReservation(Reservation r) {
        DB.cancelReservation(r);
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public List<Reservation> getReservations() {
        if(reservations == null) {
            reservations = new ArrayList();
            List<Ticket> tmp = new ArrayList(festival.getTicketCollection());
            tmp.forEach((ticket) -> {
                List<Reservation> rtmp = new ArrayList(ticket.getReservationCollection());
                for(Reservation r: rtmp) {
                    if(r.getStatus().compareTo("pending") == 0) {
                        reservations.add(r);
                    }
                }
                
            });
        }
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    
}
