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


@ManagedBean(name = "adminApproveUserController")
@ViewScoped
public class AdminApproveUserController implements Serializable {
    private List<User> users;
    
    public AdminApproveUserController() {
        
    }
    
    public String approve(Integer id) {   
        String result = "home_admin.xhtml";
        DB.approveUser(id);
        
        return result;
    }

    public List<User> getUsers() {
        return DB.getUsersByApproved(0);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}
