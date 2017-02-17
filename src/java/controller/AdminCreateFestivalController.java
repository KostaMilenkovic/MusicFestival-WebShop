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
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;



@ManagedBean(name = "adminCreateFestival")
@ViewScoped
public class AdminCreateFestivalController implements Serializable {
    Part file;
    
    public AdminCreateFestivalController() {
        
    }
    
    public void uploadFileJSON() {
        try {
            Festival festival = new Festival();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readValue(file.getInputStream(), JsonNode.class);
            
            festival.setName(rootNode.findValues("Name").get(0).asText());
            festival.setPlace(rootNode.findValues("Place").get(0).asText());
            
            String date = rootNode.findValues("StartDate").get(0).asText();
            festival.setStartDate( dateFormat.parse(date));
            
            date = rootNode.findValues("EndDate").get(0).asText();
            festival.setEndDate( dateFormat.parse(date));
            
            festival.setCostDay( rootNode.findValues("Tickets").get(0).get(0).asInt());
            festival.setCostAll( rootNode.findValues("Tickets").get(0).get(1).asInt());
            
            System.out.print(rootNode.findValues("Festival").get(0).findValues("Name").get(0));
            
    
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
