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
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Festival;
import model.FestivalImage;
import model.FestivalPerformer;
import model.FestivalVideo;
import model.Performer;
import util.FileHelper;

@ManagedBean(name = "festivalEdit")
@ViewScoped
public class FestivalEditController implements Serializable {
    private Festival festival;
    
    private List<Performer> performers = null;
    private Integer selectedPerformer;
    private Date performerDateTimeStart;
    private Date performerDateTimeEnd;
    private Part file;
    
    public FestivalEditController() {
        festival = (Festival)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getAttribute("festival_to_edit");
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
    
    public void newEvent() {
        DateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        Date dateStart;
        Date timeStart;
        Date dateEnd;
        Date timeEnd;
        try {
            dateStart = formatterDate.parse(formatterDate.format(performerDateTimeStart));
            timeStart = formatterTime.parse(formatterTime.format(performerDateTimeStart));
            dateEnd = formatterDate.parse(formatterDate.format(performerDateTimeEnd));
            timeEnd = formatterTime.parse(formatterTime.format(performerDateTimeEnd));
            Performer p = DB.getPerformerById(selectedPerformer);
            FestivalPerformer fp = new FestivalPerformer(festival, p, dateStart, timeStart, dateEnd, timeEnd);
            DB.createEvent(fp);
        } catch (ParseException ex) {
            Logger.getLogger(FestivalEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    FestivalVideo video = new FestivalVideo(festival, fileName);
                    DB.uploadVideo(video);
                }
                else {
                    FestivalImage img = new FestivalImage(festival, fileName);
                    DB.uploadImage(img);
                }
                fileSuccess = true;
            } catch (IOException ex) {
                Logger.getLogger(FestivalEditController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<SelectItem> getPerformers() {
        if(performers == null) {
            performers = DB.getAllPerformers();
        }
        List<SelectItem> items = new ArrayList<>();
        for(Performer performer: performers){
            items.add(new SelectItem(performer.getId(), performer.getName()));
        }
        return items;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public Integer getSelectedPerformer() {
        return selectedPerformer;
    }

    public void setSelectedPerformer(Integer selectedPerformer) {
        this.selectedPerformer = selectedPerformer;
    }

    public Date getPerformerDateTimeStart() {
        return performerDateTimeStart;
    }

    public void setPerformerDateTimeStart(Date performerDateTimeStart) {
        this.performerDateTimeStart = performerDateTimeStart;
    }

    public Date getPerformerDateTimeEnd() {
        return performerDateTimeEnd;
    }

    public void setPerformerDateTimeEnd(Date performerDateTimeEnd) {
        this.performerDateTimeEnd = performerDateTimeEnd;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    
    
    
}
