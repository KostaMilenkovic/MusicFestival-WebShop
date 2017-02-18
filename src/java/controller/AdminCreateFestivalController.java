/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import model.Festival;
import model.FestivalPerformer;
import model.Performer;
import model.SocialNetwork;
import model.User;
import model.json.JSONFestival;
import model.json.JSONFestivalWrapper;
import model.json.JSONPerformer;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;



@ManagedBean(name = "adminCreateFestival")
@ViewScoped
public class AdminCreateFestivalController implements Serializable {
    Part file;
    Festival festival;
    
    public AdminCreateFestivalController() {
        
    }
    
    public void uploadFileJSON() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONFestivalWrapper jsonFestival = objectMapper.readValue(file.getInputStream(), JSONFestivalWrapper.class);
            
            DB.newFestival( parseFestival(jsonFestival) );
            
            List<JSONPerformer> jsonPerformers = jsonFestival.getFestival().getPerformers();
            for (int i = 0; i < jsonPerformers.size(); i++) {
                DB.newFestivalPerformer( parseFestivalPerformer(jsonPerformers.get(i)));
            }
            
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private Festival parseFestival(JSONFestivalWrapper jsonFestival) {
        festival = new Festival();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);    
        try {    
            festival.setName(jsonFestival.getFestival().getName());
            festival.setPlace(jsonFestival.getFestival().getPlace());
            festival.setStartDate( dateFormat.parse(jsonFestival.getFestival().getStartDate()) );
            festival.setEndDate( dateFormat.parse(jsonFestival.getFestival().getEndDate()) );
            festival.setCostDay( Integer.parseInt(jsonFestival.getFestival().getTickets().get(0)) );
            festival.setCostAll( Integer.parseInt(jsonFestival.getFestival().getTickets().get(1)) );
            festival.setUserTicketDay(0);
            festival.setStatus("Open");
        } catch (Exception ex) {
            
        }
        return festival;
    }
    
    private FestivalPerformer parseFestivalPerformer(JSONPerformer jsonPerformer) {
        
        Performer performer = new Performer();
        FestivalPerformer festivalPerformer = new FestivalPerformer();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
        DateFormat timerFormat = new SimpleDateFormat("HH:mm:ss");

        performer.setName(jsonPerformer.getPerformer());
        performer = DB.newPerformer(performer);
        try {    
            festivalPerformer.setFestival(festival);
            festivalPerformer.setPerformer(performer);
            festivalPerformer.setStartDate( dateFormat.parse(jsonPerformer.getStartDate()) );
            festivalPerformer.setStartTime( timerFormat.parse(jsonPerformer.getStartTime()) );
            festivalPerformer.setEndDate( dateFormat.parse(jsonPerformer.getEndDate()) );
            festivalPerformer.setEndTime( timerFormat.parse(jsonPerformer.getEndTime()) );
            DB.newFestivalPerformer(festivalPerformer);
        } catch (Exception ex) {
            
        }
        return festivalPerformer;
    }

    
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    
}
