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
import model.json.JSONSocialNetwork;
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
            //TRY TO PARSE JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JSONFestivalWrapper jsonFestival = objectMapper.readValue(file.getInputStream(), JSONFestivalWrapper.class);
            
            DB.newFestival( parseFestival(jsonFestival) );
            
            List<JSONPerformer> jsonPerformers = jsonFestival.getFestival().getPerformers();
            for (int i = 0; i < jsonPerformers.size(); i++) {
                DB.newFestivalPerformer( parseFestivalPerformer(jsonPerformers.get(i)));
            }
            
            List<JSONSocialNetwork> jsonSocialNetworks = jsonFestival.getFestival().getSocialNetworks();
            for (int i = 0; i < jsonSocialNetworks.size(); i++) {
                DB.newSocialNetwork(parseSocialNetwork(jsonSocialNetworks.get(i)));
            }
            
            message = "Successfully parsed JSON";
            res = "admin_festivals.xhtml";
            mapFestivalVars(festival);
            
        } catch (Exception ex) {
            //PARSE CSV FILE IF PARSING JSON FAILED.
            parseCSV();
        }
        return res;
    }
    
    private void parseCSV() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        BufferedReader br = null;
        String line;
        String[] fields;
        festival = new Festival();
        
        try {
        br = new BufferedReader(new FileReader("C:\\Users\\Nikola\\Documents\\NetBeansProjects\\PIA\\src\\java\\controller\\data.csv"));
            
            br.readLine();
            line = br.readLine();
            fields = line.split("\"");
            festival.setName(fields[1]);
            festival.setPlace(fields[3]);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            festival.setStartDate(df.parse(fields[5]));
            festival.setEndDate(df.parse(fields[7]));
            
            
            br.readLine();
            line = br.readLine();
            fields = line.split("\"");
            
            festival.setCostDay( Integer.parseInt(fields[3]));
            line = br.readLine();
            fields = line.split("\"");
            festival.setCostAll( Integer.parseInt(fields[3]) );
            festival.setUserTicketDay(0);
            festival.setStatus("initialized");
            
            DB.newFestival(festival);
            
            br.readLine();
            line = br.readLine();
            fields = line.split("\"");
            while (!fields[1].equals("Social Network")) {
                Performer performer = new Performer();
                FestivalPerformer festivalPerformer = new FestivalPerformer();
                
                performer.setName(fields[1]);
                festivalPerformer.setStartDate( dateFormat.parse(fields[3]) );
                festivalPerformer.setStartTime( timeFormat.parse(fields[7]) );
                festivalPerformer.setEndDate( dateFormat.parse(fields[5]) );
                festivalPerformer.setEndTime( timeFormat.parse(fields[9]) );
                festivalPerformer.setFestival(festival);
                
                DB.newPerformer(performer);
                DB.newFestivalPerformer(festivalPerformer);
                
                line = br.readLine();
                fields = line.split("\"");
            }
            
            while ((line = br.readLine()) != null) {
                fields = line.split("\"");
                SocialNetwork socialNetwork = new SocialNetwork();
                socialNetwork.setName(fields[1]);
                socialNetwork.setLink(fields[3]);
                socialNetwork.setFestival(festival);
            }
        } catch (Exception ex) {
            
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
    
    private SocialNetwork parseSocialNetwork (JSONSocialNetwork jsonSocialNetwork) {
        SocialNetwork socialNetwork = new SocialNetwork();
        
        socialNetwork.setFestival(festival);
        socialNetwork.setLink( jsonSocialNetwork.getLink() );
        socialNetwork.setName( jsonSocialNetwork.getName() );
        
        return socialNetwork;
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