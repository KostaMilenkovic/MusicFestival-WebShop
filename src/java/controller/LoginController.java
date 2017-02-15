/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import session.User;
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

        String username = user.getUserName();
        String password = user.getPassword();
        
        if(username.equals("") || password.equals("")){
            message = "Enter username and password";
            return null;
        }
        
        boolean success = user.login(username,password);
        
        
        if(!success){
            message = "Failed to login";
            return null;
        }
        
        message = "Successfully logged in";
        
        switch(user.getRole()){
            case 1: {
                if(user.getValid()==0)
                    return "home_unregistered";
                else
                    return "home_registered";
            }
            case 2: return "home_admin";
            default: return "";
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
