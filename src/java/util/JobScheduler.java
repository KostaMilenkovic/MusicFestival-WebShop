/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import db.DB;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Festival;
import model.Reservation;
import model.Ticket;

class ReportGenerator extends TimerTask{

    @Override
    public void run() {
        List<Reservation> reservations = DB.getAllPendingReservations();
        for(Reservation reservation : reservations){
            long diff = reservation.getDate().getTime() - (new Date()).getTime();
            int daysPassed = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if(daysPassed >= 2){
                DB.cancelReservation(reservation);
                DB.addUserBlockAttempts(reservation.getUser());
            }
        }
        List<Festival> festivals = DB.getFestivalsByStatus("active");
        for(Festival festival : festivals){
            if(festival.getEndDate().before(new Date())){
                DB.finishFestival(festival);
                for(Ticket ticket : festival.getTicketCollection()){
                    for(Reservation reservation : ticket.getReservationCollection()){
                        if(reservation.getStatus().equals("active"))
                            DB.finishReservation(reservation);
                    }
                }
            }
            
        }
        
        
        
    }
    
}

@ManagedBean(name = "jobScheduler")
@ApplicationScoped
public class JobScheduler {
    
    public JobScheduler(){
        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();
        date.set(
          Calendar.DAY_OF_WEEK,
          Calendar.SUNDAY
        );
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        // 
        timer.schedule(
          new ReportGenerator(), //job
          date.getTime(), //starting date
          1000 * 60 * 60 * 24 * 1 //every day
        );
    }
}







