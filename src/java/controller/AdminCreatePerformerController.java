/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Performer;


@ManagedBean(name="adminCreatePerformer")
@ViewScoped
public class AdminCreatePerformerController {
    private String name;

    public AdminCreatePerformerController() {
        
    }

    public void createPerformer() {
        Performer p = new Performer(name);
        DB.newPerformer(p);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
