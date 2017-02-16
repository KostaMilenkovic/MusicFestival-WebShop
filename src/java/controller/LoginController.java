/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.User;
import db.DB;
import static db.DB.setCurrentUser;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.UserRole;

@SessionScoped
@ManagedBean(name = "loginController")
public class LoginController implements Serializable {
    private User user;
    private String username;
    private String password;
    
    private String message;
    //==========================================================================
    
    
    public String login(){

        
        if(username.equals("") || password.equals("")){
            message = "Please enter a username and a password";
            return null;
        }
        
        user = DB.login(username,password);
        DB.setCurrentUser(user);
        
        if(user == null){
            message = "Failed to log in.";
            return null;
        }
        
        message = "Successfully logged in!";
        
        
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
        user = new User();
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

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  
    
    
    
}
