/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Festival;
import model.FestivalImage;
import model.FestivalVideo;

@ManagedBean(name="adminApproveContent")
@ViewScoped
public class AdminApproveContentController {
    private Festival festival;
    private Festival selectedImage;
    private Festival selectedVideo;
    
    public AdminApproveContentController() {
        festival = (Festival)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("festival_to_approve");
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
    
    public void approveImage(FestivalImage img) {
        DB.approveImage(img);
    }
    
    public void approveVideo(FestivalVideo vid) {
        DB.approveVideo(vid);
    }
    
    public void cancelImage(FestivalImage img) {
        DB.cancelImage(img);
    }
    
    public void cancelVideo(FestivalVideo img) {
        DB.cancelVideo(img);
    }
}
