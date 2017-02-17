/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "festival_performer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FestivalPerformer.findAll", query = "SELECT f FROM FestivalPerformer f")
    , @NamedQuery(name = "FestivalPerformer.findById", query = "SELECT f FROM FestivalPerformer f WHERE f.id = :id")
    , @NamedQuery(name = "FestivalPerformer.findByStartDate", query = "SELECT f FROM FestivalPerformer f WHERE f.startDate = :startDate")
    , @NamedQuery(name = "FestivalPerformer.findByEndDate", query = "SELECT f FROM FestivalPerformer f WHERE f.endDate = :endDate")
    , @NamedQuery(name = "FestivalPerformer.findByStartTime", query = "SELECT f FROM FestivalPerformer f WHERE f.startTime = :startTime")
    , @NamedQuery(name = "FestivalPerformer.findByEndTime", query = "SELECT f FROM FestivalPerformer f WHERE f.endTime = :endTime")})
public class FestivalPerformer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endTime")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    
    @JoinColumn(name = "performer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Performer performer;
    @JoinColumn(name = "festival", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Festival festival;

    public FestivalPerformer() {
    }

    public FestivalPerformer(Integer id) {
        this.id = id;
    }

    public FestivalPerformer(Integer id, Date startDate, Date startTime, Date endDate, Date endTime) {
        this.id = id;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date time) {
        this.startTime = time;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date time) {
        this.endTime = time;
    }
    
    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FestivalPerformer)) {
            return false;
        }
        FestivalPerformer other = (FestivalPerformer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FestivalPerformer[ id=" + id + " ]";
    }
    
}
