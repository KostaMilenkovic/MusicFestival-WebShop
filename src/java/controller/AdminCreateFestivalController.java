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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@ManagedBean(name = "adminCreateFestival")
@ViewScoped
public class AdminCreateFestivalController implements Serializable {
    Part file;
    
    public AdminCreateFestivalController() {
        
    }
    
    public void uploadFileJSON() {
        Festival festival = new Festival();
        List<Performer> performers = new ArrayList<>();
        List<FestivalPerformer> festivalPerformers = new ArrayList<>();
        List<SocialNetwork> socialNetworks = new ArrayList<>(); 
        
        try {
            JSONParser parser = new JSONParser();
            JSONObject rootObj = (JSONObject) parser.parse(new InputStreamReader(file.getInputStream()));
            
            JSONObject objFestival = (JSONObject) rootObj.get("Festival");
            festival.setName(objFestival.get("Name").toString());
            festival.setPlace(objFestival.get("Place").toString());
            festival.setStartDate( (Date) objFestival.get("StartDate"));
            festival.setEndDate( (Date) objFestival.get("EndDate"));
            
            JSONArray objTickets = (JSONArray) objFestival.get("Tickets");
            festival.setCostDay((Integer)objTickets.get(0));
            festival.setCostAll((Integer)objTickets.get(1));
            
            JSONArray objPerformers = (JSONArray) objFestival.get("PerformersList");
            for (int i = 0; i < objPerformers.size(); i++) {
                JSONObject objPerformer = (JSONObject) objPerformers.get(i);
                Performer performer = new Performer();
                FestivalPerformer festivalPerformer = new FestivalPerformer();
                
                performer.setName(objPerformer.get("Name").toString());
                
                festivalPerformer.setStartDate( (Date) objPerformer.get("StartDate"));
                festivalPerformer.setEndDate( (Date) objPerformer.get("EndDate"));
                festivalPerformer.setStartTime( (Date) objPerformer.get("StartTime"));
                festivalPerformer.setEndTime( (Date) objPerformer.get("EndTime"));
                festivalPerformer.setFestival(festival);
                festivalPerformer.setPerformer(performer);
                
                performers.add(performer);
                festivalPerformers.add(festivalPerformer);
            }
            
            JSONArray objSocialNetworks = (JSONArray) objFestival.get("SocialNetworks");
            for (int i = 0; i < objPerformers.size(); i++) {
                JSONObject objSocialNetwork = (JSONObject) objPerformers.get(i);
                SocialNetwork socialNetwork = new SocialNetwork();
                
                socialNetwork.setName( objSocialNetwork.get("Name").toString() );
                socialNetwork.setLink( objSocialNetwork.get("Link").toString() );
                socialNetwork.setFestival(festival);
                
                socialNetworks.add(socialNetwork);
            }
            
        } catch (Exception ex) {
            
        } 
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    
}
