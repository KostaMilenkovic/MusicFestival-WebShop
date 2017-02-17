/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.User;


@ManagedBean(name = "newFestivalController")
@ViewScoped
public class AdminApproveUserController implements Serializable {
    private List<User> users;
    
    public AdminApproveUserController() {
        
    }
    
    public String approve() {   
        String result = "home_admin.xhtml";
        
        
        return result;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}
