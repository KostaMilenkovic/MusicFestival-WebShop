/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Festival;
import model.FestivalPerformer;
import model.Performer;

@ManagedBean(name = "festivalPerformers")
@ViewScoped
public class FestivalPerformersController {
    private List<FestivalPerformer> festivalPerformers = null;
    private Festival festival;
    
    public FestivalPerformersController() {
        festival = (Festival)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("festival_performers");
    }
    
    public List<FestivalPerformer> getFestivalPerformers() {
        if(festivalPerformers == null) {
            festivalPerformers = new ArrayList(festival.getFestivalPerformerCollection());
        }
        return festivalPerformers;
    }

    public void setFestivalPerformers(List<FestivalPerformer> performers) {
        this.festivalPerformers = performers;
    }
    
    public String editFestivalPerformer(FestivalPerformer performer) {
        ((HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true))).setAttribute("festival_performer_to_edit", performer);
        return "festival_performer_edit.xhtml";
    }
}
