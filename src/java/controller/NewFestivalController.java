/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.User;


@ManagedBean(name = "newFestivalController")
@ViewScoped
public class NewFestivalController implements Serializable {
    
    
    public NewFestivalController() {
        
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
    
}
