/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Festival;
import model.FestivalComment;
import model.FestivalImage;
import model.FestivalRating;
import model.FestivalVideo;
import model.User;
import util.FileHelper;

@ManagedBean(name="registeredSingleFestival")
@ViewScoped
public class RegisteredSingleFestivalController implements Serializable {
    @ManagedProperty(value = "#{user}")
    private User user;
    private Festival festival;
    private Double averageRating;
    private String commentText;
    private Part file;
    private Integer ratingToPost;
    
    public RegisteredSingleFestivalController() {
        festival = (Festival)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("registered_festival");
        calculateAvgRating();
    }
    
    public final Double calculateAvgRating() {
        List<FestivalRating> ratings = (List<FestivalRating>) festival.getFestivalRatingCollection();
        double sum = 0;
        for(FestivalRating fr: ratings) {
            sum += fr.getRating();
        }
        sum /= ratings.size();
        averageRating = sum;
        return sum;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    
    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void uploadFile(boolean isVideo) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context
                .getExternalContext().getContext();
        String path = servletContext.getRealPath("");
        path = path.substring(0, path.length()-9);
        if (file.getSize() > 0) {
            boolean fileSuccess = false;
            String fileName = FileHelper.getFileNameFromPart(file);
            String fileExt = (isVideo)?"vid":"img";
            File outputFile = new File(path + File.separator + "web"
                    + File.separator + "resources" + File.separator + fileExt + File.separator + fileName);
            try {
                inputStream = file.getInputStream();
                outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if(isVideo) {
                    FestivalVideo video = new FestivalVideo(festival, "pending", fileName);
                    DB.uploadVideo(video);
                }
                else {
                    FestivalImage img = new FestivalImage(festival, "pending", fileName);
                    DB.uploadImage(img);
                }
                fileSuccess = true;
            } catch (IOException ex) {
                Logger.getLogger(FestivalEditController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void postComment() {
        FestivalComment fc = new FestivalComment(festival, user, commentText);
        DB.newComment(fc);
        festival = DB.getFestivalById(festival.getId());
    }
    
    public void postRating() {
        FestivalRating fr = new FestivalRating(festival, user, ratingToPost);
        DB.newFestivalRating(fr);
        festival = DB.getFestivalById(festival.getId());
        calculateAvgRating();
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Integer getRatingToPost() {
        return ratingToPost;
    }

    public void setRatingToPost(Integer ratingToPost) {
        this.ratingToPost = ratingToPost;
    }
    
    
}
