/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import session.User;
import db.DB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author milenkok
 */
@ManagedBean(name = "registerController")
@ViewScoped
public class RegisterController {
    @ManagedProperty(value = "#{user}")
    private User user;
    private String message;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
    private String phone;
    
    public RegisterController(){
        
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
    
    
    
    public String register(){
        if(firstName.equals("")){
            message = "First name field is required";
            return null;
        }
        if(lastName.equals("")){
            message = "Last name field is required";
            return null;
        }
        if(userName.equals("")){
            message = "User name field is required";
            return null;
        }
        if(password.equals("")){
            message = "Password field is required";
            return null;
        }
        if(confirmPassword.equals("")){
            message = "Confirm Password field is required";
            return null;
        }
        if(phone.equals("")){
            message = "Phone field is required";
            return null;
        }
        if(email.equals("")){
            message = "Email field is required";
            return null;
        }
        if(!password.equals(confirmPassword)){
            message = "Password field does not match confirm password field";
            return null;
        }
        if(password.length()<6 || password.length()>12){
            message = "Password length must be between 6 and 12";
            return null;
        }
        
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
            return null;
        }
        if(lowerCount < 3){
            message = "Password has to contain at least three lower case letters";
            return null;
        }
        if(numCount < 1){
            message = "Password has to contain at least one digit";
            return null;
        }
        if(specialCount < 1){
            message = "Password has to contain at least one special character";
            return null;
        }
        if(threeInRow){
            message = "Password can not contain three same symbols in row";
            return null;
        }
        if(!startingWithAlpha){
            message = "Password has to start with alphabetic symbol";
            return null;
        }
        
        user.setFirstName(userName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setRole(1); //set user role
        user.setValid(0); //not approved
        
        Boolean success;
        try{
            success = DB.register(user);
        }catch(Exception e){
            e.printStackTrace();
            message = "Failed to save a user";
            return null;
        }
        
        if(!success){
            message = "User already exists";
            return  null;
        }
        
        message = "Succesfully registered";
        return "home_unregistered";
    }
    
    
}
