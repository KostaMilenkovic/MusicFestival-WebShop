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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Festival;
import model.FestivalPerformer;
import model.Performer;

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
