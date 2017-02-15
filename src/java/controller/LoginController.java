/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.User;
import db.DB;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author milenkok
 */
@ViewScoped
@ManagedBean(name = "loginController")
public class LoginController implements Serializable{
    
    @ManagedProperty(value = "#{user}")
    private User user;
    
    
    private String message;
    //==========================================================================
   
    
    
    public String login(){

        String username = user.getUsername();
        String password = user.getPassword();
        
        if(username.equals("") || password.equals("")){
            message = "Enter username and password";
            return null;
        }
        
        User success = DB.login(username,password);
        
        
        if(success == null){
            message = "Failed to login";
            return null;
        }
        
        message = "Successfully logged in";
        
        switch(user.getRole().getName()){
            case "administrator":
                return "home_admin";
            case "registered": 
                return "home_registered";
            default: 
                return "home_unregistered";
        }
    }
    
    
    
  

    
    //==========================================================================
    
    public LoginController(){
        this.user = new User();
        
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        
        this.message = message;
    }

  
    
    
    
}
