/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Festival;
import model.FestivalPerformer;
import model.Performer;
import model.Reservation;
import model.Ticket;
import model.UserReport;

@ManagedBean(name="festivalPerformerEdit")
@ViewScoped
public class FestivalPerformerEditController {
    private FestivalPerformer fp;
    private Date dateTimeStart;
    private Date dateTimeEnd;
    boolean dateChanged = false;

    public FestivalPerformerEditController() {
        fp = (FestivalPerformer)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("festival_performer_to_edit");
    }
    
    public String editFestivalPerformer() {
        DateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        Date dateStart;
        Date timeStart;
        Date dateEnd;
        Date timeEnd;
        try {
            dateStart = formatterDate.parse(formatterDate.format(dateTimeStart));
            timeStart = formatterTime.parse(formatterTime.format(dateTimeStart));
            dateEnd = formatterDate.parse(formatterDate.format(dateTimeEnd));
            timeEnd = formatterTime.parse(formatterTime.format(dateTimeEnd));
            if((dateStart != fp.getStartDate())||(timeStart != fp.getStartTime())||(dateEnd != fp.getEndDate())||(timeEnd != fp.getEndTime())) {
                List<UserReport> userReports = new ArrayList();
                List<Ticket> tickets = (List<Ticket>) fp.getFestival().getTicketCollection();
                for(Ticket ticket: tickets) {
                    List<Reservation> reservations = (List<Reservation>) ticket.getReservationCollection();
                    for(Reservation reservation: reservations) {
                        if(reservation.getStatus().compareTo("active")==0) {
                            userReports.add(new UserReport(reservation.getUser(), "Festival named '"+fp.getFestival().getName()+"' changed it's timing schedule."));
                        }
                    }
                }
                DB.createUserReports(userReports);
            }    
            fp.setStartDate(dateStart);
            fp.setStartTime(timeStart);
            fp.setEndDate(dateEnd);
            fp.setEndTime(timeEnd);
            DB.updateFestivalPerformer(fp);
        } catch (ParseException ex) {
            Logger.getLogger(FestivalEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dateChanged = false;
        return "festival_performers.xhtml";
    }
    
    public FestivalPerformer getFp() {
        return fp;
    }

    public void setFp(FestivalPerformer fp) {
        this.fp = fp;
    }

    public Date getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(Date dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
        dateChanged = true;
    }

    public Date getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(Date dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
        dateChanged = true;
    }
    
    
}
