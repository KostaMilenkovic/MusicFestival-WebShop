/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.io.BufferedReader;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import model.Festival;
import model.FestivalPerformer;
import model.Performer;
import model.SocialNetwork;
import model.Ticket;
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
    
    private String name;
    private String location;
    private Date dateStart;
    private Date dateEnd;
    private Integer priceOneDay;
    private Integer priceAllDays;
    private Integer numTicketsPerUser;
    private Integer numTicketsPerDay;
    private String message;
    
    public AdminCreateFestivalController() {
        
    }
    
    public String uploadFileJSON() {
        String res = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONFestivalWrapper jsonFestival = objectMapper.readValue(file.getInputStream(), JSONFestivalWrapper.class);
            
            DB.newFestival( parseFestival(jsonFestival) );
            
            List<JSONPerformer> jsonPerformers = jsonFestival.getFestival().getPerformers();
            for (int i = 0; i < jsonPerformers.size(); i++) {
                DB.newFestivalPerformer( parseFestivalPerformer(jsonPerformers.get(i)));
            }
            message = "Successfully parsed JSON";
            res = "admin_festivals.xhtml";
            mapFestivalVars(festival);
            
        } catch (Exception ex) {
            //PARSE CSV FILE IF objectMapper json failed.
            
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
            DateFormat timerFormat = new SimpleDateFormat("HH:mm:ss");
            festival = new Festival();
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(file.getName())));
                String line; 
                List<String[]> rootNode = new ArrayList<>();
                
                while ((line = br.readLine()) != null) {   
                    // use comma as separator
                    String[] lineNode = line.split(",");
                    rootNode.add(lineNode);
                }
                festival.setName(rootNode.get(1)[0]);
                festival.setPlace(rootNode.get(1)[1]);
                festival.setStartDate( dateFormat.parse(rootNode.get(1)[2]) );
                festival.setEndDate( dateFormat.parse(rootNode.get(1)[3]) );
                festival.setCostDay( Integer.parseInt(rootNode.get(1)[4]) );
                festival.setCostAll( Integer.parseInt(rootNode.get(1)[5]) );
                festival.setUserTicketDay(0);
                festival.setStatus("Open");
                DB.newFestival( festival );
                
            } catch(Exception ex2) {
              //PARSING CSV AND JSON FAILED!
              message = "Parsing failed!";
            }
        }
        return res;
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
            festival.setStatus("initialized");
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
    
    private void mapFestivalVars(Festival fest) {
        name = fest.getName();
        location = fest.getPlace();
        dateStart = fest.getStartDate();
        dateEnd = fest.getEndDate();
        priceOneDay = fest.getCostDay();
        priceAllDays = fest.getCostAll();
        numTicketsPerUser = fest.getUserTicketDay();
        numTicketsPerDay = fest.getNumTicketsDay();
    }
    
    public String checkIfLoggedIn() {   
        String result = "login.xhtml";
        
        User user = DB.getCurrentUser();
        if(user != null) {
            if(user.getRole().getName().compareTo("administrator")==0)
                result = "";
        }
        return result;
    }
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    
    public String newFestival() {
        String result = "admin_festivals.xhtml";
        List<Ticket> tickets = new ArrayList();
        Festival fest = new Festival(name, location, dateStart, dateEnd, priceOneDay, priceAllDays, numTicketsPerUser, numTicketsPerDay, "initialized", 0);
        DB.newFestival(fest);
        long diff = dateEnd.getTime() - dateStart.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        Date tmpDate = dateStart;
        for(int i = 0;i<days+1;i++) {
            Ticket t = new Ticket(fest, "one_day", tmpDate);
            Calendar c = Calendar.getInstance();
            c.setTime(tmpDate);
            c.add(Calendar.DATE, 1);
            tmpDate = c.getTime();
            tickets.add(t);
        }
        tickets.add(new Ticket(fest, "all_days", dateStart));
        DB.createTickets(tickets);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getPriceOneDay() {
        return priceOneDay;
    }

    public void setPriceOneDay(Integer priceOneDay) {
        this.priceOneDay = priceOneDay;
    }

    public Integer getPriceAllDays() {
        return priceAllDays;
    }

    public void setPriceAllDays(Integer priceAllDays) {
        this.priceAllDays = priceAllDays;
    }

    public Integer getNumTicketsPerUser() {
        return numTicketsPerUser;
    }

    public void setNumTicketsPerUser(Integer numTicketsPerUser) {
        this.numTicketsPerUser = numTicketsPerUser;
    }

    public Integer getNumTicketsPerDay() {
        return numTicketsPerDay;
    }

    public void setNumTicketsPerDay(Integer numTicketsPerDay) {
        this.numTicketsPerDay = numTicketsPerDay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    
}