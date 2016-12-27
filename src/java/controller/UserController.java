/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.User;
import db.DB;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author milenkok
 */
@SessionScoped
@ManagedBean(name = "usercontroller")
public class UserController implements Serializable{
    
    private User user;
    private String message;
    
    //==========================================================================
    
    public String login(){
        if(user.getUserName().equals("kosta") && user.getPassword().equals("milenko")){
            
            user.setUserName("kosta");
            user.setPassword("milenko");
            message = "Successfully logged in";
            return "home";
        }
            
        
        message = "Failed to login";
        return "login";
    }
    
    public String register(){
        if(user.getFirstName().equals("")){
            message = "First name field is required";
            return "register";
        }
        if(user.getLastName().equals("")){
            message = "Last name field is required";
            return "register";
        }
        if(user.getUserName().equals("")){
            message = "User name field is required";
            return "register";
        }
        if(user.getPassword().equals("")){
            message = "Password field is required";
            return "register";
        }
        if(user.getConfirmPassword().equals("")){
            message = "Confirm Password field is required";
            return "register";
        }
        if(user.getPhone().equals("")){
            message = "Phone field is required";
            return "register";
        }
        if(user.getEmail().equals("")){
            message = "Email field is required";
            return "register";
        }
        if(!user.getPassword().equals(user.getConfirmPassword())){
            message = "Password field does not match confirm password field";
            return "register";
        }
        if(user.getPassword().length()<6 || user.getPassword().length()>12){
            message = "Password length must be between 6 and 12";
            return "register";
        }
        String password = user.getPassword();
        int upperCount = 0;
        int lowerCount = 0;
        int numCount = 0;
        int specialCount = 0;
        boolean startingWithAlpha = false;
        boolean threeInRow = false;
        for(int i=0;i<password.length();i++){
            if(Character.isUpperCase(password.charAt(i)))
                upperCount++;
            if(Character.isLowerCase(password.charAt(i)))
                lowerCount++;
            if(Character.isDigit(password.charAt(i)))
                numCount++;
            if(!Character.isAlphabetic(password.charAt(i)) && !Character.isDigit(password.charAt(i)))
                specialCount++;
            if(i>1)
                if(password.charAt(i)==password.charAt(i-1) && password.charAt(i-1)==password.charAt(i-2))
                    threeInRow = true;
            if(i==0)
                if(Character.isAlphabetic(password.charAt(i)))
                    startingWithAlpha = true;
        }
        if(upperCount < 1){
            message = "Password has to contain at least one upper case letters";
            return "register";
        }
        if(lowerCount < 3){
            message = "Password has to contain at least three lower case letters";
            return "register";
        }
        if(numCount < 1){
            message = "Password has to contain at least one digit";
            return "register";
        }
        if(specialCount < 1){
            message = "Password has to contain at least one special character";
            return "register";
        }
        if(threeInRow){
            message = "Password can not contain three same symbols in row";
            return "register";
        }
        if(!startingWithAlpha){
            message = "Password has to start with alphabetic symbol";
            return "register";
        }
        
        try{
            DB.persistUser(user);
        }catch(Exception e){
            e.printStackTrace();
            message = "Failed to save a user";
            return "register";
        }
        
        message = "Succesfully registered";
        return "home";
    }
    
    //==========================================================================
    
    public UserController(){
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
