/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.io.Serializable;
import java.sql.Time;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import model.Festival;
import model.FestivalPerformer;
import model.Performer;

@ManagedBean(name = "festivalEdit")
@ViewScoped
public class FestivalEditController implements Serializable {
    private Festival festival;
    
    private List<Performer> performers = null;
    private Integer selectedPerformer;
    private Date performerDateTimeStart;
    private Date performerDateTimeEnd;
    
    public FestivalEditController() {
        festival = (Festival)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("festival_to_edit");
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
    
    public void newEvent() {
        DateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        Date dateStart;
        Date timeStart;
        Date dateEnd;
        Date timeEnd;
        try {
            dateStart = formatterDate.parse(formatterDate.format(performerDateTimeStart));
            timeStart = formatterTime.parse(formatterTime.format(performerDateTimeStart));
            dateEnd = formatterDate.parse(formatterDate.format(performerDateTimeEnd));
            timeEnd = formatterTime.parse(formatterTime.format(performerDateTimeEnd));
            Performer p = DB.getPerformerById(selectedPerformer);
            FestivalPerformer fp = new FestivalPerformer(festival, p, dateStart, timeStart, dateEnd, timeEnd);
            DB.createEvent(fp);
        } catch (ParseException ex) {
            Logger.getLogger(FestivalEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<SelectItem> getPerformers() {
        if(performers == null) {
            performers = DB.getAllPerformers();
        }
        List<SelectItem> items = new ArrayList<>();
        for(Performer performer: performers){
            items.add(new SelectItem(performer.getId(), performer.getName()));
        }
        return items;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public Integer getSelectedPerformer() {
        return selectedPerformer;
    }

    public void setSelectedPerformer(Integer selectedPerformer) {
        this.selectedPerformer = selectedPerformer;
    }

    public Date getPerformerDateTimeStart() {
        return performerDateTimeStart;
    }

    public void setPerformerDateTimeStart(Date performerDateTimeStart) {
        this.performerDateTimeStart = performerDateTimeStart;
    }

    public Date getPerformerDateTimeEnd() {
        return performerDateTimeEnd;
    }

    public void setPerformerDateTimeEnd(Date performerDateTimeEnd) {
        this.performerDateTimeEnd = performerDateTimeEnd;
    }

    
    
    
}
