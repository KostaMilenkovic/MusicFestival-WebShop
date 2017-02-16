/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.User;


@ManagedBean(name = "resetPasswordController")
@ViewScoped
public class ResetPasswordController {
    @ManagedProperty(value = "#{user}")
    private User user;
    
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    
    private String message;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    public String resetPassword(){
        
        if(username.equals("")){
            message = "User name field is required";
            return null;
        }
        if(oldPassword.equals("")){
            message = "Old Password field is required";
            return null;
        }
        if(newPassword.equals("")){
            message = "Password field is required";
            return null;
        }
        if(confirmPassword.equals("")){
            message = "Confirm Password field is required";
            return null;
        }
        if(!newPassword.equals(confirmPassword)){
            message = "Password field does not match confirm password field";
            return null;
        }
        if(newPassword.length()<6 || newPassword.length()>12){
            message = "Password length must be between 6 and 12";
            return null;
        }
        String password = newPassword;
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
        
        Boolean success;
        
        try{
            user.setPassword(newPassword);
            user.setUsername(username);
            success = DB.resetPassword(username, oldPassword, newPassword);
        }catch(Exception e){
            e.printStackTrace();
            message = "Failed to reset a password. Try again";
            return null;
        }
        
        if(!success){
            message = "Wrong old password";
            return null;
        }
        
        message = "Your password has been reset successfully";
        return null;
    }
    
    
}
